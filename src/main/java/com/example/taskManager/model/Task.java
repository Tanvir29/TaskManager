/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.taskManager.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author saurav
 */
@Entity
public class Task implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   

    @NotNull
    private String title;

    @NotNull
    private String description;
//
//    @Temporal(TemporalType.DATE)
//    @NotNull
//    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TaskStatus status;

    @ManyToMany
//    @JoinTable(
//    name = "TASK_USER",
//    joinColumns = @JoinColumn(name = "task_id"),
//    inverseJoinColumns = @JoinColumn(name = "user_id",referencedColumnName = "USER.id")
//)
   // @JoinTable
    private List<User> assignees;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public LocalDate getDueDate() {
//        return dueDate;
//    }
//
//    public void setDueDate(LocalDate dueDate) {
//        this.dueDate = dueDate;
//    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public List<User> getAssignees() {
        return assignees;
    }
    
    public void setAssignees(List<User> assignees) {
        this.assignees = assignees;
    }
    

    @Override
    public String toString() {
        return "Task{" + "id=" + id + ", title=" + title + ", description=" + description + ", status=" + status + ", assignees=" + assignees + '}';
    }

    
}
