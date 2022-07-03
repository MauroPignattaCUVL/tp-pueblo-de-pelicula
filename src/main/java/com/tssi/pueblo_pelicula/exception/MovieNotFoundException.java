package com.tssi.pueblo_pelicula.exception;

public class MovieNotFoundException extends RuntimeException {

    public MovieNotFoundException(String msg) {
        super(msg);
    }
}
