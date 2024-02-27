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
    
    public void findAllDocuments(Long projectId) {
        
    }
    
    public String getAbsolutePath() {
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String baseDirectory = servletContext.getRealPath("/");
        
        baseDirectory = baseDirectory.substring(0, baseDirectory.indexOf("target")) + "/src/main/uploaded_files";
        
        String directoryPath = Paths.get(baseDirectory).toString();
        
        return directoryPath;
    }
    
    public String uploadDocument(Long projectId) throws FileNotFoundException {
        if(uploadedFile != null) {
            String fileName = uploadedFile.getSubmittedFileName();
            
            String directoryPath = getAbsolutePath();
            String filePath = Paths.get(directoryPath, fileName).toString();
            
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            
            OutputStream outputStream = new FileOutputStream(filePath);
            
            try (InputStream inputStream = uploadedFile.getInputStream()) {
                int read;
                while ((read = inputStream.read()) != -1) {
                    outputStream.write(read);
                }
            } catch (IOException e) {
                return null;
            } finally {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    return null;
                }
            }   
            
            document.setFilePath(filePath);
            
            Project project = projectService.findProjectById(projectId);
            document.setProject(project);
            
            documentService.saveDocument(document);
            
            uploadedFile = null;
            
            return "success";
        }
        else {
            return null;
        }
    }
}
