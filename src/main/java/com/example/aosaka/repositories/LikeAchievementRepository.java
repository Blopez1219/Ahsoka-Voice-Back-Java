package com.example.aosaka.repositories;


import com.example.aosaka.models.LikeAchievementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeAchievementRepository extends JpaRepository<LikeAchievementEntity, Long> {
    long countByAchievementId(Long achievementId);
}
