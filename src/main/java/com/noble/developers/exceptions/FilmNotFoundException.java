package com.noble.developers.exceptions;

public class FilmNotFoundException extends RuntimeException{
    public FilmNotFoundException(String message){
        super(message);
    }
}
