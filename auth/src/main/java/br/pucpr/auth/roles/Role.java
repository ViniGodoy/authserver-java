package br.pucpr.auth.roles;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor @RequiredArgsConstructor
public class Role {
    @Id @GeneratedValue
    private Long id;

    @NotBlank @NonNull
    @Column(unique = true)
    private String name;

    @NotNull @NonNull
    private String description;
}
