package com.unir.G9.books_payments.service;

import com.unir.G9.books_payments.controller.model.OrderRequest;
import com.unir.G9.books_payments.data.OrderJpaRepository;
import com.unir.G9.books_payments.data.model.Order;
import com.unir.G9.books_payments.facade.BooksFacade;
import com.unir.G9.books_payments.facade.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class BookOrdersServiceImpl implements BookOrdersService {

  @Autowired //Inyeccion por campo (field injection). Es la menos recomendada.
  private BooksFacade booksFacade;

  @Autowired //Inyeccion por campo (field injection). Es la menos recomendada.
  private OrderJpaRepository repository;

  @Override
  public Order createOrder(OrderRequest request) {

    List<Book> books = request.getBooks().stream().map(booksFacade::getBook).filter(Objects::nonNull).toList();

    if(books.size() != request.getBooks().size() || books.stream().anyMatch(book -> !book.getVisible_libro())) {
      return null;
    } else {
      Order order = Order.builder().books(books.stream().map(Book::getIdlibro).collect(Collectors.toList())).build();
      repository.save(order);
      return order;
    }
  }

  @Override
  public Order getOrder(String id) {
    return repository.findById(Long.valueOf(id)).orElse(null);
  }

  @Override
  public List<Order> getOrders() {
    List<Order> orders = repository.findAll();
    return orders.isEmpty() ? null : orders;
  }
}
