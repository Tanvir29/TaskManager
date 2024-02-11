/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.taskManager.service;

/**
 *
 * @author hasan
 */
import com.example.taskManager.model.Task;
import com.example.taskManager.model.TaskStatus;
import com.example.taskManager.model.User;
import com.example.taskManager.model.UserRole;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
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
        if (entityManager.contains(user)) {
            entityManager.remove(user);
        } else {
            User managedUser = findUserById(user.getId());
            if (managedUser != null) {
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

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public boolean isEmailExists(String email) {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(u) FROM User u WHERE u.email = :email", Long.class);

        query.setParameter("email", email);

        Long count = query.getSingleResult();
        return count > 0;
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Task> findUserTasks(User user, boolean isCompleted) {
        String queryString = "SELECT t FROM Task t INNER JOIN t.assignees u WHERE u.id = :userId ";
        if (isCompleted) {
            queryString += "AND t.status = :completedStatus";
        } else {
            queryString += "AND t.status != :completedStatus";
        }

        TypedQuery<Task> query = entityManager.createQuery(queryString, Task.class)
                .setParameter("userId", user.getId())
                .setParameter("completedStatus", TaskStatus.COMPLETED);

        return query.getResultList();
    }

}
