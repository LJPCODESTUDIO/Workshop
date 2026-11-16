package org.example.workshop;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WorkshopRepository extends JpaRepository<Workshop, Long> {
    boolean existsByTitleIgnoreCaseAndCategoryIgnoreCaseAndPresenterIgnoreCase(String title, String category, String presenter);

    List<Workshop> findByCategoryIgnoreCaseOrderByIdAsc(String category);
    List<Workshop> findByPresenterIgnoreCaseOrderByIdAsc(String presenter);
    List<Workshop> findByTitleContainingIgnoreCaseOrderByIdAsc(String title);
}
