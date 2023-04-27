package org.example.domains;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@ToString(callSuper = true)
public class AuthUser extends Auditable<Integer> {

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String otp;

    @Enumerated(EnumType.ORDINAL)
    @Column(columnDefinition = "smallint default 1")
    private Active active;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(10) default 'USER'")
    private Role role;

    public enum Role {
        ADMIN, USER
    }

    public enum Active {
        BLOCKED, IN_ACTIVE, ACTIVE,
    }

    @Builder(builderMethodName = "childBuilder")
    public AuthUser(Integer integer, LocalDateTime createdAt, LocalDateTime updatedAt, Integer createdBy, Integer updatedBy, boolean deleted, String username, String email, String phoneNumber, String password, Active active, Role role, String otp
    ) {
        super(integer, createdAt, updatedAt, createdBy, updatedBy, deleted);
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.active = active;
        this.role = role;
        this.otp = otp;
    }

}
