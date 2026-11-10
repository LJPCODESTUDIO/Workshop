package org.example.workshop;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkshopRepository extends JpaRepository<Workshop, Long> {
    boolean existsByTitleIgnoreCaseAndCategoryIgnoreCaseAndPresenterIgnoreCase(String title, String category, String presenter);
}
