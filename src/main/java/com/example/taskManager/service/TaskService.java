package com.example.taskManager.service;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import com.example.taskManager.model.Feedback;
import com.example.taskManager.model.Task;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class TaskService {

    @PersistenceContext
    private EntityManager entityManager;


    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createTask(Task task) {
        entityManager.persist(task);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteTask(Task task) {
        if (entityManager.contains(task)){
            entityManager.remove(task);
        }
        else{
            Task managedTask = getTasksById(task.getId());
            if (managedTask != null){
                entityManager.remove(managedTask);
            }
        }
    }
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Task> getAllTasks() {
        return entityManager.createQuery("SELECT t FROM Task t", Task.class).getResultList();
    }
    
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Task getTasksById(Long id) {
        return entityManager.find(Task.class, id);
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateTask(Task taskToEdit) {
        entityManager.merge(taskToEdit); 
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void addFeedback(Long taskId, Feedback feedback) {
        Task task = entityManager.find(Task.class, taskId);
        task.setFeedback(feedback);
        entityManager.merge(task);
    }
}

