/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi.rmi.api.service;

import com.rmi.api.entity.User;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author STU
 */
public interface UserService extends Remote{
    void insertUser(User user) throws RemoteException;
    void updateUser(User user) throws RemoteException;
    void deleteUser(String nim) throws RemoteException;
    User login(String nim,String kata_sandi) throws RemoteException; 
    User getUserByNim(String nim) throws RemoteException;
    List<User> getAllUser() throws RemoteException;
}
