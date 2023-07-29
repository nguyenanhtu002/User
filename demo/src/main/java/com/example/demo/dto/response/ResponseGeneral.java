package com.example.demo.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
@JsonInclude(JsonInclude.Include.NON_NULL)

@Data
public class ResponseGeneral<T> {

    private String message;

    private int statusCode;

    private T data;

    public ResponseGeneral(String message, T data) {
        this.statusCode = 200;
        this.message = message;
        this.data = data;
    }

    public ResponseGeneral(String message) {
        this.statusCode = 200;
        this.message = message;
    }

}