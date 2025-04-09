package com.inventory.inventory_management.reports;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.inventory_management.Product.ProductService;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
public class ReportController {

    private final PdfGenerationService pdfGenerationService;
    private final ProductService productService;
    
    @GetMapping("/inventory/pdf")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<byte[]> generateInventoryPdf() {
        // Get all products from your service
        var products = productService.getAllProducts();
        
        // Generate PDF
        byte[] pdfBytes = pdfGenerationService.generateInventoryReport(products);
        
        // Prepare response headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        
        // Set filename for download
        String filename = "inventory-report-" + 
                java.time.LocalDate.now().toString() + ".pdf";
        headers.setContentDispositionFormData("attachment", filename);
        
        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }
    
    // Additional endpoints for other report types
}