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
public class ArticuloValidation implements Validator{

    @Override
    public boolean supports(Class<?> type) {
        return ArticuloBean.class.isAssignableFrom(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ArticuloBean articulo = (ArticuloBean)o;
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "titulo", "titulo.required", "El título del artículo es obligatorio");
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "descripcion", "descripcion.required", "La descripción del artídulo es obligarotia");
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "precio", "precio.required", "El precio del artículo es obligatorio");
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "foto", "foto.required", "La foto del artículo es obligatoria");
    }
    
}
