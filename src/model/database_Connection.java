/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import com.mysql.jdbc.Driver;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author oussa
 */
public class database_Connection {
    
     private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://127.0.0.1/learningplatform";

    // Database credentials
    private static final String USER = "root";
    private static final String PASS = "";

    private static Connection conn = null;
    private database_Connection() {

    }
        public static synchronized Connection getConnection() throws SQLException {
        if (conn == null) {
            try {
                // Register JDBC driver
                Class.forName("com.mysql.jdbc.Driver");

                // Create a new connection
                conn = java.sql.DriverManager.getConnection(DB_URL, USER, PASS);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }
}
