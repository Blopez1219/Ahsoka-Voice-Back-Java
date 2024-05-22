package com.api.ahsoka.repositories;

import com.api.ahsoka.models.CommentsEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsRepository extends CrudRepository<CommentsEntity, Long> {
    @Query("Select c from CommentsEntity c where c.achievement.idachievement = ?1")
    List<CommentsEntity> findByAchievementId(Long achievementId);

    @Query("Select c from CommentsEntity c where c.user.iduser = ?1")
    List<CommentsEntity> findByUserId(Long userId);
}
