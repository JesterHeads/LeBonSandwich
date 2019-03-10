 package org.lpro.leBonSandwich.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "commande")
public class Commande {

    @Id
    private String id;
    @Column(name="created_at")
    private Date createdAt = new Date();
    private Date livraison;
    private String nom;
    private String mail;
    private Float montant;
    private Float remise;
    private String token;
    @Column(name="carte_paiement")
    private String cartePaiement;
    @Column(name="expiration_paiement")
    private String expirationPaiement;
    @Column(name="ref_paiement")
    private String refPaiement;
    @Column(name="date_paiement")
    private Date datePaiement;
    @Column(name="mode_paiement")
    private Integer modePaiement;
    private Integer status = 1;
    
    @OneToMany(mappedBy="commande", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    private Set<Item> items;

    public Commande () {}

    public Commande (String nom, Date livraison, String mail) {
        this.nom  = nom;
        this.livraison = livraison;
        this.mail = mail;
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

	public Integer getStatus() {
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

    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token the token to set
     */
    public void setToken(String token) {
        System.out.println(token);
        this.token = token;
    }

    /**
     * @return the mail
     */
    public String getMail() {
        return mail;
    }

    /**
     * @param mail the mail to set
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

	public Set<Item> getItems() {
		return items;
	}

	public void setItems(Set<Item> items) {
		this.items = items;
	}

	public String getRefPaiement() {
		return refPaiement;
	}

	public void setRefPaiement(String refPaiement) {
		this.refPaiement = refPaiement;
	}

	public Date getDatePaiement() {
		return datePaiement;
	}

	public void setDatePaiement(Date datePaiement) {
		this.datePaiement = datePaiement;
	}

	public Integer getModePaiement() {
		return modePaiement;
	}

	public void setModePaiement(Integer modePaiement) {
		this.modePaiement = modePaiement;
	}

	public String getCartePaiement() {
		return cartePaiement;
	}

	public void setCartePaiement(String cartePaiement) {
		this.cartePaiement = cartePaiement;
	}

	public String getExpirationPaiement() {
		return expirationPaiement;
	}

	public void setExpirationPaiement(String expirationPaiement) {
		this.expirationPaiement = expirationPaiement;
	}
	
	
    
    
}