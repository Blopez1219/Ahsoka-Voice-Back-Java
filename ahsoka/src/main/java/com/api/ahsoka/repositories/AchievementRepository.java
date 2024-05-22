package com.api.ahsoka.repositories;

import com.api.ahsoka.models.AchievementEntity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AchievementRepository extends CrudRepository<AchievementEntity, Long> {

    @Query("Select a from AchievementEntity a where a.description = ?1")
    Optional<AchievementEntity> findByDescription(String description);
}
