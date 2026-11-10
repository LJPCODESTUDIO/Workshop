package org.example.workshop;

public record WorkshopResponse(Long id, String title, String category, String presenter, Integer durationMinutes, Integer capacity) {
    public static WorkshopResponse from (Workshop workshop) {
        return new WorkshopResponse(workshop.getId(),
                workshop.getTitle(),
                workshop.getCategory(),
                workshop.getPresenter(),
                workshop.getDurationMinutes(),
                workshop.getCapacity());
    }
}
