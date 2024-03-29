/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.taskManager.service;

import com.example.taskManager.model.Feedback;
import com.example.taskManager.model.User;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author saurav
 */
@Stateless
public class FeedbackService {

    @PersistenceContext
    private EntityManager entityManager;
    @Inject
    private UserService userService;
    
    @Inject
    private TaskService taskService;
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createFeedback(Long taskId,Feedback feedback) {
        feedback.setTimestamp(LocalDateTime.now());
        User commenter = userService.findAdmin();
        feedback.setCommenter(commenter);
        entityManager.persist(feedback);
        taskService.addFeedback(taskId,feedback);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteFeedback(Feedback feedback) {
        if (feedback != null) {
            entityManager.remove(feedback);
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Feedback> getAllFeedbacks() {
        return entityManager.createQuery("SELECT f FROM Feedback f", Feedback.class).getResultList();
    }
    
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Feedback getFeedbackById(Long id) {
        return entityManager.find(Feedback.class, id);
    }
}