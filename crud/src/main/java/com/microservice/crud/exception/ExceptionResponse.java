package com.microservice.crud.exception;


import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExceptionResponse implements Serializable {

    private static final long serialVersionUID = -8879503042034140828L;

    private Date timestamp;
    private String message;
    private String details;
}
