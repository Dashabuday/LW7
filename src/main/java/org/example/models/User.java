package org.example.models;

import com.google.common.hash.Hashing;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.nio.charset.StandardCharsets;

/**
 * Class for validating and storing coordinates
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements ModelHasId {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private String login;

    private String password;

    public void setPassword(String password) {
        this.password = getHashedPassword(password);
    }

    public static String getHashedPassword(String password) {
        return Hashing.sha512().hashString(password, StandardCharsets.UTF_8).toString();
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                '}';
    }
}
