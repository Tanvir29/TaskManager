/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.taskManager.service;

import com.example.taskManager.model.Document;
import com.example.taskManager.model.Project;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.ServletContext;
import java.io.File;
import java.nio.file.Paths;
import java.util.List;

/**
 *
 * @author Fardin
 */
@Stateless
public class DocumentService {
    @PersistenceContext
    private EntityManager entityManager;
    
    @Inject
    private ProjectService projectService;
    
    public String getAbsolutePath() {
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String baseDirectory = servletContext.getRealPath("/");
        
        baseDirectory = baseDirectory.substring(0, baseDirectory.indexOf("target")) + "/src/main/webapp/files";
        
        String directoryPath = Paths.get(baseDirectory).toString();
        
        return directoryPath;
    }
    
    public String buildPath(String fileName) {
        String directoryPath = getAbsolutePath();
        String filePath = Paths.get(directoryPath, fileName).toString();

        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        
        return filePath;
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void saveDocument(Document document, Long projectId, String fileName){
        document.setFilePath("/home/files/" + fileName);
        document.setFileName(fileName);

        Project project = projectService.findProjectById(projectId);
        document.setProject(project);
        
        entityManager.persist(document);
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Document> findDocumentsOfProject(Long projectId) {
        TypedQuery<Document> query = entityManager.createQuery("SELECT d FROM Document d WHERE d.project.id = :projectId", Document.class);
        query.setParameter("projectId", projectId);
        return query.getResultList();
    }
    
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Document findDocumentById(Long id) {
        return entityManager.find(Document.class, id);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteDocument(Document document) {
        if (entityManager.contains(document)) {
            entityManager.remove(document);
        } else {
            Document managedDocument = findDocumentById(document.getId());
            if (managedDocument != null) {
                entityManager.remove(managedDocument);
            }
        }
    }
}
