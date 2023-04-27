package org.example.dto;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.Pattern;
import lombok.NonNull;
import org.springdoc.core.annotations.ParameterObject;

@ParameterObject
public record CreateAuthUserDTO(
        @NonNull
        String username,

        @NonNull
        @Pattern(regexp = "^([a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6})*$")
        String email,

        @NonNull
        @Pattern(regexp = "^\\+?\\d+\\d{2}\\d{7}$")
        String phoneNumber,

        @NonNull
        String password,

        @NonNull
        String confirmPassword

) {
}
