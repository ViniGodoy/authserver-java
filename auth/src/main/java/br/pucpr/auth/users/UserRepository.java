package br.pucpr.auth.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query(
            "select distinct u from User u" +
                    " join u.roles r" +
                    " where r.name = upper(:role)" +
                    " order by u.name"
    )
    List<User> findByRole(String role);
}
