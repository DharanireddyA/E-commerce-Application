package com.example.ecommerce.controller;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.service.OrderService;
import com.example.ecommerce.util.PdfGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.InputStreamResource;
import java.io.ByteArrayInputStream;
import com.example.ecommerce.dto.OrderRequest;
import java.util.List;
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private PdfGenerator pdfGenerator;
    // 1. Get all orders
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/place")
public Order placeOrder(@RequestBody OrderRequest orderRequest) {
    return orderService.placeOrder(orderRequest.getUserId(), orderRequest.getProductIds());
}
    @DeleteMapping("/{id}")
public ResponseEntity<String> deleteOrder(@PathVariable Long id) {
    orderService.deleteOrder(id);
    return ResponseEntity.ok("Order deleted successfully");
}
public ResponseEntity<String> removeProductFromOrder(@PathVariable Long orderId, @RequestParam Long productId) {
    orderService.removeProductFromOrder(orderId, productId);
    return ResponseEntity.ok("Product removed from order successfully");
}
    // 3. Get order by ID
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(order);
    }
    // 4. Download order as PDF
@GetMapping("/{id}/pdf")
public ResponseEntity<InputStreamResource> downloadOrderPdf(@PathVariable Long id) {
    Order order = orderService.getOrderById(id);
    ByteArrayInputStream bis = pdfGenerator.generateOrderPdf(order);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Disposition", "attachment; filename=order_" + id + ".pdf");

    return ResponseEntity
            .ok()
            .headers(headers)
            .contentType(MediaType.APPLICATION_PDF)
            .body(new InputStreamResource(bis));
}
}

    