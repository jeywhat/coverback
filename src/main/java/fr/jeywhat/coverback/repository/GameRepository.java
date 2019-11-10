package fr.jeywhat.coverback.repository;

import fr.jeywhat.coverback.repository.model.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<GameEntity, String> {

    List<GameEntity> findGameEntitiesByImageIsNullOrImageEqualsOrImageEquals(byte[] image1, byte[] image2);
}
