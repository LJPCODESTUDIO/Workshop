package org.example.workshop;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/workshops")
public class WorkshopController {
    private WorkshopService service;

    public WorkshopController(WorkshopService service) {
        this.service = service;
    }

    // GET requests

    //Get all workshops or filter by category
    @GetMapping
    public List<WorkshopResponse> getWorkshops(
        @RequestParam(required = false) String category) {

        if (category == null) {
            return service.findAll();
        }

        return service.findByCategory(category);
    }

    @GetMapping("/{id}")
    public WorkshopResponse getById(@PathVariable long id) {
        return service.findById(id);
    }

    // POST requests
    @PostMapping
    public ResponseEntity<WorkshopResponse> createBook(@RequestBody WorkshopRequest request) {
        WorkshopResponse saved = service.add(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    //Pull requests

    //Update an existing workshop
    @PutMapping("/{id}")
    public ResponseEntity<WorkshopResponse> updateWorkshop(
            @PathVariable long id,
            @RequestBody WorkshopRequest request) {

        Optional<WorkshopResponse> updated = service.update(id, request);

        if (updated.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updated.get());
    }

    //Delete an existing workshop
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkshop(@PathVariable Long id) {

        boolean deleted = service.delete(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
