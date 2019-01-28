package org.lpro.leBonSandwich.boundaries;

import org.lpro.leBonSandwich.entity.Commande;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandeRessource extends JpaRepository<Commande, String> {

}