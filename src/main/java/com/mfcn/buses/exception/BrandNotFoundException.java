package com.mfcn.buses.exception;

public class BrandNotFoundException extends RuntimeException {
    public BrandNotFoundException(Long id) {
        super("No se encontro ninguna marca con id " + id);
    }
}