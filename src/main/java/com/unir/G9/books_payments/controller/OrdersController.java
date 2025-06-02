package com.unir.G9.books_payments.controller;

import com.unir.G9.books_payments.controller.model.OrderRequest;
import com.unir.G9.books_payments.data.model.Order;
import com.unir.G9.books_payments.service.BookOrdersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OrdersController {

    private final BookOrdersService service; //Inyeccion por constructor mediante @RequiredArgsConstructor. Y, también es inyección por interfaz.

    @PostMapping("/orders")
    public ResponseEntity<Order> createOrder(@RequestBody @Valid OrderRequest request) { //Se valida con Jakarta Validation API

        log.info("Creating order...");
        Order created = service.createOrder(request);

        if (created != null) {
            return ResponseEntity.ok(created);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getOrders() {

        List<Order> orders = service.getOrders();
        if (orders != null) {
            return ResponseEntity.ok(orders);
        } else {
            return ResponseEntity.ok(Collections.emptyList());
        }
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable String id) {

        Order order = service.getOrder(id);
        if (order != null) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
