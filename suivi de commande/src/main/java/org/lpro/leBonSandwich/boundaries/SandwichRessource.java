package org.lpro.leBonSandwich.boundaries;

import org.springframework.data.jpa.repository.JpaRepository;
import org.lpro.leBonSandwich.entity.Sandwich;

public interface SandwichRessource extends JpaRepository<Sandwich, String> {

}