package com.hoaxify.ws.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hoaxify.ws.user.validation.UniqueEmail;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity()
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = {"email"}))
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;

    private String email;

    @JsonIgnore
    private String password;
    @JsonIgnore

    private boolean active = false;
    @JsonIgnore

    private String activationToken;

    private String image;
}
