package com.inventory.inventory_management.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.inventory.inventory_management.Order.Dto.CreateOrderDto;
import com.inventory.inventory_management.Order.Dto.CreateOrderItemDto;
import com.inventory.inventory_management.Order.Dto.OrderResponseDto;
import com.inventory.inventory_management.Order.Dto.UpdateOrderStatus;
import com.inventory.inventory_management.Product.Product;
import com.inventory.inventory_management.Product.ProductRepository;
import com.inventory.inventory_management.Security.User;
import com.inventory.inventory_management.Security.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final StockService stockService;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public List<OrderResponseDto> getAllOrders() {
        return orderRepository.findAll().stream().map(order -> modelMapper.map(order, OrderResponseDto.class)).toList();
    }

    public OrderResponseDto getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .map(order -> modelMapper.map(order, OrderResponseDto.class))
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
    }

    public List<OrderResponseDto> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(order -> modelMapper.map(order, OrderResponseDto.class))
                .toList();
    }

    @Transactional
    public OrderResponseDto createOrder(CreateOrderDto createOrderDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        for (CreateOrderItemDto itemDto : createOrderDto.getItems()) {
            if (!stockService.isInStock(itemDto.getProductId(), itemDto.getQuantity())) {
                Product product = productRepository.findById(itemDto.getProductId())
                        .orElseThrow(
                                () -> new RuntimeException("Product not found with id: " + itemDto.getProductId()));
                throw new RuntimeException("Not enough stock available for product: " + product.getName());
            }
        }

        Order order = Order.builder().orderNumber(generateOrderNumber()).user(user).orderDate(LocalDateTime.now())
                .status(OrderStatus.PENDING).shippingAddress(createOrderDto.getShippingAddress())
                .orderItems(new ArrayList<>()).build();

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (CreateOrderItemDto itemDto : createOrderDto.getItems()) {
            Product product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found with id: " + itemDto.getProductId()));

            BigDecimal unitPrice = product.getPrice();

            BigDecimal subTotal = unitPrice.multiply(BigDecimal.valueOf(itemDto.getQuantity()));

            OrderItem orderItem = OrderItem.builder().order(order).product(product).quantity(itemDto.getQuantity())
                    .unitPrice(unitPrice).subTotal(subTotal).build();

            order.getOrderItems().add(orderItem);
            totalAmount = totalAmount.add(subTotal);

            stockService.reduceQuantity(product.getId(), itemDto.getQuantity());

        }

        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);

        return modelMapper.map(savedOrder, OrderResponseDto.class);
    }

    @Transactional
    public OrderResponseDto updateOrderStatus(Long orderId, UpdateOrderStatus updateOrderStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        OrderStatus oldStatus = order.getStatus();
        OrderStatus newStatus = updateOrderStatus.getOrderStatus();

        if (newStatus == OrderStatus.CANCELLED && oldStatus != OrderStatus.CANCELLED) {
            for (OrderItem orderItem : order.getOrderItems()) {
                stockService.increaseQuantity(orderItem.getProduct().getId(), orderItem.getQuantity());
            }
        }

        if (oldStatus == OrderStatus.CANCELLED && newStatus != OrderStatus.CANCELLED) {
            for (OrderItem orderItem : order.getOrderItems()) {
                stockService.reduceQuantity(orderItem.getProduct().getId(), orderItem.getQuantity());
            }
        }

        order.setStatus(newStatus);
        Order updatedOrder = orderRepository.save(order);

        return modelMapper.map(updatedOrder, OrderResponseDto.class);
    }

    @Transactional
    public void deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        if (order.getStatus() != OrderStatus.CANCELLED) {
            for (OrderItem orderItem : order.getOrderItems()) {
                stockService.increaseQuantity(orderItem.getProduct().getId(), orderItem.getQuantity());
            }
        }

        orderRepository.delete(order);
    }

    private String generateOrderNumber() {
        return "ORD-" + System.currentTimeMillis();
    }

}
