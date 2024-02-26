/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.taskManager.service;

import com.example.taskManager.model.Project;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

/**
 *
 * @author saurav
 */
@Stateless
public class ProjectService {
    
    @PersistenceContext
    private EntityManager entityManager;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createProject(Project project) {
        entityManager.persist(project);
    }
    
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Project> findAllProjects() {
        return entityManager.createQuery("SELECT p FROM Project p", Project.class).getResultList();
    }
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Project findProjectById(long id) {
        return entityManager.find(Project.class, id);
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateProject(Project project) {
        try {
            entityManager.merge(project); 
        } catch (Exception e) {
            throw new RuntimeException("Failed to update the project", e);
        }
    }

   @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteProject(Project project) {
        if (entityManager.contains(project)) {
            entityManager.remove(project);
        } else {
            Project managedProject = findProjectById(project.getId());
            if (managedProject != null) {
                entityManager.remove(managedProject);
            }
        }
    }
}
