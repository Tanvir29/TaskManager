/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.taskManager.service;

import com.example.taskManager.model.FeedBack;
import jakarta.ejb.Stateless;
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
public class FeedBackService {

    @PersistenceContext
    private EntityManager entityManager;
    
    @Inject
    private UserService userService;
    
    @Transactional
    public void createFeedback(FeedBack feedback) {
        feedback.setTimestamp(LocalDateTime.now());
        feedback.setCommenter(userService.getAuthUser());
        entityManager.persist(feedback);
    }

    @Transactional
    public void deleteFeedback(FeedBack feedback) {
        if (feedback != null) {
            entityManager.remove(feedback);
        }
    }

    public List<FeedBack> getAllFeedbacks() {
        return entityManager.createQuery("SELECT f FROM FeedBack f", FeedBack.class).getResultList();
    }

    public FeedBack getFeedbackById(Long id) {
        return entityManager.find(FeedBack.class, id);
    }
}