package com.unir.G9.books_payments.data.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder

public class BaseResponse {

    private Integer code;
    private String msg;

}
