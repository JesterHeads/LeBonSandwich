package org.lpro.leBonSandwich.boundaries;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.lpro.leBonSandwich.control.GenericService;
import org.lpro.leBonSandwich.entity.User;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/oauthjwt")
public class ResourceController {
    @Autowired
    private GenericService userService;

    @GetMapping(value ="/commandes")
    @PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
    public List<Commande> getUser(){
        return userService.findAllCommandes();
    }

    @GetMapping(value ="/users")
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    public List<User> getUsers(){
        return userService.findAllUsers();
    }
}
