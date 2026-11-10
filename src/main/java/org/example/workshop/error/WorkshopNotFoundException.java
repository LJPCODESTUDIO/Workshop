package org.example.workshop.error;

public class WorkshopNotFoundException extends RuntimeException {
    private final long workshopId;

    public WorkshopNotFoundException(long workshopId) {
        super("Workshop with ID " + workshopId + " not found");
        this.workshopId = workshopId;
    }

    public long getWorkshopId() {
        return workshopId;
    }
}
