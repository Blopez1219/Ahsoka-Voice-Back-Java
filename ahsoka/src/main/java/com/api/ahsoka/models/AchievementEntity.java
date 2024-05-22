package com.api.ahsoka.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "logro")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AchievementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idachievement;

    @NotNull(message = "La fecha de logro no puede ser nula")
    private LocalDate achievementDate;

    @NotBlank
    private String description;

    private String image;

    private boolean visible;

    @ManyToOne
    @JoinColumn(name = "iduser", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "idarea", nullable = false)
    private AreaEntity area;

    @OneToMany(mappedBy = "achievement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LikeAchievementEntity> likes;

    @OneToMany(mappedBy = "achievement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentsEntity> comments;
}
