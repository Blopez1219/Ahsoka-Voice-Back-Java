package com.api.ahsoka.exceptions;

import com.api.ahsoka.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exceptions.UsernameNotFoundException.class)
    public ResponseEntity<ApiResponse> handleUsernameNotFoundException(Exceptions.UsernameNotFoundException ex) {
        ApiResponse response = new ApiResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleException(Exception ex) {
        ApiResponse response = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ocurrió un error al procesar la solicitud");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
