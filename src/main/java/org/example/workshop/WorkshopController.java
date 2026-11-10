package org.example.workshop;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workshops")
public class WorkshopController {
    private WorkshopService service;

    public WorkshopController(WorkshopService service) {
        this.service = service;
    }

    // GET requests
    @GetMapping
    public List<WorkshopResponse> getWorkshops() {
        return service.findAll();
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
}
