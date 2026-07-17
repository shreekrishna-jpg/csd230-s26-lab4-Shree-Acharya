package app.controllers;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String entityName, Long id) {
        super("Could not find " + entityName + " with ID: " + id);
    }
}