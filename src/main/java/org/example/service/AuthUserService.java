package org.example.service;

import freemarker.template.Configuration;
import jakarta.mail.internet.MimeMessage;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.domains.AuthUser;
import org.example.dto.ActivateAccountDTO;
import org.example.dto.CreateAuthUserDTO;
import org.example.repository.AuthUserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Random;

@Lazy
@Slf4j
@Service
@Component
@RequiredArgsConstructor
public class AuthUserService {
    private final PasswordEncoder passwordEncoder;
    private final AuthUserRepository authUserRepository;
    private final JavaMailSender javaMailSender;
    private final Configuration configuration;

    public void register(@NonNull CreateAuthUserDTO dto) {

        if (!dto.password().equals(dto.confirmPassword())) {
            throw new RuntimeException("Passwords are not equal");
        }

        Random random = new Random();
        String code = String.valueOf(random.nextInt(100000, 999999));
        System.out.println(">===------> O T P : " + code);

        AuthUser authUser = AuthUser.childBuilder()
                .username(dto.username())
                .email(dto.email())
                .phoneNumber(dto.phoneNumber())
                .password(passwordEncoder.encode(dto.password()))
                .otp(code)
                .active(AuthUser.Active.IN_ACTIVE)
                .role(AuthUser.Role.USER)
                .build();

        authUserRepository.save(authUser);

        sendActivationOTP(dto.username(), dto.email(), code);

    }

    @Async
    public void sendActivationOTP(String username, String email, String otp) {

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            mimeMessage.setFrom("john.lgd65@gmail.com");
            mimeMessage.setRecipients(MimeMessage.RecipientType.TO, email);
            mimeMessage.setSubject("Spring Boot Mailing Example");

            String content = """
                    <!DOCTYPE html>
                    <html lang="en">
                    <head>
                        <meta charset="UTF-8">
                        <title>Activation Page</title>
                    </head>
                    <body>
                    <div style="background: aqua;">
                        <h1>Hello %s</h1>
                        <h2>Your Code To Activate Account</h2>
                        <h2>OTP: %s</h2>
                    </div>
                    <div style="background: red;">
                        <div style="background: white;" >In order to activate account via link, just click on the link below</div>
                        <a href="http://localhost:8080/api/v1/auth/test/%s" target="_blank">activate</a>
                        <a><h2>Click</h2></a>
                    </div>
                    </body>
                    </html>""".formatted(username, otp, username);
            mimeMessage.setContent(content, "text/html");
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Async
    public void activateAccountWithOTP(ActivateAccountDTO dto) {
        AuthUser authUser = authUserRepository.findByUsername(dto.username())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (authUser.getActive().equals(AuthUser.Active.ACTIVE)) {
            throw new RuntimeException("Account already activated");
        }

        if (!authUser.getOtp().equals(dto.otp())) {
            throw new RuntimeException("OTP is not correct");
        }
        authUser.setActive(AuthUser.Active.ACTIVE);
        authUserRepository.save(authUser);
    }

    public void activateAccount(String username) {
AuthUser authUser = authUserRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (authUser.getActive().equals(AuthUser.Active.ACTIVE)) {
            throw new RuntimeException("Account already activated");
        }

        authUser.setActive(AuthUser.Active.ACTIVE);
        authUserRepository.save(authUser);
    }
}