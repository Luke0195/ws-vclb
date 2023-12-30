package br.com.dscatalog.application.services.exceptions;

public class ResourceNotExists extends RuntimeException {

    public ResourceNotExists() {
    }

    public ResourceNotExists(String msg) {
        super(msg);
    }
}
