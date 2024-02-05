/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.taskManager.managedBean;

import com.example.taskManager.model.Feedback;
import com.example.taskManager.service.FeedbackService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author saurav
 */
@Named
@RequestScoped
public class FeedbackBean {

    @Inject
    private FeedbackService feedBackService;
    
    private Feedback feedback;
    
    private Long taskId;
    
    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }
    public Feedback getFeedback() {
        return feedback;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }

    @PostConstruct
    public void init() {
        feedback = new Feedback();
    }

    
    public String createFeedbackForm(Long taskId){
        this.taskId = taskId;
        return "/app/feedback/feedbackCreate";
    }
    public String createFeedback() {
        feedBackService.createFeedback(taskId,feedback);
        return "/app/taskView/taskList?faces-redirect=true";
    }

    public void deleteFeedback(Feedback feedback) {
        feedBackService.deleteFeedback(feedback);
    }
}
