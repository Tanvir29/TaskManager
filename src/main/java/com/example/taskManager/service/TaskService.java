package com.example.taskManager.service;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import com.example.taskManager.mailService.EmailSender;
import com.example.taskManager.model.Feedback;
import com.example.taskManager.model.Task;
import com.example.taskManager.model.TaskPriority;
import com.example.taskManager.model.TaskStatus;
import com.example.taskManager.model.User;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class TaskService {

    @PersistenceContext
    private EntityManager entityManager;
    
    @Inject
    private EmailSender emailSender;
    
    public void notifyUsers(Task task, List<User> users, String subject, String body) 
            throws MessagingException, IOException{
        List<String> recipientEmailLists = new ArrayList<>();
        for (User user: users){
         recipientEmailLists.add(user.getEmail());
         }
        String addressList = String.join(",", recipientEmailLists);
        emailSender.sendEmail(addressList, subject, body);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createTask(Task task, List<User> users) throws MessagingException, IOException {
        entityManager.persist(task);
        String mailSubject = "New Task Assigned";
        String mailBody =  "You have been assigned a new task: " + task;
        notifyUsers(task, users, mailSubject, mailBody);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteTask(Task task) throws MessagingException, IOException {
        
        if (entityManager.contains(task)){
            entityManager.remove(task);
        }
        else{
            Task managedTask = getTaskById(task.getId());
            if (managedTask != null){
                entityManager.remove(managedTask);
            }
        }
        List<User> users = task.getAssignees();
        String mailSubject = "Task Removed";
        String mailBody =  "This task has been deleted from your taskList : " + task;
        notifyUsers(task, users, mailSubject, mailBody);
    }
    
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Task> getAllTasks() {
        return entityManager.createQuery("SELECT t FROM Task t", Task.class).getResultList();
    }
    
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Task getTaskById(Long id) {
    if (id == null) {
        throw new IllegalArgumentException("ID cannot be null");
    }
    return entityManager.find(Task.class, id);
}

    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateTask(Task task) throws MessagingException, IOException {
        Task originalTask = getTaskById(task.getId());
        List<User> originalAssignees = originalTask.getAssignees();
        entityManager.merge(task);
        
        List<User> updatedAssignees = task.getAssignees();
        List<User> addedAssignees = new ArrayList<>(updatedAssignees);
        addedAssignees.removeAll(originalAssignees);
        if (!addedAssignees.isEmpty()){
            String mailSubject = "New Task Assigned";
            String mailBody =  "You have been assigned to an existing task: " + task;
            notifyUsers(task, addedAssignees, mailSubject, mailBody);
        }
        
        List<User> removedAssignees = new ArrayList<>(originalAssignees);
        removedAssignees.removeAll(updatedAssignees);
        if (!removedAssignees.isEmpty()){
            String mailSubject = "Task Removed";
            String mailBody =  "This task has been removed from your plate: " + task;
            notifyUsers(task, removedAssignees, mailSubject, mailBody);
        }
        
        List<User> sameAssignees = new ArrayList<>(updatedAssignees);
        sameAssignees.retainAll(originalAssignees);
        if (!sameAssignees.isEmpty()){
            String mailSubject = "Task Updated Notification";
            String mailBody =  "This task has been modified: " + task;
            notifyUsers(task, sameAssignees, mailSubject, mailBody);
        } 
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void addFeedback(Long taskId, Feedback feedback) {
        Task task = entityManager.find(Task.class, taskId);
        task.setFeedback(feedback);
        entityManager.merge(task);
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Task> filterTasks(String statusFilter, String priorityFilter, LocalDate dueDateFilter) {
        if (!isValidFilter(statusFilter) && !isValidFilter(priorityFilter)
                && dueDateFilter == null){
            return getAllTasks();
        }
        
        String queryString = "SELECT t FROM Task t WHERE 1=1";    
        if (isValidFilter(statusFilter)) {
            queryString += " AND t.status = :statusFilter";
        }
        if (isValidFilter(priorityFilter)) {
            queryString += " AND t.priority = :priorityFilter";
        }
        if (dueDateFilter != null) {
            queryString += " AND t.dueDate <= :dueDateFilter";
        }
        
        TypedQuery<Task> query = entityManager.createQuery(queryString, Task.class);
        if (isValidFilter(statusFilter)) {
            query.setParameter("statusFilter", TaskStatus.valueOf(statusFilter));
        }
        if (isValidFilter(priorityFilter)) {
            query.setParameter("priorityFilter", TaskPriority.valueOf(priorityFilter));
        }
        if (dueDateFilter != null) {
            query.setParameter("dueDateFilter", dueDateFilter);
        }
        
        List<Task> tasks = query.getResultList();
        
        return tasks;
    }
    
    public boolean isValidFilter(String filter){
        return filter != null && !filter.equals("--None--");
    }
}

