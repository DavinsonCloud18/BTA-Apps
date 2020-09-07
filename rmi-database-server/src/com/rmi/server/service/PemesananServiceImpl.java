/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rmi.server.service;

import com.rmi.api.entity.Pemesanan;
import com.rmi.server.utilities.DatabaseConnection;
import java.rmi.RemoteException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rmi.rmi.api.service.PemesananService;

/**
 *
 * @author STU
 */
public class PemesananServiceImpl implements PemesananService{

    @Override
    public void insertPemesanan(Pemesanan pemesanan) throws RemoteException {
        PreparedStatement statement = null;
        
        String sql = "INSERT INTO pemesanan(nim,id_barang,lokasi_transaksi,kuantitas) VALUES (?,?,?,?)";
        try {
            statement = DatabaseConnection.getConnection().prepareStatement(sql);
            statement.setString(1, pemesanan.getNim());
            statement.setInt(2, pemesanan.getId_barang());
            statement.setString(3, pemesanan.getLokasi_transaksi());
            statement.setInt(4, pemesanan.getKuantitas());
            
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
    public Pemesanan getPemesanan(int id_transaksi) throws RemoteException {
        PreparedStatement statement = null;
        String sql = "SELECT * FROM pemesanan WHERE id_transaksi = ?";
        try {
            statement = DatabaseConnection.getConnection().prepareStatement(sql);
            statement.setInt(1, id_transaksi);
            ResultSet result = statement.executeQuery();
            Pemesanan pemesanan = new Pemesanan();
            if(result.next()){
                pemesanan.setId_barang(result.getInt("id_transaksi"));
                pemesanan.setNim(result.getString("nim"));
                pemesanan.setId_barang(result.getInt("id_barang"));
                pemesanan.setKuantitas(result.getInt("kuantitas"));
                pemesanan.setLokasi_transaksi(result.getString("lokasi_transaksi"));
            }
            result.close();
            return pemesanan;
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
    public List<Pemesanan> getAllPemesanan(String nim) throws RemoteException {
        PreparedStatement statement = null;
        String sql = "SELECT * FROM pemesanan INNER JOIN barang ON pemesanan.id_barang = barang.id_barang WHERE pemesanan.nim = ?";
        try {
            statement = DatabaseConnection.getConnection().prepareCall(sql);
            statement.setString(1, nim);
            List<Pemesanan> listPemesanan = new ArrayList<>();
            ResultSet result = statement.executeQuery();
            while(result.next()){
                Pemesanan pemesanan = new Pemesanan();
                pemesanan.setId_transaksi(result.getInt("id_transaksi"));
                pemesanan.setNim(result.getString("nim"));
                pemesanan.setLokasi_transaksi(result.getString("lokasi_transaksi"));
                pemesanan.setId_barang(result.getInt("id_barang"));
                pemesanan.setKuantitas(result.getInt("kuantitas"));
                pemesanan.setNama_barang(result.getString("nama_barang"));
                pemesanan.setJenis_barang(result.getString("jenis"));
                pemesanan.setHarga_barang(result.getFloat("harga"));
                listPemesanan.add(pemesanan);
            }
            result.close();
            return listPemesanan;
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
    public List<Pemesanan> getSemuaPemesanan(String nim) throws RemoteException{
        PreparedStatement statement = null;
        String sql = "SELECT * FROM pemesanan WHERE nim = ?";
        try {
            statement = DatabaseConnection.getConnection().prepareCall(sql);
            statement.setString(1, nim);
            List<Pemesanan> listPemesanan = new ArrayList<>();
            ResultSet result = statement.executeQuery();
            while(result.next()){
                Pemesanan pemesanan = new Pemesanan();
                pemesanan.setId_transaksi(result.getInt("id_transaksi"));
                pemesanan.setNim(result.getString("nim"));
                pemesanan.setLokasi_transaksi(result.getString("lokasi_transaksi"));
                pemesanan.setId_barang(result.getInt("id_barang"));
                pemesanan.setKuantitas(result.getInt("kuantitas"));
                listPemesanan.add(pemesanan);
            }
            result.close();
            return listPemesanan;
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
