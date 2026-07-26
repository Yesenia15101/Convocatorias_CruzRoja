package com.example.demo.exception;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(
            ConvocatoriaNoEncontradaException.class
    )

    public ResponseEntity<String> manejar(
            ConvocatoriaNoEncontradaException ex){

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());

    }

}