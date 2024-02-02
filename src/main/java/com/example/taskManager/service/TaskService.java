package com.example.taskManager.service;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import com.example.taskManager.model.Task;
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
        entityManager.flush();
    }

    @Transactional
    public void deleteTask(Long taskId) {
        Task task = entityManager.find(Task.class, taskId);
        if (task != null) {
            entityManager.remove(task);
        }
    }
    
     @Transactional
    public void editTask(Long taskId) {
        Task task = entityManager.find(Task.class, taskId);
        
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
}

