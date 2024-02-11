package com.example.taskManager.service;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import com.example.taskManager.model.Feedback;
import com.example.taskManager.model.Task;
import com.example.taskManager.model.TaskPriority;
import com.example.taskManager.model.TaskStatus;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.time.LocalDate;
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
            Task managedTask = getTaskById(task.getId());
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
    public Task getTaskById(Long id) {
        return entityManager.find(Task.class, id);
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateTask(Task task) {
        entityManager.merge(task); 
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void addFeedback(Long taskId, Feedback feedback) {
        Task task = entityManager.find(Task.class, taskId);
        task.setFeedback(feedback);
        entityManager.merge(task);
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Task> filterTasks(String statusFilter, String priorityFilter, LocalDate dueDateFilter) {
        String queryString = "SELECT t FROM Task t WHERE 1=1";
        if (!"Blank".equals(statusFilter)) {
            queryString += " AND t.status = :statusFilter";
        }
        if (!"Blank".equals(priorityFilter)) {
            queryString += " AND t.priority = :priorityFilter";
        }
        if (dueDateFilter != null) {
            queryString += " AND t.dueDate < :dueDateFilter";
        }
        
        TypedQuery<Task> query = entityManager.createQuery(queryString, Task.class);
        
        if (!"Blank".equals(statusFilter)) {
            query.setParameter("statusFilter", TaskStatus.valueOf(statusFilter));
        }
        if (!"Blank".equals(priorityFilter)) {
            query.setParameter("priorityFilter", TaskPriority.valueOf(priorityFilter));
        }
        if (dueDateFilter != null) {
            query.setParameter("dueDateFilter", dueDateFilter);
        }
        
        List<Task> tasks = query.getResultList();
        
        return tasks;
    }
}

