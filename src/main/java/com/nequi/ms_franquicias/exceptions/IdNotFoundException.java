package com.nequi.ms_franquicias.exceptions;


public class IdNotFoundException extends RuntimeException{
    public IdNotFoundException(Long id){
        super("id not found: " + id);
    }
}