package org.lpro.leBonSandwich.boundaries;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

import org.lpro.leBonSandwich.entity.Categorie;
import org.lpro.leBonSandwich.entity.Sandwich;

public interface CategorieRessource extends JpaRepository<Categorie, String> {
	Set<Categorie> findBySandwichs_Id(String sandwichId);	
}