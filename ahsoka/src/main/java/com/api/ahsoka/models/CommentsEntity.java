package com.api.ahsoka.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comentarios")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idcomment;

    @NotBlank
    private String description;

    @ManyToOne
    @JoinColumn(name = "idachievement", nullable = false)
    private AchievementEntity achievement;

    @ManyToOne
    @JoinColumn(name = "iduser", nullable = false)
    private UserEntity user;
}
