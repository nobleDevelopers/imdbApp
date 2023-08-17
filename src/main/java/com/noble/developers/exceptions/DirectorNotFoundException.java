package com.noble.developers.exceptions;

public class DirectorNotFoundException extends RuntimeException{
    public DirectorNotFoundException(String message){
        super(message);
    }
}
