package org.lpro.leBonSandwich.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "commande")
public class Commande {

    @Id
    private String id;
    private String nom;
    private Date created_at;
    private Date livraison;
    private int status;

    public Commande () {}

    public Commande (String nom, Date created_at, Date livraison, int status) {
        this.nom  = nom;
        this.created_at = created_at;
        this.livraison = livraison;
        this.status = status;
    }

    public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public Date getLivraison() {
		return livraison;
	}

	public void setLivraison(Date livraison) {
		this.livraison = livraison;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    
    public String isValid(){
        String valid = "";
        if(this.nom == null || this.nom.isEmpty()){
            valid += "le nom de la commande doit être défini"+System.getProperty("line.separator");
        }
        return valid;
    }
}