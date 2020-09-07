/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rmi.server.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author STU
 */
public class DatabaseConnection {
    private static Connection connection;
    public static Connection getConnection(){
        if(connection == null){
            try {
                DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bta_database", "root", "");
            }
            catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return connection;
    }
}
