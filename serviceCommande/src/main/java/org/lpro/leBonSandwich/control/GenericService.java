package org.lpro.leBonSandwich.control;

import java.util.List;
import org.lpro.leBonSandwich.entity.Commande;
import org.lpro.leBonSandwich.entity.User;

public interface GenericService {
    User findByUsername(String username);

    List<User> findAllUsers();

    List<Commande> findAllCommandes();
}
