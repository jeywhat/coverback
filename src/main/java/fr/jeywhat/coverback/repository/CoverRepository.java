package fr.jeywhat.coverback.repository;

import fr.jeywhat.coverback.repository.model.CoverEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoverRepository extends JpaRepository<CoverEntity, String> {
}
