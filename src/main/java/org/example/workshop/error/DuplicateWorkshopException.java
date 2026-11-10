package org.example.workshop.error;

public class DuplicateWorkshopException extends RuntimeException {
    public DuplicateWorkshopException(String title, String category, String presenter) {
        super("A workshop titled '" + title + "' in the category '" + category + "' presented by '" + presenter + "' already exists.");
    }
}
