/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.taskManager.service;

import com.example.taskManager.model.Document;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 *
 * @author Fardin
 */
@Stateless
public class DocumentService {
    @PersistenceContext
    private EntityManager entityManager;
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void saveDocument(Document document){
        entityManager.persist(document);
    }
}
