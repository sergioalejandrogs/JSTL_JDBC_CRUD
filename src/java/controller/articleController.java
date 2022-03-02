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
import models.ArticuloBean;
import models.ArticuloValidation;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
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
public class articleController {
    
    private ArticuloValidation articuloValidar;
    private JdbcTemplate jdbcTemplate;
    
    public articleController(){
        this.articuloValidar = new ArticuloValidation();
        ConectarDB conectar = new ConectarDB();
        this.jdbcTemplate = new JdbcTemplate(conectar.conectar());
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView form(){
        ArticuloBean articulo = new ArticuloBean();
        return new ModelAndView("views/formArticulo", "articulo", new ArticuloBean());
    }
    
    @RequestMapping(value="formArticulo.htm", method = RequestMethod.POST)
    public ModelAndView form(
            @ModelAttribute("articulo") ArticuloBean a,
            BindingResult result,
            SessionStatus status
    ){
        this.articuloValidar.validate(a, result);
        if(result.hasErrors()){
            ModelAndView mav = new ModelAndView();
            mav.setViewName("views/formArticulo");
            mav.addObject("articulo", new ArticuloBean());
            return mav;
        }
        else{
            ModelAndView mav = new ModelAndView();
            mav.setViewName("views/vistaArticulo");
            mav.addObject("titulo", a.getTitulo());
            mav.addObject("descripcion", a.getDescripcion());
            mav.addObject("precio", a.getPrecio());
            mav.addObject("foto", a.getFoto());
            return mav;
        }
    }
    
    @RequestMapping(value="listarArticulos.htm")
    public ModelAndView listarArticulos(){
        ModelAndView mav = new ModelAndView();
        String sql = "select * from articulos";
        List datos = this.jdbcTemplate.queryForList(sql);
        mav.setViewName("views/listarArticulos");
        mav.addObject("articulo", datos);
        return mav;
    }
    
    @RequestMapping(value="addArticulo.htm", method=RequestMethod.GET)
    public ModelAndView addArticulo(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("articulo", new ArticuloBean());
        mav.setViewName("views/addArticulo");
        return mav;
    }
    
    @RequestMapping(value="addArticulo.htm", method=RequestMethod.POST)
    public ModelAndView addArticulo(ArticuloBean a){
        ModelAndView mav = new ModelAndView();
        String sql = "insert into articulos (titulo, descripcion, precio, foto) values (?,?,?,?)";
        this.jdbcTemplate.update(
                sql, a.getTitulo(), a.getDescripcion(), a.getPrecio(), a.getFoto()
        );
        mav.setViewName("redirect:/listarArticulos.htm");
        return mav;
    }
    
    @RequestMapping(value="deleteArticulo.htm")
    public ModelAndView deleteArticulo(HttpServletRequest request){
        ModelAndView mav = new ModelAndView();
        int id = Integer.parseInt(request.getParameter("id"));
        String sql = "delete from articulos where id_articulo = ?";
        this.jdbcTemplate.update(sql, id);
        mav.setViewName("redirect:/listarArticulos.htm");
        return mav;
    }
    
    @RequestMapping(value="updateArticulo.htm", method=RequestMethod.GET)
    public ModelAndView updateArticulo(HttpServletRequest request){
        ModelAndView mav = new ModelAndView();
        int id = Integer.parseInt(request.getParameter("id"));
        ArticuloBean a = consultaArticuloxId(id);
        mav.addObject("articulo", a);
        mav.setViewName("views/updateArticulo");
        return mav;
    }
    
    public ArticuloBean consultaArticuloxId(int id){
        ArticuloBean a = new ArticuloBean();
        String sql = "select * from articulos where id_articulo = " + id;
        return (ArticuloBean)jdbcTemplate.query(sql, new ResultSetExtractor<ArticuloBean>()
        {
            public ArticuloBean extractData (ResultSet rs) throws SQLException, DataAccessException{
                if(rs.next()){
                    a.setId_articulo(rs.getInt("id_articulo"));
                    a.setTitulo(rs.getString("titulo"));
                    a.setDescripcion(rs.getString("descripcion"));
                    a.setPrecio(rs.getInt("precio"));
                    a.setFoto(rs.getString("foto"));
                }
            return a;
            }
        }
        );
    }
    
    @RequestMapping(value="updateArticulo.htm", method=RequestMethod.POST)
    public ModelAndView updateArticulo(ArticuloBean a){
        ModelAndView mav = new ModelAndView();
        String sql = "update articulos set titulo = ?, descripcion = ?, precio = ?, foto = ? where id_articulo = ?";
        this.jdbcTemplate.update(
                sql, a.getTitulo(), a.getDescripcion(), a.getPrecio(), a.getFoto(), a.getId_articulo()
        );
        mav.setViewName("redirect:/listarArticulos.htm");
        return mav;
    }
}
