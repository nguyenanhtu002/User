package com.example.demo.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ResponseGeneral<T> {

  private String message;
  private int statusCode;
  private T data;

  public ResponseGeneral(String message, T data, int statusCode) {
    this.message = message;
    this.statusCode = statusCode;
    this.data = data;
  }

  public ResponseGeneral(String message, int statusCode) {
    this(message, null, statusCode);
  }

  public ResponseGeneral(String message, T data) {
    this(message, data, HttpStatus.OK.value());
  }

  public ResponseGeneral(String message) {
    this(message, null, HttpStatus.OK.value());
  }

}