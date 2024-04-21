package br.pucpr.auth.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUserRequest(
    @NotBlank String name,
    @Email String email,
    @NotBlank String password
) {
    public User toEntity() {
        return new User(name, email, password);
    }
}
