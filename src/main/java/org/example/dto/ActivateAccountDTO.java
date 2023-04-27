package org.example.dto;

import jakarta.validation.constraints.Pattern;
import lombok.NonNull;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.PathVariable;

@ParameterObject
public record ActivateAccountDTO(
        @NonNull
        String otp,

        @NonNull
        String username,

        @NonNull
        @Pattern(regexp = "^([a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6})*$", message = "Invalid email format")
        String email
) {
}
