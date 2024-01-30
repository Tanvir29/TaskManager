/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.taskManager.managedBean;

import com.example.taskManager.model.FeedBack;
import com.example.taskManager.service.FeedBackService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

/**
 *
 * @author saurav
 */
@Named
@RequestScoped
public class FeedBackBean {

    @Inject
    private FeedBackService feedBackService;

    private FeedBack feedback;
    private List<FeedBack> feedbackList;

    public FeedBack getFeedback() {
        return feedback;
    }

    public void setFeedback(FeedBack feedback) {
        this.feedback = feedback;
    }

    public List<FeedBack> getFeedbackList() {
        return feedbackList;
    }

    public void setFeedbackList(List<FeedBack> feedbackList) {
        this.feedbackList = feedbackList;
    }

    @PostConstruct
    public void init() {
        feedback = new FeedBack();
        loadFeedbackList();
    }

    public void loadFeedbackList() {
        feedbackList = feedBackService.getAllFeedbacks();
    }

    public void createFeedback() {
        feedBackService.createFeedback(feedback);
        feedback = new FeedBack();
        loadFeedbackList();
    }

    public void deleteFeedback(FeedBack feedback) {
        feedBackService.deleteFeedback(feedback.getId());
        loadFeedbackList();
    }
}
