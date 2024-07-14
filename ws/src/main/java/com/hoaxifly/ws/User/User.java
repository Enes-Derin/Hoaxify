package com.hoaxifly.ws.User;

import com.hoaxifly.ws.User.Validation.UniqueEmail;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = {"email"}))
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank(message = "{hoaxify.constraint.username.notBlank}")
    @Size(min = 4, max = 255)
    String username;

    @NotBlank
    @Email
    @UniqueEmail(message = "{hoaxify.constraint.email.uniqueEmail}")
    String email;

    @Size(min = 8, max = 255)
    @Pattern(regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "{hoaxify.constraint.password.pattern}")
    String password;
}
