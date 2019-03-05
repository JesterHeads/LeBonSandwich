 package org.lpro.leBonSandwich.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "item")
public class Item {
	
    @Id
    private String id;
    private String uri;
    private String libelle;
    private float tarif;
    private int quantite;
    
    @ManyToOne(fetch=FetchType.LAZY, optional=false)
    @JoinColumn(name = "command_id", nullable = false)
    private Commande commande;

    public Item () {}

    public Item (String uri, String libelle, float tarif, int quantite) {
    	
        this.uri      = uri;
        this.libelle  = libelle;
        this.tarif    = tarif;
        this.quantite = quantite;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public float getTarif() {
		return tarif;
	}

	public void setTarif(float tarif) {
		this.tarif = tarif;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

    
}