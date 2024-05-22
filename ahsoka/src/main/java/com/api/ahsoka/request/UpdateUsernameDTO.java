package com.api.ahsoka.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUsernameDTO {

    @NotNull
    private Long userId;

    @NotBlank
    private String newUsername;
}
