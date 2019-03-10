package org.lpro.leBonSandwich.entityMirror;

import java.util.Date;
import java.util.List;

import org.springframework.hateoas.core.Relation;


@Relation(collectionRelation = "commandes")
public class CommandeMirroirWithToken extends CommandeMirroirWithDetails {

  
    private String token = "";

    
    public CommandeMirroirWithToken (String id, String nom, Date created, Date livraison, int status, String mail, List<ItemMirroir> items, String token) {
    	super(id, nom, created, livraison, status, mail, items);
    	this.token = token;
    }

    
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        System.out.println(token);
        this.token = token;
    }
    
}