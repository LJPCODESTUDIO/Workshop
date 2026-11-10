package org.example.workshop;

import org.example.workshop.error.DuplicateWorkshopException;
import org.example.workshop.error.WorkshopNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class WorkshopService {
    private final WorkshopRepository repository;

    public WorkshopService(WorkshopRepository repository) {
        this.repository = repository;
    }

    // Getters
    public List<WorkshopResponse> findAll() {
        return repository.findAll(Sort.by("id")).stream()
                .map(WorkshopResponse::from)
                .toList();
    }

    public WorkshopResponse findById(long id) {
        return repository.findById(id)
                .map(WorkshopResponse::from)
                .orElseThrow(() -> new WorkshopNotFoundException(id));
    }

    // Setters
    @Transactional
    public WorkshopResponse add(WorkshopRequest request) {
        if (repository.existsByTitleIgnoreCaseAndCategoryIgnoreCaseAndPresenterIgnoreCase(
                request.title(), request.category(), request.presenter())) {
            throw new DuplicateWorkshopException(request.title(), request.category(), request.presenter());
        }

        Workshop workshop = new Workshop(request.title(),
                request.category(),
                request.presenter(),
                request.durationMinutes(),
                request.capacity());

        return WorkshopResponse.from(repository.save(workshop));
    }
}
