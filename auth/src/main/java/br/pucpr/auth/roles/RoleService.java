package br.pucpr.auth.roles;

import br.pucpr.auth.error.BadRequestException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class RoleService {
    private RoleRepository roleRepository;

    public Role save(Role role) {
        var u = roleRepository.findByName(role.getName());
        if (u.isPresent()) {
            throw new BadRequestException("Papel já existe!");
        }
        var saved = roleRepository.save(role);
        log.info("Papel inserido: {}", saved.getName());
        return saved;
    }

    public List<Role> findAll() {
        return roleRepository.findAll(Sort.by("name").ascending());
    }
}
