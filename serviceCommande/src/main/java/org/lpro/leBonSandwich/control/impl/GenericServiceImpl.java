package org.lpro.leBonSandwich.control.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import org.lpro.leBonSandwich.boundaries.UserRepository;
import org.lpro.leBonSandwich.entity.Commande;
import org.lpro.leBonSandwich.entity.User;
import org.lpro.leBonSandwich.control.GenericService;
import org.lpro.leBonSandwich.boundaries.CommandeRessource;

@Service
public class GenericServiceImpl implements GenericService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommandeRessource commandeRessource;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> findAllUsers() {
        return (List<User>)userRepository.findAll();
    }

    @Override
    public List<Commande> findAllCommandes() {
        return (List<Commande>)commandeRessource.findAll();
    }
}
