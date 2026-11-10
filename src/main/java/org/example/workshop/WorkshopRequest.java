package org.example.workshop;

public record WorkshopRequest(String title, String category, String presenter, Integer durationMinutes, Integer capacity) {
}
