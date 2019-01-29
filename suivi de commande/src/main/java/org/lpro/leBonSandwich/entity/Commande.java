package org.lpro.leBonSandwich.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;

@Entity
@Table(name = "commande")
public class Commande {

    @Id
    private String id;
    private String nom;
    @Column(name="created_at", nullable=false)
    private Date createdAt;
    private Date livraison;
    private int status;

    public Commande () {}

    public Commande (String nom, Date created, Date livraison, int status) {
        //SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        this.nom  = nom;
        this.createdAt = created;
        this.livraison = livraison;
        this.status = status;
    }

    public Date getcreatedAt() {
		return createdAt;
	}

	public void setcreatedAt(Date createdAt) {
		this.createdAt = createdAt;
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