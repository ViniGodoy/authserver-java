package br.pucpr.auth.users;

import br.pucpr.auth.roles.RoleRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    private final UserRepository userRepository = mock(UserRepository.class);
    private final RoleRepository roleRepository = mock(RoleRepository.class);
    private final UserService service = new UserService(userRepository, roleRepository);

    @Test
    public void deleteByIdReturnsFalseIfNoUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertFalse(service.deleteById(1L));
    }

    @Test
    public void deleteByIdReturnsTrueIfNoDeleted() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
        verify(userRepository).deleteById(1L);
        assertTrue(service.deleteById(1L));
    }
}
