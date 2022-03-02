/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import Dao.ConectarDB;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import models.PersonaBean;
import models.PersonaValidation;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author SENA
 */
@Controller
public class homeController {
    
    private PersonaValidation personaValidar;
    private JdbcTemplate jdbcTemplate;
    
    public homeController(){
        this.personaValidar = new PersonaValidation();
        ConectarDB conectar = new ConectarDB();
        this.jdbcTemplate = new JdbcTemplate(conectar.conectar());
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView form(){
        PersonaBean persona = new PersonaBean();
//        ModelAndView mav = new ModelAndView();
//        mav.setViewName("views/formUsuario");
//        mav.addObject("persona", persona);
//        return mav;
        return new ModelAndView("views/formUsuario", "persona", new PersonaBean());
    }
    
    @RequestMapping(value="formUsuario.htm", method = RequestMethod.POST)
    public ModelAndView form(
            @ModelAttribute("persona") PersonaBean p,
            BindingResult result,
            SessionStatus status
        ){
        this.personaValidar.validate(p, result);
        if(result.hasErrors()){
            ModelAndView mav = new ModelAndView();
            mav.addObject("persona", new PersonaBean());
            mav.setViewName("views/formUsuario");
            return mav;
        }
        else{
            ModelAndView mav = new ModelAndView();
            mav.setViewName("views/vistaUsuario");
            mav.addObject("nombre", p.getNombre());
            mav.addObject("apellido", p.getApellido());
            mav.addObject("edad", p.getEdad());
            mav.addObject("correo", p.getCorreo());
            return mav;
        }
    }
    
    @RequestMapping(value="listarUsuarios.htm")
    public ModelAndView listarUsuarios(){
        ModelAndView mav = new ModelAndView();
        String sql = "select * from usuarios";
        List datos = this.jdbcTemplate.queryForList(sql);
        mav.setViewName("views/listarUsuarios");
        mav.addObject("usuario", datos);
        return mav;
    }
    
    @RequestMapping(value="addUsuario.htm", method=RequestMethod.GET)
    public ModelAndView addUsuario(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("usuario", new PersonaBean());
        mav.setViewName("views/addUsuario");
        return mav;
    }
    
    @RequestMapping(value="addUsuario.htm", method=RequestMethod.POST)
    public ModelAndView addUsuario(PersonaBean p){
        ModelAndView mav = new ModelAndView();
        String sql = "insert into usuarios (nombre, apellido, edad, correo) values (?,?,?,?)";
        this.jdbcTemplate.update(
                sql, p.getNombre(), p.getApellido(), p.getEdad(), p.getCorreo()
        );
        mav.setViewName("redirect:/listarUsuarios.htm");
        return mav;
    }
    
    @RequestMapping(value="deleteUsuario.htm")
    public ModelAndView deleteUsuario(HttpServletRequest request){
        ModelAndView mav = new ModelAndView();
        int id = Integer.parseInt(request.getParameter("id"));
        String sql = "delete from usuarios where id_usuario = ?";
        this.jdbcTemplate.update(sql, id);
        mav.setViewName("redirect:/listarUsuarios.htm");
        return mav;
    }
    
    @RequestMapping(value="updateUsuario.htm", method=RequestMethod.GET)
    public ModelAndView updateUsuario(HttpServletRequest request){
        ModelAndView mav = new ModelAndView();
        int id = Integer.parseInt(request.getParameter("id"));
        PersonaBean p = consultaUsuarioxId(id);
        mav.addObject("usuario", p);
        mav.setViewName("views/updateUsuario");
        return mav;
    }
    
    public PersonaBean consultaUsuarioxId(int id){
        PersonaBean p = new PersonaBean();
        String sql = "select * from usuarios where id_usuario = " + id;
        return (PersonaBean)jdbcTemplate.query(sql, new ResultSetExtractor<PersonaBean>()
        {
            public PersonaBean extractData (ResultSet rs) throws SQLException, DataAccessException{
                if(rs.next()){
                    p.setId(rs.getInt("id_usuario"));
                    p.setNombre(rs.getString("nombre"));
                    p.setApellido(rs.getString("apellido"));
                    p.setEdad(rs.getInt("edad"));
                    p.setCorreo(rs.getString("correo"));
                }
            return p;
            }
        }
        );
    }
    
    @RequestMapping(value="updateUsuario.htm", method=RequestMethod.POST)
    public ModelAndView updateUsuario(PersonaBean p){
        ModelAndView mav = new ModelAndView();
        String sql = "update usuarios set nombre = ?, apellido = ?, edad = ?, correo = ? where id_usuario = ?";
        this.jdbcTemplate.update(
                sql, p.getNombre(), p.getApellido(), p.getEdad(), p.getCorreo(), p.getId()
        );
        mav.setViewName("redirect:/listarUsuarios.htm");
        return mav;
    }
}
