package br.pucpr.auth;

import br.pucpr.auth.roles.Role;
import br.pucpr.auth.roles.RoleRepository;
import br.pucpr.auth.users.User;
import br.pucpr.auth.users.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class BootStrapper implements ApplicationListener<ContextRefreshedEvent> {
    private RoleRepository roleRepository;
    private UserRepository userRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        var adminRole = roleRepository.findByName("ADMIN").orElseGet(() -> {
            var r = roleRepository.save(new Role("ADMIN", "System Administrator"));
            roleRepository.save(new Role("USER", "Premium User"));
            log.info("ADMIN and USER roles created");
            return r;
        });

        if (userRepository.findByRole("ADMIN").isEmpty()) {
            var admin = new User(
                    "Auth Server Administrator",
                    "admin@authserver.com",
                    "admin"
            );
            admin.getRoles().add(adminRole);
            userRepository.save(admin);
            log.info("Admin user created");
        }
    }
}
