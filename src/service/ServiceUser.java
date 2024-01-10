package service;

import dbconnexion.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.ModelLogin;
import model.userModel;

public class ServiceUser {

    private final Connection con;

    public ServiceUser() {
        con = DatabaseConnection.getInstance().getConnection();
    }

    public void insertUser(userModel user) throws SQLException {
        PreparedStatement p = con.prepareStatement("insert into `user` (UserName, Email, `Password`) values (?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
        
        p.setString(1, user.getUserName());
        p.setString(2, user.getEmail());
        p.setString(3, user.getPassword());
        
        p.execute();
        ResultSet r = p.getGeneratedKeys();
        r.first();
        int userID = r.getInt(1);
        r.close();
        p.close();
        user.setUserId(userID);  
    }
      public userModel login(ModelLogin login) throws SQLException {
        userModel data = null;
        PreparedStatement p = con.prepareStatement("select UserID, UserName, Email from `user` where BINARY(Email)=? and BINARY(`Password`)=?  limit 1");
        p.setString(1, login.getEmail());
        p.setString(2, login.getPassword());
        ResultSet r = p.executeQuery();
        if (r.first()) {
            int userID = r.getInt(1);
            String userName = r.getString(2);
            String email = r.getString(3);
            data = new userModel(userID, userName, email, "");
        }
        r.close();
        p.close();
        return data;
    }
    public boolean checkDuplicateEmail(String user) throws SQLException {
        boolean duplicate = false;
        PreparedStatement p = con.prepareStatement("select UserID from `user` where Email=?  limit 1");
        p.setString(1, user);
        ResultSet r = p.executeQuery();
        if (r.first()) {
            duplicate = true;
        }
        r.close();
        p.close();
        return duplicate;
    }
   
    public static boolean verifyEmailformat(userModel user){
        String email=user.getEmail();
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches() ;
    }

}
