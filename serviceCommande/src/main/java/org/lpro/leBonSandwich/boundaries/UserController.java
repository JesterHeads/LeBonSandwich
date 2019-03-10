package org.lpro.leBonSandwich.boundaries;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.lpro.leBonSandwich.boundaries.UserRepository;
import org.lpro.leBonSandwich.entity.Commande;
import org.springframework.web.bind.annotation.RequestBody;
import org.lpro.leBonSandwich.entity.User;
import org.lpro.leBonSandwich.exception.NotFound;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.hateoas.ExposesResourceFor;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Jwts;
import java.util.Date;


@RestController
@RequestMapping(value="/users", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(User.class)
public class UserController {

    private UserRepository ur;
    BCryptPasswordEncoder encoder;

    public UserController (UserRepository ur){
        this.ur = ur;
    }

    @GetMapping()
    public ResponseEntity<?>  getUsers(){
        return new ResponseEntity<>("lol",HttpStatus.OK);
    }

    @PostMapping(value="/signin")
    public ResponseEntity<?> signin(@RequestBody User user) throws NotFound{
        Optional<User> u = ur.findByUsername(user.getUsername());
        if(u.isPresent()){
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
            if(encoder.matches(user.getPassword(),u.get().getPassword())){
                String token = generateToken();
                u.get().setToken(token);
                ur.save(u.get());
                return new ResponseEntity<>(u.get(),HttpStatus.OK);
            }
        }
        throw new NotFound("Utilisateur inconnu");
    }

    private String generateToken() {
        String jwtToken = Jwts.builder()
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "cmdSecret")
                .compact();
        return jwtToken;
    }

    @PostMapping()
    public ResponseEntity<?> inscription(@RequestBody User user){
        return new ResponseEntity<>("lol",HttpStatus.OK);
    }



}
