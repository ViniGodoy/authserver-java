package br.pucpr.auth.users;

import br.pucpr.auth.error.BadRequestException;
import br.pucpr.auth.error.Validators;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/users")
@RestController
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> insert(@Valid @RequestBody CreateUserRequest user) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new UserDto(userService.save(user.toEntity())));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAll(
            @RequestParam(required = false) String sortDir,
            @RequestParam(required = false) String role
    ) {
        if (role != null) role = role.toUpperCase();
        sortDir = Validators.sortDir(sortDir);

        var users = userService
                .findAll(sortDir, role).stream()
                .map(UserDto::new)
                .toList();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Long id) {
        return userService.findById(id)
                .map(UserDto::new)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        return userService.deleteById(id) ?
                ResponseEntity.ok().build() :
                ResponseEntity.notFound().build();
    }

    @PutMapping("/users/{id}/roles/{role}")
    public ResponseEntity<Void> grant(
            @PathVariable Long id,
            @PathVariable String role
    ) {
        return userService.grant(id, role.toUpperCase()) ?
                ResponseEntity.ok().build() :
                ResponseEntity.noContent().build();
    }
}
