/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author oussa
 */
public class student_matiereService {
    
    private  Connection con;
    public student_matiereService() {
        try {
            con = database_Connection.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(student_matiereService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void insert() throws SQLException {
    }

    
    public void update() throws SQLException {
    }

    
    public void delete() throws SQLException {
    }

    
    public void select() throws SQLException {
    }
  
}