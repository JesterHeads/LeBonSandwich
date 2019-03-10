package main.java.org.lpro.leBonSandwich.boundaries;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.lpro.leBonSandwich.control.GenericService;
import org.lpro.leBonSandwich.entity.Commande;
import org.springframework.web.bind.annotation.RequestBody;
import org.lpro.leBonSandwich.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.hateoas.ExposesResourceFor;

@RestController
@RequestMapping(value="/users", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(User.class)
public class UserController {
    @Autowired
    private GenericService userService;

    @GetMapping()
    public ResponseEntity<?>  getUsers(){
        return new ResponseEntity<>("lol",HttpStatus.OK);
    }

    @PostMapping(value="/signin")
    public ResponseEntity<?> signin(){
        return new ResponseEntity<>("lol",HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> inscription(@RequestBody User user){
        return new ResponseEntity<>("lol",HttpStatus.OK);
    }



}
