package com.unir.G9.books_payments.service;

import com.unir.G9.books_payments.controller.model.OrderRequest;
import com.unir.G9.books_payments.data.OrderJpaRepository;
import com.unir.G9.books_payments.data.model.Order;
import com.unir.G9.books_payments.facade.BooksFacade;
import com.unir.G9.books_payments.facade.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class BookOrdersServiceImpl implements BookOrdersService {

  @Autowired //Inyeccion por campo (field injection). Es la menos recomendada.
  private BooksFacade booksFacade;
  private Integer total;

  @Autowired //Inyeccion por campo (field injection). Es la menos recomendada.
  private OrderJpaRepository repository;

  @Override
  public Order createOrder(OrderRequest request) {

    List<Book> books = request.getBooks().stream().map(booksFacade::getBook).filter(Objects::nonNull).toList();
    total= 0;
    // Paso 1: contar cantidad por ID
    Map<Long, Long> cantidades = request.getBooks().stream()
            .collect(Collectors.groupingBy(id -> id, Collectors.counting()));


    if(books.size() != request.getBooks().size() || books.stream().anyMatch(book -> !book.getVisible())) {

      Order order = new Order();
      order.setCode(404);
      order.setMsg("Error al cargar el book");
      return order;
    } else {
      Order order = Order.builder().books(books.stream().map(Book::getIdlibro).collect(Collectors.toList())).build();
      //implementar el Total de Orden

      books.stream().filter(book -> book.getVisible()).forEach(book -> {
        total = total+ book.getPrecio();
      });

      //implementar el metodo Actualziar Stock
      // ✅ Actualizar stock de cada libro visible
      for (Book book : books) {
        long cantidad = cantidades.get(book.getIdlibro());
        int nuevoStock = book.getStock() - (int) cantidad;
        boolean actualizado = booksFacade.patchBook(
                book.getIdlibro(),
                Map.of("stock", nuevoStock)
        );

        if (actualizado) {

        }
      }
      order.setTotal(total);
      order.setCode(200);
      order.setMsg("Orden Creada Exitosamente");
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
