/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rmi.server.service;

import com.rmi.api.entity.Barang;
import com.rmi.server.utilities.DatabaseConnection;
import java.rmi.RemoteException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import rmi.rmi.api.service.BarangService;

/**
 *
 * @author STU
 */
public class BarangServiceImpl implements BarangService{

    @Override
    public void insertBarang(Barang barang) throws RemoteException {
        PreparedStatement statement = null;
        
        String sql = "INSERT INTO barang(nama_barang,jenis,harga) VALUES (?,?,?)";
        try {
            statement = DatabaseConnection.getConnection().prepareStatement(sql);
            statement.setString(1, barang.getNama_barang());
            statement.setString(2, barang.getJenis());
            statement.setFloat(3, barang.getHarga());
            
            statement.executeUpdate();
            ResultSet result = statement.getGeneratedKeys();
            if(result.next()){
                barang.setId_barang(result.getInt(1));
            }
            result.close();
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
    public void updateBarang(Barang barang) throws RemoteException {
        PreparedStatement statement = null;
        String sql = "UPDATE barang SET nama_barang = ?"
                    +", jenis = ?, harga = ? WHERE id_barang = ?";
        try {
            statement = DatabaseConnection.getConnection().prepareStatement(sql);
            statement.setString(1, barang.getNama_barang());
            statement.setString(2, barang.getJenis());
            statement.setFloat(3, barang.getHarga());
            statement.setInt(4,barang.getId_barang());
            
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
    public void deleteBarang(int id) throws RemoteException {
        PreparedStatement statement = null;
        
        String sql = "DELETE FROM barang WHERE id_barang = ?";
        try {
            statement = DatabaseConnection.getConnection().prepareStatement(sql);
            statement.setInt(1, id);
            
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
    public Barang getBarangById(int id_barang) throws RemoteException {
        PreparedStatement statement = null;
        
        String sql = "SELECT * FROM barang WHERE id_barang = ?";
        try {
            statement = DatabaseConnection.getConnection().prepareStatement(sql);
            statement.setInt(1, id_barang);
            
            ResultSet result = statement.executeQuery();
            Barang barang = new Barang();
            if(result.next()){
                barang.setId_barang(result.getInt("id_barang"));
                barang.setNama_barang(result.getString("nama_barang"));
                barang.setJenis(result.getString("jenis"));
                barang.setHarga(result.getFloat("harga"));
            }
            result.close();
            return barang;
        }
        catch(SQLException ex) {
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
    public List<Barang> getAllBarang() throws RemoteException {
        PreparedStatement statement = null;
        
        String sql = "SELECT * FROM barang";
        try {
            
            statement = DatabaseConnection.getConnection().prepareStatement(sql);
            List<Barang> listBarang = new ArrayList<>();
            ResultSet result = statement.executeQuery(); // ini beda dari contoh -> executeQuery(sql)
            while(result.next()){
                Barang barang = new Barang();
                barang.setId_barang(result.getInt("id_barang"));
                barang.setNama_barang(result.getString("nama_barang"));
                barang.setJenis(result.getString("jenis"));
                barang.setHarga(result.getFloat("harga"));
                listBarang.add(barang);
            }
            result.close();
            return listBarang;
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
