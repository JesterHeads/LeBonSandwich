package org.lpro.oauthjwt.boundary;

import org.lpro.oauthjwt.entity.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
}
