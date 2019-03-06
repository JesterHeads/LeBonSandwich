package org.lpro.leBonSandwich.boundaries;

import java.util.List;
import java.util.Optional;

import org.lpro.leBonSandwich.entity.Item;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRessource extends JpaRepository<Item, String> {
}