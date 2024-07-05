package com.example.aosaka.controllers;


import com.example.aosaka.models.AchievementEntity;
import com.example.aosaka.service.AchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/achievements")
public class AchievementController {

    @Autowired
    private AchievementService achievementService;

    @PostMapping
    public AchievementEntity createAchievement(@RequestBody AchievementEntity achievement) {
        return achievementService.createAchievement(achievement);
    }

    @GetMapping
    public List<AchievementEntity> getAllAchievements() {
        return achievementService.getAllAchievements();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AchievementEntity> getAchievementById(@PathVariable Long id) {
        Optional<AchievementEntity> achievement = achievementService.getAchievementById(id);
        return achievement.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AchievementEntity> updateAchievement(@PathVariable Long id, @RequestBody AchievementEntity achievementDetails) {
        AchievementEntity updatedAchievement = achievementService.updateAchievement(id, achievementDetails);
        return ResponseEntity.ok(updatedAchievement);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAchievement(@PathVariable Long id) {
        achievementService.deleteAchievement(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/hide")
    public ResponseEntity<AchievementEntity> hideAchievement(@PathVariable Long id) {
        achievementService.hideAchievement(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/random")
    public AchievementEntity getRandomAchievement() {
        return achievementService.getRandomAchievement();
    }

    @GetMapping("/user/{userId}")
    public List<AchievementEntity> getAchievementsByUserId(@PathVariable Long userId) {
        return achievementService.getAchievementsByUserId(userId);
    }
}
