package com.unir.G9.books_payments.data;

import com.unir.G9.books_payments.data.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<Order, Long> {
}
