package org.lpro.leBonSandwich.entity;

import javax.persistence.Entity;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "categorie")
public class Categorie {

    @Id
    private String id;
    private String nom;
    private String desc;

    public Categorie() {}

    public Categorie( String nom, String desc) {
        this.nom = nom;
        this.desc = desc;
    }

    public String getId(){
        return this.id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }



    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", nom='" + getNom() + "'" +
            ", desc='" + getDesc() + "'" +
            "}";
    }

    public String isValid(){
        String valid = "";
        if(this.nom == null || this.nom.isEmpty()){
            valid += "le nom de la catégorie doit être défini"+System.getProperty("line.separator");
        }
        if(this.desc == null || this.desc.isEmpty()){
            valid += "Veuillez mettre une description pour cette catégorie"+System.getProperty("line.separator");
        }
        return valid;
    }
    

}