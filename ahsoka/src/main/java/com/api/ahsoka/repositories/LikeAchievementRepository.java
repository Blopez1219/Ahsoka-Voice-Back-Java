package com.api.ahsoka.repositories;

import com.api.ahsoka.models.LikeAchievementEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeAchievementRepository extends CrudRepository<LikeAchievementEntity, Long> {

    @Query("Select l from LikeAchievementEntity l where l.achievement.idachievement = ?1")
    List<LikeAchievementEntity> findByAchievementId(Long achievementId);

    @Query("Select l from LikeAchievementEntity l where l.user.iduser = ?1")
    List<LikeAchievementEntity> findByUserId(Long userId);
}
