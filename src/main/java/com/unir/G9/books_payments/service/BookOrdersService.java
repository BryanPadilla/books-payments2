package com.unir.G9.books_payments.service;

import com.unir.G9.books_payments.facade.model.Book;
import com.unir.G9.books_payments.controller.model.OrderRequest;
import com.unir.G9.books_payments.data.model.Order;

import java.util.List;

public interface BookOrdersService {

	Order createOrder(OrderRequest request);

	Order getOrder(String id);

	List<Order> getOrders();

}
