package org.lpro.leBonSandwich.boundaries;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.Optional;
import java.util.UUID;

import org.lpro.leBonSandwich.entity.Commande;
import org.lpro.leBonSandwich.exception.BadRequest;
import org.lpro.leBonSandwich.exception.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



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

    @GetMapping
    public ResponseEntity<?> getAllCommandes() {
        Iterable<Commande> allCommandes = cr.findAll();
        return new ResponseEntity<>(allCommandes,HttpStatus.OK);
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<?> getCommandeWithId (@PathVariable("id") String id) throws NotFound{
        return Optional.ofNullable(cr.findById(id))
                .filter(Optional::isPresent)
                .map(commande -> new ResponseEntity<>(commande.get(),HttpStatus.OK))
                .orElseThrow(() -> new NotFound("Commande not found"));
    }

    @PostMapping
    public ResponseEntity<?> postCommande(@RequestBody Commande ctg) throws BadRequest{
        ctg.setId(UUID.randomUUID().toString());
        String errors = ctg.isValid();
        if(errors.isEmpty()){
            Commande newCtg = cr.save(ctg);
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.setLocation(linkTo(CommandeRepresentation.class).slash(newCtg.getId()).toUri());
            return new ResponseEntity<>(newCtg,responseHeaders,HttpStatus.CREATED);
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

//    @DeleteMapping(value="/id")
//    public ResponseEntity<?> deleteCommande(@PathVariable("id") String id) throws NotFound,MethodNotAllowed{
//
//    }
}


