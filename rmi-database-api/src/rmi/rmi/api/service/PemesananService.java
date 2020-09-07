/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi.rmi.api.service;

import com.rmi.api.entity.Pemesanan;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author STU
 */
public interface PemesananService extends Remote{
    void insertPemesanan(Pemesanan pemesanan) throws RemoteException;
    Pemesanan getPemesanan(int id_transaksi) throws RemoteException;
    List<Pemesanan> getAllPemesanan(String nim) throws RemoteException;
    List<Pemesanan> getSemuaPemesanan(String nim) throws RemoteException;
}
