package com.example.aosaka.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
public class LikeAchievementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "iduser", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "idachievement", nullable = false)
    private AchievementEntity achievement;
}
