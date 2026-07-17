package app.repositories;

import app.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StationeryRepository extends JpaRepository<ProductEntity, Long> {

    @Query("SELECT p FROM ProductEntity p WHERE TYPE(p) IN (app.entities.PenEntity, app.entities.NotebookEntity)")
    List<ProductEntity> findAllStationery();
}