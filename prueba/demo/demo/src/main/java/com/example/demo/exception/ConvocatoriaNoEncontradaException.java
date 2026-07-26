package com.example.demo.exception;

public class ConvocatoriaNoEncontradaException
        extends RuntimeException{

    public ConvocatoriaNoEncontradaException(Integer id){

        super("No existe la convocatoria " + id);

    }

}