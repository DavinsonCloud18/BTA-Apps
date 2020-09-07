/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rmi.server.service;

import com.rmi.api.entity.User;
import com.rmi.server.utilities.DatabaseConnection;
import java.rmi.RemoteException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rmi.rmi.api.service.UserService;

/**
 *
 * @author STU
 */
public class UserServiceImpl implements UserService{

    @Override
    public void insertUser(User user) throws RemoteException {
        PreparedStatement statement = null;
        
        String sql = "INSERT INTO users(nim,nama,kata_sandi,email) VALUES (?,?,?,?)";
        try {
            statement = DatabaseConnection.getConnection().prepareStatement(sql);
            statement.setString(1, user.getNim());
            statement.setString(2, user.getNama());
            statement.setString(3, user.getKata_sandi());
            statement.setString(4, user.getEmail());
            
            statement.executeUpdate();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally{
            if(statement != null){
                try {
                    statement.close();
                }
                catch(SQLException ex) {
                    ex.printStackTrace();
                }
            }
            
        }
    }

    @Override
    public void updateUser(User user) throws RemoteException {
        PreparedStatement statement = null;
        String sql = "Update users SET nim = ? "+
                     ", nama = ?, kata_sandi = ? "+
                     ", email = ?";
        try {
            statement = DatabaseConnection.getConnection().prepareStatement(sql);
            statement.setString(1, user.getNim());
            statement.setString(2, user.getNama());
            statement.setString(3, user.getKata_sandi());
            statement.setString(4, user.getEmail());
            
            statement.executeUpdate();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally{
            if(statement != null){
                try {
                    statement.close();
                }
                catch(SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    public void deleteUser(String nim) throws RemoteException {
        PreparedStatement statement = null;
        
        String sql = "DELETE FROM users WHERE nim = ?";
        try {
            statement = DatabaseConnection.getConnection().prepareStatement(sql);
            statement.setString(1, nim);
            statement.executeUpdate();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally{
            if(statement != null){
                try {
                    statement.close();
                }
                catch(SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    public User login(String nim, String kata_sandi) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User getUserByNim(String nim) throws RemoteException {
        PreparedStatement statement = null;
        
        String sql = "SELECT * FROM users WHERE nim = ?";
        try {
            statement = DatabaseConnection.getConnection().prepareStatement(sql);
            statement.setString(1, nim);
            
            ResultSet result = statement.executeQuery();
            User user = new User();
            if(result.next()){
                user.setNim(result.getString("nim"));
                user.setNama(result.getString("nama"));
                user.setKata_sandi(result.getString("kata_sandi"));
                user.setEmail(result.getString("email"));
            }
            result.close();
            return user;
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
        finally{
            if(statement != null){
                try {
                    statement.close();
                }
                catch(SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        
    }

    @Override
    public List<User> getAllUser() throws RemoteException {
        PreparedStatement statement = null;
        String sql = "SELECT * FROM users";
        try {
            statement = DatabaseConnection.getConnection().prepareStatement(sql);
            List<User> listUser = new ArrayList<>();
            ResultSet result = statement.executeQuery();
            while(result.next()){
                User user = new User();
                user.setNim(result.getString("nim"));
                user.setNama(result.getString("nama"));
                user.setKata_sandi(result.getString("kata_sandi"));
                user.setEmail(result.getString("email"));
                listUser.add(user);
            }
            result.close();
            return listUser;
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
        finally{
            if(statement != null){
                try {
                    statement.close();
                }
                catch(SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
