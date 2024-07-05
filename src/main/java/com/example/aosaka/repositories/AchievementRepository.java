package com.example.aosaka.repositories;
import java.util.List;

import com.example.aosaka.models.AchievementEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
public interface AchievementRepository extends CrudRepository<AchievementEntity, Long> {

    @Query("SELECT a FROM AchievementEntity a WHERE a.user.id = ?1")
    List<AchievementEntity> findByUserId(Long userId);

    List<AchievementEntity> findAll();
}