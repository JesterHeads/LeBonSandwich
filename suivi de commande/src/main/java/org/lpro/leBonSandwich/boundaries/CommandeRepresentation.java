package org.lpro.leBonSandwich.boundaries;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.lpro.leBonSandwich.entity.Commande;
import org.lpro.leBonSandwich.exception.BadRequest;
import org.lpro.leBonSandwich.exception.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


//Permet de définir un controller REST
@RestController
// Définition d'une route par défaut
@RequestMapping(value="/commandes", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(Commande.class)
public class CommandeRepresentation {
    @Autowired
    private final CommandeRessource cr;

    public CommandeRepresentation(CommandeRessource cr) {
        this.cr = cr;
    } 

     private Resources<Resource<Commande>> commandesToResource(Iterable<Commande> commandes) {
        Link selfLink = linkTo(CommandeRepresentation.class).withSelfRel();
        List<Resource<Commande>> commandeResources = new ArrayList();
        commandes.forEach(commande
                -> commandeResources.add(commandeToResource(commande, false)));
        return new Resources<>(commandeResources, selfLink);
    }
    
    private Resource<Commande> commandeToResource(Commande commande, Boolean collection) {
        Link selfLink = linkTo(CommandeRepresentation.class)
                .slash(commande.getId())
                .withSelfRel();
        if (collection) {
            Link collectionLink = linkTo(CommandeRepresentation.class).withRel("collection");
            return new Resource<>(commande, selfLink, collectionLink);
        } else {
            return new Resource<>(commande, selfLink);
        }
    }

//@RequestParam(value="page", required=false)Optional<Integer> page,
          //  @RequestParam(value="limit", required=false)Optional<Integer> limit
    @GetMapping
    public ResponseEntity<?> getAllCommandes(
        @RequestParam(value="status",required=false)Optional<Integer> status,
        @RequestParam(value="page", required=false)Optional<Integer> page,
        @RequestParam(value="limit", required=false,defaultValue="10")int limit) throws BadRequest {
            PageRequest pageable = null;
            if(page.isPresent() && page.get() > 0){
                pageable = PageRequest.of(page.get(), limit);
            } else {
                pageable = PageRequest.of(1, limit);
            }
            Iterable<Commande> allCommandes = null;
            if(status.isPresent()){
                if(status.get() > 0 && status.get() <= 4){
                    allCommandes = cr.findByStatusEqualsOrderByCreatedAtAscLivraisonAsc(status.get(),pageable);
                } else throw new BadRequest("Veuillez choisir un statut de console valide");
            } else {
                allCommandes = cr.findAllByOrderByCreatedAtAscLivraisonAsc(pageable);
            }
        return new ResponseEntity<>(commandesToResource(allCommandes),HttpStatus.OK);
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<?> getCommandeWithId (@PathVariable("id") String id) throws NotFound{
        return Optional.ofNullable(cr.findById(id))
                .filter(Optional::isPresent)
                .map(commande -> new ResponseEntity<>(commandeToResource(commande.get(), true),HttpStatus.OK))
                .orElseThrow(() -> new NotFound("Commande not found"));
    }

    @PostMapping
    public ResponseEntity<?> postCommande(@RequestBody Commande ctg) throws BadRequest{
        ctg.setId(UUID.randomUUID().toString());
        String errors = ctg.isValid();

        String jwtToken;

        if(errors.isEmpty()){
            Commande newCtg = cr.save(ctg);
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.setLocation(linkTo(CommandeRepresentation.class).slash(newCtg.getId()).toUri());

            jwtToken = Jwts.builder().setSubject(newCtg.getId()).claim("roles", "commande").setIssuedAt(new Date()) //rajouter un param dans setIssuedAt pour l'expiration
                .signWith(SignatureAlgorithm.HS256, "secretkey").compact();

            newCtg.setToken(jwtToken);
                
            return new ResponseEntity<>(commandeToResource(newCtg, true),responseHeaders,HttpStatus.CREATED);
        } else {
            throw new BadRequest(errors);
        }
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<?> putCommande(@RequestBody Commande commandeUpdated, @PathVariable("id") String id) throws BadRequest,NotFound{
        return cr.findById(id)
                .map(commande -> {
                    String errors = commande.isValid();
                    if(errors.isEmpty()){
                        cr.save(commande);
                        HttpHeaders responseHeaders = new HttpHeaders();
                        responseHeaders.setLocation(linkTo(CommandeRepresentation.class).slash(commande.getId()).toUri());
                        return new ResponseEntity<>(commande, responseHeaders, HttpStatus.CREATED);
                    } else throw new BadRequest(errors);
                }).orElseThrow(() -> new NotFound("Intervenant inexistant"));
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<?> deleteCommande(@PathVariable("id") String id) {
        Commande commande = cr.findById(id).orElseThrow(() -> new NotFound("Commande inexistante"));
        cr.delete(commande);
        return new ResponseEntity<>(commande, HttpStatus.OK);
    }

}



