package com.example.taskManager.service;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import com.example.taskManager.model.Feedback;
import com.example.taskManager.model.Task;
import com.example.taskManager.model.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

@Stateless
public class TaskService {

    @PersistenceContext
    private EntityManager entityManager;


    @Transactional
    public void createTask(Task task) {
        entityManager.persist(task);
    }

    @Transactional
    public void deleteTask(Long taskId) {
        Task task = entityManager.find(Task.class, taskId);
        if (task != null) {
            List<User> assignees = task.getAssignees();        
            if (assignees != null) {
                for (User user : assignees) {
                    user.getAssignedTasks().remove(task);
                }
            }
            entityManager.remove(task);
        }
    }
    
    public List<Task> getAllTasks() {
        return entityManager.createQuery("SELECT t FROM Task t", Task.class).getResultList();
    }

    public Task getTasksById(Long id) {
        return entityManager.find(Task.class, id);
    }
    
    @Transactional
    public void updateTask(Task taskToEdit) {
        entityManager.merge(taskToEdit); 
    }

    public void addFeedback(Long taskId, Feedback feedback) {
        Task task = entityManager.find(Task.class, taskId);
        task.setFeedback(feedback);
        entityManager.merge(task);
    }

    public void addComment(Task task, String commentText) {
        task.setComment(commentText);
        entityManager.merge(task);
    }
}

