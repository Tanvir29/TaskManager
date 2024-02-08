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
import com.example.taskManager.model.UserRole;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
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
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void saveUser(User user) {
        entityManager.persist(user);
    }
    
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<User> findAllUsers() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }
    
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public User findUserById(long id) {
        return entityManager.find(User.class, id);
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteUser(User user) {
        if (entityManager.contains(user)){
            entityManager.remove(user);
        }
        else{
            User managedUser = findUserById(user.getId());
            if (managedUser != null){
                entityManager.remove(managedUser);
            }
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public User findAdmin() {
        TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u WHERE u.role = :role", User.class);

        query.setParameter("role", UserRole.ADMIN);
        User adminUser = query.getSingleResult();
        
        return adminUser;
    }
}

