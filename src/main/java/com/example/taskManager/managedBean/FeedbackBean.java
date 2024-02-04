/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.taskManager.managedBean;

import com.example.taskManager.model.Feedback;
import com.example.taskManager.model.Task;
import com.example.taskManager.service.FeedbackService;
import com.example.taskManager.service.TaskService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;

/**
 *
 * @author saurav
 */
@Named
@RequestScoped
public class FeedbackBean {

    @Inject
    private FeedbackService feedBackService;
    
    @Inject
    private TaskService taskService;
    
    private Feedback feedback;
    
    private Long taskId;
    private List<Feedback> feedbackList;
    
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

    public List<Feedback> getFeedbackList() {
        return feedbackList;
    }

    public void setFeedbackList(List<Feedback> feedbackList) {
        this.feedbackList = feedbackList;
    }

    @PostConstruct
    public void init() {
        feedback = new Feedback();
        loadFeedbackList();
    }

    public void loadFeedbackList() {
        feedbackList = feedBackService.getAllFeedbacks();
    }
    
    public String createFeedbackForm(Long taskId){
        this.taskId = taskId;
        return "/app/feedback/feedbackCreate";
    }
    public String createFeedback() {
        feedBackService.createFeedback(taskId,feedback);
        //feedback = new Feedback();
        return "/app/taskView/taskList";
    }

    public void deleteFeedback(Feedback feedback) {
        feedBackService.deleteFeedback(feedback);
    }
}
