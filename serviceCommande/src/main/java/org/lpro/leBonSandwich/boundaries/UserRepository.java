package org.lpro.leBonSandwich.boundaries;

import java.util.Optional;

import org.lpro.leBonSandwich.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
    Optional<User> findByUsername(String username);
}
