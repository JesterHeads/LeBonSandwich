package org.lpro.leBonSandwich.boundaries;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.lpro.leBonSandwich.control.GenericService;
import org.lpro.leBonSandwich.entity.Commande;
import org.lpro.leBonSandwich.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/users")
public class ResourceController {
    @Autowired
    private GenericService userService;

    @GetMapping(value ="/users")
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    public List<User> getUsers(){
        return userService.findAllUsers();
    }

    @PostMapping

}
