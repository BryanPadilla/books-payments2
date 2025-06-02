package com.unir.G9.books_payments.facade;

import com.unir.G9.books_payments.facade.model.Book;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Map;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class BooksFacade {

  @Value("${getBook.url}")
  private String getBookUrl;

  @Value("${patchBook.url}")               // ej. http://books-service/api/books/%d
  private String patchBookUrl;

  private final WebClient.Builder webClient;

  public Book getBook(Long id) {

    try {
      String url = String.format(getBookUrl, id);
      log.info("Getting product with ID {}. Request to {}", id, url);
      return webClient.build()
              .get()
              .uri(url)
              .retrieve()
              .bodyToMono(Book.class)
              .block();
    } catch (HttpClientErrorException e) {
      log.error("Client Error: {}, Product with ID {}", e.getStatusCode(), id);
      return null;
    } catch (HttpServerErrorException e) {
      log.error("Server Error: {}, Product with ID {}", e.getStatusCode(), id);
      return null;
    } catch (Exception e) {
      log.error("Error: {}, Product with ID {}", e.getMessage(), id);
      return null;
    }
  }


  /** PATCH: actualiza campos parciales (p.e. stock) */
  public boolean patchBook(Long id, Map<String, Object> fields) {

    try {
      String url = String.format(patchBookUrl, id);
      log.info("Patching book ID {} with fields {}. Request to {}", id, fields, url);

      webClient.build()
              .patch()
              .uri(url)
              .bodyValue(fields)          // envía solo los campos a modificar
              .retrieve()
              .bodyToMono(Void.class)
              .block();

      return true;

    } catch (HttpClientErrorException e) {
      log.error("Client Error: {}, Book ID {}", e.getStatusCode(), id);
      return false;
    } catch (HttpServerErrorException e) {
      log.error("Server Error: {}, Book ID {}", e.getStatusCode(), id);
      return false;
    } catch (Exception e) {
      log.error("Error: {}, Book ID {}", e.getMessage(), id);
      return false;
    }
  }

}
