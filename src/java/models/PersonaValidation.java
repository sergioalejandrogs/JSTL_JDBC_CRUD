/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author SENA
 */
public class PersonaValidation implements Validator{

    @Override
    public boolean supports(Class<?> type) {
        return PersonaBean.class.isAssignableFrom(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
        PersonaBean persona = (PersonaBean)o;
        
        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors, 
                "nombre", 
                "nombre.lol", 
                "El nombre es obligatorio"
        );
        
        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors, 
                "apellido", 
                "apellido.lol", 
                "El apellido es obligatorio"
        );
        
        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors, 
                "edad", 
                "edad.lol", 
                "La edad es obligatoria"
        );
        
        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors, 
                "correo", 
                "correo.lol", 
                "El correo es obligatorio"
        );
    }
    
    
    
}
