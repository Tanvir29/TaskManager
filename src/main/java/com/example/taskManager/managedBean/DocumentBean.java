/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.taskManager.managedBean;

import com.example.taskManager.model.Document;
import com.example.taskManager.model.Project;
import com.example.taskManager.service.DocumentService;
import com.example.taskManager.service.ProjectService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Paths;
import java.util.List;

/**
 *
 * @author Fardin
 */
@Named
@ViewScoped
public class DocumentBean implements  Serializable {
    
    private Document document;
    private Long projectId;
    
    private Part uploadedFile;
    
    @Inject
    private ProjectService projectService;
    
    @Inject
    private DocumentService documentService;

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Part getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(Part uploadedFile) {
        this.uploadedFile = uploadedFile;
    }
    
    @PostConstruct
    public void init() {
        document = new Document();
    }
    
    public List<Document> findAllDocuments(Long projectId) {
        return documentService.findDocumentsOfProject(projectId);
    }
    
    public void copyFile(String filePath) throws FileNotFoundException, IOException {
        OutputStream outputStream = new FileOutputStream(filePath);
            
        try (InputStream inputStream = uploadedFile.getInputStream()) {
            int read;
            while ((read = inputStream.read()) != -1) {
                outputStream.write(read);
            }
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                throw e;
            }
        } 
    }
    
    public String uploadDocument(Long projectId) throws IOException{
        if(uploadedFile != null) {
            String fileName = uploadedFile.getSubmittedFileName();
            
            copyFile(documentService.buildPath(fileName));
            
            documentService.saveDocument(document, projectId, fileName);
            
            uploadedFile = null;
            
        }
        
        return "/app/documentView/documents.xhtml?id=" + projectId + "&faces-redirect=true";
    }
    
    public String removeDocument(Document document) {
        Long projectIdOfDoc = document.getProject().getId();
        documentService.deleteDocument(document);
        
        return "/app/documentView/documents.xhtml?id=" + projectIdOfDoc + "&faces-redirect=true";
    }
}
