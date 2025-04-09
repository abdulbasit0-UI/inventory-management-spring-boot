package com.inventory.inventory_management.reports;

import com.inventory.inventory_management.Product.Dto.ProductResponseDto;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PdfGenerationService {

    // Method to generate product inventory report
    public byte[] generateInventoryReport(List<ProductResponseDto> products) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        
        try (PdfWriter writer = new PdfWriter(outputStream);
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf)) {

                document.add(new Paragraph("COMPANY NAME")
                .setFontSize(20)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER));

            // Add report header
            Paragraph header = new Paragraph("Inventory Report")
                    .setFontSize(18)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(header);
            
            // Add generated date
            String currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            Paragraph dateInfo = new Paragraph("Generated on: " + currentDateTime)
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.RIGHT);
            document.add(dateInfo);
            
            document.add(new Paragraph("\n"));
            
            // Create table for products
            Table table = new Table(UnitValue.createPercentArray(new float[]{3, 15, 5, 5, 7}))
                    .useAllAvailableWidth();
            
            // Add table headers
            table.addHeaderCell(createHeaderCell("ID"));
            table.addHeaderCell(createHeaderCell("Product Name"));
            table.addHeaderCell(createHeaderCell("Price"));
            table.addHeaderCell(createHeaderCell("Quantity"));
            table.addHeaderCell(createHeaderCell("Category"));
            
            // Add product data rows
            for (ProductResponseDto product : products) {
                table.addCell(createCell(String.valueOf(product.getId())));
                table.addCell(createCell(product.getName()));
                table.addCell(createCell(String.format("$%.2f", product.getPrice())));
                table.addCell(createCell(String.valueOf(product.getQuantity())));
                table.addCell(createCell(product.getCategoryName()));
            }
            
            document.add(table);
            
            // Add summary information
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Total Products: " + products.size())
                    .setTextAlignment(TextAlignment.RIGHT));
            
            int totalInventory = products.stream().mapToInt(ProductResponseDto::getQuantity).sum();
            document.add(new Paragraph("Total Inventory Count: " + totalInventory)
                    .setTextAlignment(TextAlignment.RIGHT));
            
            double totalValue = products.stream()
                    .mapToDouble(p -> p.getPrice().toBigInteger().doubleValue() * p.getQuantity())
                    .sum();
            document.add(new Paragraph(String.format("Total Inventory Value: $%.2f", totalValue))
                    .setTextAlignment(TextAlignment.RIGHT));
            
            // Add footer
            document.add(new Paragraph("\n\n"));
            document.add(new Paragraph("This is a system generated report.")
                    .setFontSize(8)
                    .setItalic()
                    .setTextAlignment(TextAlignment.CENTER));
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate inventory PDF report", e);
        }
        
        return outputStream.toByteArray();
    }
    
    // Helper method for creating header cells
    private Cell createHeaderCell(String text) {
        return new Cell()
                .add(new Paragraph(text))
                .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER);
    }
    
    // Helper method for creating regular cells
    private Cell createCell(String text) {
        return new Cell().add(new Paragraph(text));
    }
    
    // Additional methods for other report types...
    // e.g., generateOrderInvoice(), generateProductCatalog(), etc.
}