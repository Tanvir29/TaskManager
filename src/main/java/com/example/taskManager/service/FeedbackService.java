/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.taskManager.service;

import com.example.taskManager.model.Feedback;
import com.example.taskManager.model.User;
import com.example.taskManager.repository.UserRepository;
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
public class FeedbackService {

    @PersistenceContext
    private EntityManager entityManager;
    
    @Inject
    private UserRepository userRepository;
    
    @Transactional
    public void createFeedback(Feedback feedback) {
        //Feedback feedback = new Feedback();
        feedback.setTimestamp(LocalDateTime.now());
        User commenter = userRepository.findUserById(1);
        feedback.setCommenter(commenter);
        entityManager.persist(feedback);
    }

    @Transactional
    public void deleteFeedback(Feedback feedback) {
        if (feedback != null) {
            entityManager.remove(feedback);
        }
    }

    public List<Feedback> getAllFeedbacks() {
        return entityManager.createQuery("SELECT f FROM Feedback f", Feedback.class).getResultList();
    }

    public Feedback getFeedbackById(Long id) {
        return entityManager.find(Feedback.class, id);
    }
}