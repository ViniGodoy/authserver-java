package br.pucpr.auth.users;

import br.pucpr.auth.roles.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="tblUser")
@Data
@NoArgsConstructor @RequiredArgsConstructor
public class User {
    @Id @GeneratedValue
    private Long id;

    @NotBlank @NonNull
    private String name;

    @Column(unique = true)
    @Email @NonNull
    private String email;

    @NotBlank @NonNull
    private String password;

    @ManyToMany
    @JoinTable(
            name = "UserRole",
            joinColumns = @JoinColumn(name = "idUser"),
            inverseJoinColumns = @JoinColumn(name = "idRole")
    )
    private Set<Role> roles = new HashSet<>();
}
