package org.lpro.leBonSandwich.boundaries;

import org.springframework.data.jpa.repository.JpaRepository;
import org.lpro.leBonSandwich.entity.Categorie;

public interface CategorieRessource extends JpaRepository<Categorie, String> {

}