package org.lpro.leBonSandwich.boundaries;

import org.lpro.leBonSandwich.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
