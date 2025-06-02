package com.unir.G9.books_payments.facade.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Book {
	private Long idlibro;
	private String fechapub_libro;
	private String isbn_libro;
	private Integer stock_libro;
	private String titulo_libro;
	private String valoracion_libro;
	private Boolean visible_libro;
}
