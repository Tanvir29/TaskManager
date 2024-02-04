/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.taskManager.service;

/**
 *
 * @author hasan
 */
import com.example.taskManager.model.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

/**
 *
 * @author Fardin
 */
@Stateless
public class UserService {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Transactional
    public void saveUser(User user) {
        entityManager.persist(user);
    }

    public List<User> findAllUsers() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }
   
    public User findUserById(long id) {
        return entityManager.find(User.class, id);
    }
}

