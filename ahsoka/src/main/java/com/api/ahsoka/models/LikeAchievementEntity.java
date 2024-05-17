package com.api.ahsoka.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "likeslogro")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LikeAchievementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idlikeachievement;

    @ManyToOne
    @JoinColumn(name = "iduser", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "idachievement", nullable = false)
    private AchievementEntity achievement;
}
