package org.example.controllers;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.example.dto.ActivateAccountDTO;
import org.example.dto.CreateAuthUserDTO;
import org.example.service.AuthUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth/")
public class AuthUserController {

    private final AuthUserService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @NonNull @Valid CreateAuthUserDTO dto
    ) {
        authService.register(dto);
        return ResponseEntity.ok("User registered successfully\n" +
                "Please check your email for activation link");
    }

    @PostMapping("/activate_account")
    public ResponseEntity<String> activateAccount(
            @NonNull @Valid ActivateAccountDTO dto
    ) {
        authService.activateAccountWithOTP(dto);
        return ResponseEntity.ok("Account activated successfully");
    }

    @GetMapping("/test/{username}")
    public ResponseEntity<String> test(
            @NonNull @PathVariable("username") String username
    ) {
        authService.activateAccount(username);
        return ResponseEntity.ok("Your email is activated successfully, dear " + username + "\n" + "You can close this window now");
    }
}
