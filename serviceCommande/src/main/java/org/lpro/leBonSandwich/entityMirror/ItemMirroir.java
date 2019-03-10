package org.lpro.leBonSandwich.entityMirror;

import org.springframework.hateoas.core.Relation;

@Relation(collectionRelation = "items")
public class ItemMirroir {

    private String uri;
    private int quantite;
    

    public ItemMirroir (String uri, int quantite) {
        this.uri = uri;
        this.quantite = quantite;
    }

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public int getQuantite() {
		return quantite;
	}


	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}
    
    
}