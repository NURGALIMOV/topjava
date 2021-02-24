package ru.javawebinar.topjava.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(long id) {
        super(String.format("Not found by id: %s", id));
    }

}
