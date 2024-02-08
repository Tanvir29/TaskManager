/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.taskManager.validator;

import com.example.taskManager.service.UserService;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;
import jakarta.inject.Inject;

/**
 *
 * @author Fardin
 */
@FacesValidator(value = "uniqueEmailValidator", managed = true)
public class UniqueEmailValidator implements Validator<String>{
    
    @Inject
    private UserService userService; 

    @Override
    public void validate(FacesContext fc, UIComponent uic, String email) throws ValidatorException {
        if (email == null || email.isEmpty()) {
            return;
        }
        
        if (userService.isEmailExists(email)) {
            throw new ValidatorException(new FacesMessage("Email Already In Use !!"));
        }
    }
    
}
