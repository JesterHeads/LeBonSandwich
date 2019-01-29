package org.lpro.leBonSandwich.boundaries;

import java.util.List;
import java.util.Date;
import org.lpro.leBonSandwich.entity.Commande;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandeRessource extends JpaRepository<Commande, String> {

    List<Commande> findAllByOrderByCreatedAtAscLivraisonAsc(Pageable page);

    List<Commande> findByStatusEqualsOrderByCreatedAtAscLivraisonAsc(int status , Pageable page);
}