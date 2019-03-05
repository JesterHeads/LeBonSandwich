package org.lpro.leBonSandwich.boundaries;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.lpro.leBonSandwich.entity.Commande;
import org.lpro.leBonSandwich.entity.Item;
import org.lpro.leBonSandwich.entityMirror.CommandeMirroir;
import org.lpro.leBonSandwich.entityMirror.CommandeMirroirWithDetails;
import org.lpro.leBonSandwich.entityMirror.CommandeMirroirWithToken;
import org.lpro.leBonSandwich.entityMirror.ItemMirroir;
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
import org.springframework.web.bind.annotation.RequestHeader;
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
    

    private CommandeMirroir commandeToMirror(Commande c, Boolean showToken, Boolean showDetail) {
    	CommandeMirroir cm = null;
    	
    	if(!showToken && !showDetail) {
    		cm = new CommandeMirroir(c.getId(), c.getNom(), c.getcreatedAt(), c.getLivraison(), c.getStatus(), c.getMail());
    	}else {
    		
    		 List<ItemMirroir> itemsMirroir = new ArrayList();
	   	   	 Set<Item> itemsList = c.getItems();
	   	   	 
	   	   	 if(itemsList != null && !itemsList.isEmpty()) {
	   	   		itemsList.forEach(item -> itemsMirroir.add(itemToMirror(item)));
	   	   	 }
    		
    		if(showToken) {
        		cm = new CommandeMirroirWithToken(c.getId(), c.getNom(), c.getcreatedAt(), c.getLivraison(), c.getStatus(), c.getMail(), itemsMirroir, c.getToken());
        	}else if(showDetail){
        		 cm = new CommandeMirroirWithDetails(c.getId(), c.getNom(), c.getcreatedAt(), c.getLivraison(), c.getStatus(), c.getMail(), itemsMirroir);
        	}
    	}
	   	 
    	return cm;
    }
    
    
    private ItemMirroir itemToMirror(Item i) {
    	return new ItemMirroir(i.getUri(), i.getQuantite());
    }
    

     
     private Resource<CommandeMirroir> commandeToResource(Commande commande, Boolean showToken, Boolean collection, Boolean showDetail) {
    	 
    	 Link selfLink      = linkTo(CommandeRepresentation.class).slash(commande.getId()).withSelfRel();
    	 CommandeMirroir cm = commandeToMirror(commande, showToken, showDetail);
    	 
         if (collection) {
             Link collectionLink = linkTo(CommandeRepresentation.class).withRel("collection");
             return new Resource<>(cm, selfLink, collectionLink);
         } else {
             return new Resource<>(cm, selfLink);
         }
     }
     
     
     
     private Resources<Resource<CommandeMirroir>> commandesToResource(Iterable<Commande> commandes, Boolean showToken, Boolean collection, Boolean showDetail) {
    	 Link selfLink = linkTo(CommandeRepresentation.class).withSelfRel();
    	 
    	 List<Resource<CommandeMirroir>> commandeResources = new ArrayList();
    	 commandes.forEach(commande -> commandeResources.add(commandeToResource(commande, showToken, collection, showDetail)));
         
    	 return new Resources<>(commandeResources, selfLink);
     }
     
     
     
     private String generateToken() {
    	 String jwtToken = Jwts.builder()
                 .setIssuedAt(new Date())
                 .signWith(SignatureAlgorithm.HS256, "cmdSecret")
                 .compact();
    	 return jwtToken;
     }

     
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
            List<Commande> allCommandes = null;
            if(status.isPresent()){
                if(status.get() > 0 && status.get() <= 4){
                    allCommandes = cr.findByStatusEqualsOrderByCreatedAtAscLivraisonAsc(status.get(),pageable);
                } else throw new BadRequest("Veuillez choisir un statut de commande valide");
            } else {
                allCommandes = cr.findAllByOrderByCreatedAtAscLivraisonAsc(pageable);
            }
   
            return new ResponseEntity<>(commandesToResource(allCommandes, false, false, false),HttpStatus.OK);
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<?> getCommandeWithIdAndToken (@PathVariable("id") String id, @RequestHeader(value = "x-lbs-token") String headerToken) throws NotFound{
    	Optional<Commande> commande = cr.findByIdAndToken(id, headerToken);
    	
    	if(commande.isPresent()) {
    		return new ResponseEntity<>(commandeToResource(commande.get(), false, true, true) ,HttpStatus.OK);
    	}
    	
        throw new NotFound("Commande not found");
    }

    @PostMapping
    public ResponseEntity<?> postCommande(@RequestBody Commande ctg) throws BadRequest{
        ctg.setId(UUID.randomUUID().toString());
        String errors = ctg.isValid();

        
        String jwtToken = generateToken();
        ctg.setToken(jwtToken);
        

        if(errors.isEmpty()){
            Commande newCtg = cr.save(ctg);
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.setLocation(linkTo(CommandeRepresentation.class).slash(newCtg.getId()).toUri());
            return new ResponseEntity<>(commandeToResource(newCtg, true, true, true),responseHeaders,HttpStatus.CREATED);
        } else {
            throw new BadRequest(errors);
        }
    }

    //TODO faire le put
    @PutMapping(value="/{id}")
    public ResponseEntity<?> putCommande(@RequestBody Commande commandeUpdated, @PathVariable("id") String id, @RequestHeader(value = "x-lbs-token") String headerToken) throws BadRequest,NotFound{
        
    	Optional<Commande> commande = cr.findByIdAndToken(id, headerToken);
    	
    	if(commande.isPresent()) {
    		Commande c = commande.get();
    		String errors = c.isValid();
    		
    		if(errors.isEmpty()){
                cr.save(c);
                
                HttpHeaders responseHeaders = new HttpHeaders();
                responseHeaders.setLocation(linkTo(CommandeRepresentation.class).slash(c.getId()).toUri());
                return new ResponseEntity<>(commandeToResource(c, false, true, true), responseHeaders, HttpStatus.CREATED);
            } else throw new BadRequest(errors);
    	}
    	
    	throw new NotFound("Intervenant inexistant");
    }

    
    @DeleteMapping(value="/{id}")
    public ResponseEntity<?> deleteCommande(@PathVariable("id") String id, @RequestHeader(value = "x-lbs-token") String headerToken) {
    	
    	Optional<Commande> commande = cr.findByIdAndToken(id, headerToken);
    	
    	if(commande.isPresent()) {
    		Commande c = commande.get();
    		cr.delete(c);
            return new ResponseEntity<>(commandeToResource(c, false, true, true), HttpStatus.OK);
    	}
    		
    	throw new NotFound("Commande inexistante");
    }

}



