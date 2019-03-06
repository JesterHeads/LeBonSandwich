package org.lpro.leBonSandwich.boundaries;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.lpro.leBonSandwich.entity.Item;
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
@RequestMapping(value="/items", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(Item.class)
public class ItemRepresentation {
	@Autowired
	private final ItemRessource ir;

	public ItemRepresentation(ItemRessource ir) {
		this.ir = ir;
	} 
	
	private Resource<Item> ItemToResource(Item item, Boolean collection) {
		Link selfLink = linkTo(ItemRepresentation.class).slash(item.getId()).withSelfRel();
		
		if (collection) {
			Link collectionLink = linkTo(ItemRepresentation.class).withRel("collection");
		 	return new Resource<>(item, selfLink, collectionLink);
		} else {
		 	return new Resource<>(item, selfLink);
		}

	}
	 
	private Resources<Resource<Item>> ItemsToResource(Iterable<Item> items) {
	 	Link selfLink = linkTo(ItemRepresentation.class).withSelfRel();
	 
	 	List<Resource<Item>> ItemResources = new ArrayList();
	 	items.forEach(item -> ItemResources.add(ItemToResource(item, false)));
	 
	 	return new Resources<>(ItemResources, selfLink);
	}
	 
	@GetMapping
    public ResponseEntity<?> getAllItems(){
        Iterable<Item> allItem = ir.findAll();
        return new ResponseEntity<>(ItemsToResource(allItem),HttpStatus.OK);
    }

	@GetMapping(value="/{id}")
	public ResponseEntity<?> getItemWithId (@PathVariable("id") String id) throws NotFound{
		Optional<Item> Item = ir.findById(id);
		
		if(Item.isPresent()) {
			return new ResponseEntity<>(ItemToResource(Item.get(),false) ,HttpStatus.OK);
		}
		
		throw new NotFound("Item not found");
	}

	@PostMapping
	public ResponseEntity<?> postItem(@RequestBody Item it) throws BadRequest{
		it.setId(UUID.randomUUID().toString());
		
		Item newIt = ir.save(it);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setLocation(linkTo(ItemRepresentation.class).slash(newIt.getId()).toUri());
		return new ResponseEntity<>(ItemToResource(newIt, true),responseHeaders,HttpStatus.CREATED);
	}

}



