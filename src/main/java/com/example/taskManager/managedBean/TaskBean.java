/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.taskManager.managedBean;

/**
 *
 * @author hasan
 */

import com.example.taskManager.model.Task;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

@Named
@RequestScoped
public class TaskBean {

    @PersistenceContext
    private EntityManager entityManager;

    private Task task;
    private List<Task> taskList;

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    // Getters and setters
    @PostConstruct
    public void init() {
        task = new Task();
        loadTaskList();
    }

    public void loadTaskList() {
        taskList = entityManager.createQuery("SELECT t FROM Task t", Task.class).getResultList();
    }

    @Transactional
    public void createTask() {
        entityManager.persist(task);
        task = new Task();
        loadTaskList();
    }

    @Transactional
    public void deleteTask(Task task) {
        Task mergedTask = entityManager.merge(task);
        entityManager.remove(mergedTask);
        loadTaskList();
    }
}


