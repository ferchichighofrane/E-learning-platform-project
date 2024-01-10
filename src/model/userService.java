/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
/**
 *
 * @author oussa
 */
public class userService {
    private Connection connection;

    public userService() {
        try {
     connection = database_Connection.getConnection();
    // Use the connection to execute SQL queries
} catch (SQLException e) {
    e.printStackTrace();
}
    }

    public int createUser(userEntity user) throws SQLException {
        String sql = "INSERT INTO users (FirstName, email, password, type) VALUES (?, ?, ?, 1 )";
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, user.getFirstName());
        
        statement.setString(2, user.getEmail());
        statement.setString(3, user.getPassword());
        
        int affectedRows = statement.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Creating user failed, no rows affected.");
        }
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                user.setId(id);
            } else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
        }
        return user.getId();
    }

    public void updateUser(userEntity user) throws SQLException {
        String sql = "UPDATE users SET FirstName=?, FamilyName=?, email=?, password=?, type=?, date_de_naissance=? WHERE id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, user.getFirstName());
        statement.setString(2, user.getFamilyName());
        statement.setString(3, user.getEmail());
        statement.setString(4, user.getPassword());
        statement.setInt(5, user.getType());
        statement.setString(6, user.getDateOfBirth());
        statement.setInt(7, user.getId());
        int affectedRows = statement.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Updating user failed, no rows affected.");
        }
    }

    public void deleteUser(int id) throws SQLException {
        String sql = "DELETE FROM users WHERE id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        int affectedRows = statement.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Deleting user failed, no rows affected.");
        }
        
    }

    public userEntity getUserById(int id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet =    statement.executeQuery();
    if (resultSet.next()) {
        String firstName = resultSet.getString("FirstName");
        String familyName = resultSet.getString("FamilyName");
        String email = resultSet.getString("email");
        String password = resultSet.getString("password");
        int type = resultSet.getInt("type");
        String dateOfBirth = resultSet.getString("date_de_naissance");
        userEntity user = new userEntity(firstName, familyName, email, password, type, dateOfBirth);
        user.setId(id);
        return user;
    } else {
        return null;
    }
}

public List<userEntity> getAllUsers() throws SQLException {
    List<userEntity> users = new ArrayList<>();
    String sql = "SELECT * FROM users";
    PreparedStatement statement = connection.prepareStatement(sql);
    ResultSet resultSet = statement.executeQuery();
    while (resultSet.next()) {
        int id = resultSet.getInt("id");
        String firstName = resultSet.getString("FirstName");
        String familyName = resultSet.getString("FamilyName");
        String email = resultSet.getString("email");
        String password = resultSet.getString("password");
        int type = resultSet.getInt("type");
        String dateOfBirth = resultSet.getString("date_de_naissance");
        userEntity user = new userEntity(firstName, familyName, email, password, type, dateOfBirth);
        user.setId(id);
        users.add(user);
    }
    return users;

}

    // rest of the class code

    public List<userEntity> getAllStudents() throws SQLException {
        List<userEntity> students = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE type = 1";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String firstName = resultSet.getString("FirstName");
            String familyName = resultSet.getString("FamilyName");
            String email = resultSet.getString("email");
            String password = resultSet.getString("password");
            int type = resultSet.getInt("type");
            String dateOfBirth = resultSet.getString("date_de_naissance");
            userEntity user = new userEntity(firstName, familyName, email, password, type, dateOfBirth);
            user.setId(id);
            students.add(user);
        }
        return students;
    }

    public List<userEntity> getAllAdmins() throws SQLException {
        List<userEntity> admins = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE type = 0";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String firstName = resultSet.getString("FirstName");
            String familyName = resultSet.getString("FamilyName");
            String email = resultSet.getString("email");
            String password = resultSet.getString("password");
            int type = resultSet.getInt("type");
            String dateOfBirth = resultSet.getString("date_de_naissance");
            userEntity user = new userEntity(firstName, familyName, email, password, type, dateOfBirth);
            user.setId(id);
            admins.add(user);
        }
        return admins;
    }
          public userEntity login(ModelLogin login) throws SQLException {
        userEntity data = null;
        PreparedStatement p = connection.prepareStatement("select id,email from `users` where BINARY(email)=? and BINARY(`password`)=?  limit 1");
        p.setString(1, login.getEmail());
        p.setString(2, login.getPassword());
        ResultSet r = p.executeQuery();
        if (r.first()) {
            int id = r.getInt(1);
            data=getUserById(id);
        }
        r.close();
        p.close();
        return data;
    }
    public boolean checkDuplicateEmail(String email) throws SQLException {
        boolean duplicate = false;
        PreparedStatement p = connection.prepareStatement("select id from `users` where email=?  limit 1");
        p.setString(1, email);
        ResultSet r = p.executeQuery();
        if (r.first()) {
            duplicate = true;
        }
        r.close();
        p.close();
        return duplicate;
    }
   
    public static boolean verifyEmailformat(userEntity user){
        String email=user.getEmail();
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches() ;
    }
}



