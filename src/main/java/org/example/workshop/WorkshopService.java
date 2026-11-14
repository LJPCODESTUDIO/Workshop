package org.example.workshop;

import org.example.workshop.error.DuplicateWorkshopException;
import org.example.workshop.error.WorkshopNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    public List<WorkshopResponse> findByCategory(String category) {
        return repository.findByCategoryIgnoreCaseOrderByIdAsc(category)
            .stream()
            .map(WorkshopResponse::from)
            .toList();
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

    //Update a workshop
    @Transactional
    public Optional<WorkshopResponse> update(long id, WorkshopRequest request) {

        Optional<Workshop> result = repository.findById(id);

        if (result.isEmpty()) {
            return Optional.empty();
        }

        Workshop workshop = result.get();

        workshop.setTitle(request.title());
        workshop.setCategory(request.category());
        workshop.setPresenter(request.presenter());
        workshop.setDurationMinutes(request.durationMinutes());
        workshop.setCapacity(request.capacity());

        Workshop updatedWorkshop = repository.save(workshop);

        return Optional.of(WorkshopResponse.from(updatedWorkshop));
    }

    //Delete a workshop
    @Transactional
    public boolean delete(long id) {

        Optional<Workshop> result = repository.findById(id);

        if (result.isEmpty()) {
            return false;
        }

        repository.delete(result.get());

        return true;
    }
}
