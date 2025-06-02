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
	private String fechapub;
	private String isbn;
	private Integer stock;
	private String titulo;
	private String valoracion;
	private Boolean visible;
	private Integer precio;
}
