package org.lpro.leBonSandwich.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonFilter;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "app_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private String id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String email;

    private String token;

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    /*
     * optionnel si on ne gere pas les roles
    */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns
            = @JoinColumn(name = "user_id",
            referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id",
                    referencedColumnName = "id"))
    private List<Role> roles;

    public User(){

    }

    public User (String prenom, String nom, String username, String email){
        this.firstName = prenom;
        this.lastName = nom;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }


    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    

    public String isValid(){
        String valid = "";
        if(this.firstName == null || this.firstName.isEmpty()){
            valid += "veuillez saisir un prénom"+System.getProperty("line.separator");
        }
        if(this.lastName == null || this.lastName.isEmpty()){
            valid += "Veuillez saisir un nom de famille"+System.getProperty("line.separator");
        }
        if(this.username == null || this.username.isEmpty()){
            valid += "Veuillez saisir un nom d'utilisateur valide"+System.getProperty("line.separator");
        }
        if(this.email == null || this.email.isEmpty()){
            valid += "Veuillez saisir une adresse email valide"+System.getProperty("line.separator");
        }
        return valid;
    }
}

