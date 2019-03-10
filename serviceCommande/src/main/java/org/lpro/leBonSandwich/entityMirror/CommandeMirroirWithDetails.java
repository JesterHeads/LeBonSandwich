package org.lpro.leBonSandwich.entityMirror;

import java.util.Date;
import java.util.List;

import org.springframework.hateoas.core.Relation;


@Relation(collectionRelation = "commandes")
public class CommandeMirroirWithDetails extends CommandeMirroir {

  
    private  List<ItemMirroir> items;

    
    public CommandeMirroirWithDetails (String id, String nom, Date created, Date livraison, int status, String mail, List<ItemMirroir> items) {
    	super(id, nom, created, livraison, status, mail);
    	this.items = items;
    }

    
    public List<ItemMirroir> getItems() {
        return items;
    }
    public void setToken(List<ItemMirroir> items) {
        this.items = items;
    }
    
}