/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi.rmi.api.service;

import com.rmi.api.entity.Barang;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author STU
 */
public interface BarangService extends Remote {
    void insertBarang(Barang barang) throws RemoteException;
    void updateBarang(Barang barang) throws RemoteException;
    void deleteBarang(int id) throws RemoteException;
    Barang getBarangById(int id_barang) throws RemoteException;
    List<Barang> getAllBarang() throws RemoteException;
}
