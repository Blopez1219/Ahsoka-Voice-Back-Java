package com.example.aosaka.service;

import com.example.aosaka.models.AchievementEntity;
import com.example.aosaka.repositories.AchievementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class AchievementService {

    @Autowired
    private AchievementRepository achievementRepository;

    public AchievementEntity createAchievement(AchievementEntity achievement) {
        return achievementRepository.save(achievement);
    }

    public List<AchievementEntity> getAllAchievements() {
        return (List<AchievementEntity>) achievementRepository.findAll();
    }

    public Optional<AchievementEntity> getAchievementById(Long id) {
        return achievementRepository.findById(id);
    }

    public AchievementEntity updateAchievement(Long id, AchievementEntity achievementDetails) {
        AchievementEntity achievement = achievementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Achievement not found"));
        achievement.setTitle(achievementDetails.getTitle());
        achievement.setAchievementDate(achievementDetails.getAchievementDate());
        achievement.setDescription(achievementDetails.getDescription());
        achievement.setImage(achievementDetails.getImage());
        achievement.setVisible(achievementDetails.isVisible());
        return achievementRepository.save(achievement);
    }

    public void deleteAchievement(Long id) {
        AchievementEntity achievement = achievementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Achievement not found"));
        achievementRepository.delete(achievement);
    }

    public void hideAchievement(Long id) {
        AchievementEntity achievement = achievementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Achievement not found"));
        achievement.setVisible(false);
        achievementRepository.save(achievement);
    }

    public AchievementEntity getRandomAchievement() {
        List<AchievementEntity> achievements = (List<AchievementEntity>) achievementRepository.findAll();
        return achievements.get(new Random().nextInt(achievements.size()));
    }

    public List<AchievementEntity> getAchievementsByUserId(Long userId) {
        return achievementRepository.findByUserId(userId);
    }
}

