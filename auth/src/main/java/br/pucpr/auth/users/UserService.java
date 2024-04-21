package br.pucpr.auth.users;

import br.pucpr.auth.error.BadRequestException;
import br.pucpr.auth.error.NotFoundException;
import br.pucpr.auth.roles.RoleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    public User save(User user) {
        var u = userRepository.findByEmail(user.getEmail());
        if (u.isPresent()) {
            throw new BadRequestException("E-mail duplicado!");
        }
        var saved = userRepository.save(user);
        log.info("Usuario inserido: {}", saved.getName());
        return saved;
    }

    public List<User> findAll(String sortDir, String role) {
        if (role != null && !role.isBlank()) {
            return userRepository.findByRole(role);
        }

        if (sortDir.equals("ASC")) {
            return userRepository.findAll(Sort.by("name").ascending());
        }
        return userRepository.findAll(Sort.by("name").descending());
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public boolean deleteById(Long id) {
        var user = findById(id);
        if (user.isPresent()) {
            userRepository.delete(user.get());
            return true;
        }
        return false;
    }

    public boolean grant(Long id, String roleName) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        if (user.getRoles().stream().anyMatch(r -> r.getName().equals(roleName))) {
            return false;
        }

        var role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new BadRequestException("Papel inválido: " + roleName));

        user.getRoles().add(role);
        userRepository.save(user);
        log.info("Papel {} dado ao usuário {}", role.getName(), user.getId());
        return true;
    }
}
