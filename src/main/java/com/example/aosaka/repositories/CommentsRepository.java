package com.example.aosaka.repositories;

import com.example.aosaka.models.ComentsEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsRepository extends CrudRepository<ComentsEntity, Long> {
    @Query("Select c from CommentsEntity c where c.achievement.idachievement = ?1")
    List<ComentsEntity> findByAchievementId(Long achievementId);

    @Query("Select c from CommentsEntity c where c.user.iduser = ?1")
    List<ComentsEntity> findByUserId(Long userId);
}
