/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Dao;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 *
 * @author SENA
 */
public class ConectarDB {
    public DriverManagerDataSource conectar(){
        DriverManagerDataSource datasource = new DriverManagerDataSource();
        datasource.setDriverClassName("com.mysql.jdbc.Driver");
        datasource.setUrl("jdbc:mysql://localhost:3306/prueba");
        datasource.setUsername("root");
        datasource.setPassword("");
        return datasource;
    }
}
