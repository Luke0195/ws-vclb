package br.com.dscatalog.application.services.exceptions;

public class DatabaseException extends RuntimeException {

    public DatabaseException() {
    }

    public DatabaseException(String message) {
        super(message);
    }
}
