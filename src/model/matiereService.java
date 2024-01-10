/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author oussa
 */
public class matiereService {

     private  Connection con ;

    public matiereService() {
         try {
             this.con = database_Connection.getConnection();
         } catch (SQLException ex) {
             Logger.getLogger(matiereService.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    public matiereEntity createMatiere(int id,int java, int base, int programmationweb, int conception) throws SQLException {
      PreparedStatement statement = con.prepareStatement("INSERT INTO user_matiere(id_user, java, base, programmationweb, conception) VALUES (?, ?, ?, ?, ?)");
    statement.setInt(1, id);
    statement.setInt(2, java);
    statement.setInt(3, base);
    statement.setInt(4, programmationweb);
    statement.setInt(5, conception);
    statement.executeUpdate();

        
        return new matiereEntity(id, java, base, programmationweb, conception);
    }

    public void deleteMatiere(int id) throws SQLException {
        PreparedStatement statement = con.prepareStatement("DELETE FROM user_matiere WHERE id_user = ?");
        statement.setInt(1, id);
        statement.executeUpdate();
    }

    public void updateMatiere(matiereEntity matiere) throws SQLException {
        PreparedStatement statement = con.prepareStatement("UPDATE user_matiere SET java = ?, base = ?, programmationweb = ?, conception = ? WHERE id_user = ?");
        statement.setInt(1, matiere.getJava());
        statement.setInt(2, matiere.getBase());
        statement.setInt(3, matiere.getProgrammationweb());
        statement.setInt(4, matiere.getConception());
        statement.setInt(5, matiere.getId());
        statement.executeUpdate();
    }
    public matiereEntity getMatiereById(int id) throws SQLException {
    PreparedStatement statement = con.prepareStatement("SELECT * FROM user_matiere WHERE id_user = ?");
    statement.setInt(1, id);
    ResultSet resultSet = statement.executeQuery();

    if (resultSet.next()) {
        int java = resultSet.getInt("java");
        int base = resultSet.getInt("base");
        int programmationweb = resultSet.getInt("programmationweb");
        int conception = resultSet.getInt("conception");
        return new matiereEntity(id, java, base, programmationweb, conception);
    }

     createMatiere(id, 0, 0, 0, 0);
     return null;
}
    public List<matiereEntity> getAllMatieres() throws SQLException {
    List<matiereEntity> matieres = new ArrayList<>();

    PreparedStatement statement = con.prepareStatement("SELECT * FROM user_matiere");
    ResultSet resultSet = statement.executeQuery();

    while (resultSet.next()) {
        int id = resultSet.getInt("id_user");
        int java = resultSet.getInt("java");
        int base = resultSet.getInt("base");
        int programmationweb = resultSet.getInt("programmationweb");
        int conception = resultSet.getInt("conception");
        matieres.add(new matiereEntity(id, java, base, programmationweb, conception));
    }

    return matieres;
}
}
  

