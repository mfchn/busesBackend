package com.mfcn.buses.exception;

public class BusNotFoundException extends RuntimeException{
    public BusNotFoundException(Long id) {
        super("No se encontro ningun bus con id " + id);
    }

}
