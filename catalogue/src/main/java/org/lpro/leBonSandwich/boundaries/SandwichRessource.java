package org.lpro.leBonSandwich.boundaries;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

import org.lpro.leBonSandwich.entity.Sandwich;

public interface SandwichRessource extends JpaRepository<Sandwich, String> {
	Set<Sandwich> findByCategories_Id(String categorieId);	
}