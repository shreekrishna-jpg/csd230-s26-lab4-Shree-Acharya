package app.repositories;

import app.entities.PenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PenRepository
        extends JpaRepository<PenEntity, Long> {
}