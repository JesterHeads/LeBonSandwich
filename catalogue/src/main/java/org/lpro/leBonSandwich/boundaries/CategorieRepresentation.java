package org.lpro.leBonSandwich.boundaries;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.lpro.leBonSandwich.entity.Categorie;
import org.lpro.leBonSandwich.exception.BadRequest;
import org.lpro.leBonSandwich.exception.NotFound;
import org.lpro.leBonSandwich.exception.MethodNotAllowed;
import java.security.cert.PKIXRevocationChecker.Option;
import java.util.Optional;
import java.util.UUID;



//Permet de définir un controller REST
@RestController
// Définition d'une route par défaut
@RequestMapping(value="/categories", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(Categorie.class)
public class CategorieRepresentation {
    @Autowired
    private final CategorieRessource cr;

    public CategorieRepresentation(CategorieRessource cr) {
        this.cr = cr;
    } 

    @GetMapping
    public ResponseEntity<?> getAllIntervenants() {
        Iterable<Categorie> allIntervenant = cr.findAll();
        return new ResponseEntity<>(allIntervenant,HttpStatus.OK);
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<?> getCategorieWithId (@PathVariable("id") String id) throws NotFound{
        return Optional.ofNullable(cr.findById(id))
                .filter(Optional::isPresent)
                .map(categorie -> new ResponseEntity<>(categorie.get(),HttpStatus.OK))
                .orElseThrow(() -> new NotFound("Categorie not found"));
    }

    @PostMapping
    public ResponseEntity<?> postCategorie(@RequestBody Categorie ctg) throws BadRequest{
        ctg.setId(UUID.randomUUID().toString());
        String errors = ctg.isValid();
        if(errors.isEmpty()){
            Categorie newCtg = cr.save(ctg);
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.setLocation(linkTo(CategorieRepresentation.class).slash(newCtg.getId()).toUri());
            return new ResponseEntity<>(newCtg,responseHeaders,HttpStatus.CREATED);
        } else {
            throw new BadRequest(errors);
        }
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<?> putSandwich(@RequestBody Categorie categorieUpdated, @PathVariable("id") String id) throws BadRequest,NotFound{
        return cr.findById(id)
                .map(categorie -> {
                    categorie.setId(categorie.getId());
                    categorie.setDesc(categorieUpdated.getDesc());
                    categorie.setNom(categorieUpdated.getNom());
                    String errors = categorie.isValid();
                    if(errors.isEmpty()){
                        cr.save(categorie);
                        HttpHeaders responseHeaders = new HttpHeaders();
                        responseHeaders.setLocation(linkTo(CategorieRepresentation.class).slash(categorie.getId()).toUri());
                        return new ResponseEntity<>(categorie, responseHeaders, HttpStatus.CREATED);
                    } else throw new BadRequest(errors);
                }).orElseThrow(() -> new NotFound("Intervenant inexistant"));
    }

    @DeleteMapping(value="/id")
    public ResponseEntity<?> deleteCategorie(@PathVariable("id") String id) throws NotFound,MethodNotAllowed{
        return cr.findById(id).map( categorie -> {
            cr.delete(categorie);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }).orElseThrow( () -> new NotFound("Categorie not found"));
    }
}



