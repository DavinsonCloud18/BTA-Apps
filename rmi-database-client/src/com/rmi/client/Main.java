/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rmi.client;

import com.rmi.api.entity.Barang;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import rmi.rmi.api.service.BarangService;
import rmi.rmi.api.service.PemesananService;
import rmi.rmi.api.service.UserService;

/**
 *
 * @author STU
 */
public class Main extends Application{
    
    private BarangService barangService;
    private UserService userService;
    private PemesananService pemesananService;
    
    @Override 
    public void start(Stage primaryStage) throws Exception {
        Registry registry = LocateRegistry.getRegistry("127.0.0.1",9898);
        
        barangService = (BarangService) registry.lookup("barang_services");
        userService = (UserService) registry.lookup("user_services");
        pemesananService = (PemesananService) registry.lookup("pemesanan_services");
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPage.fxml"));
        Parent root = loader.load();
        MainPageController controller = loader.getController();
        controller.setMain(this);
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("BTA Application (Barang)");
        primaryStage.show();
    }   
    public BarangService getBarangService(){
        return barangService;
    }
    public UserService getUserService(){
        return userService;
    }
    public PemesananService getPemesananService(){
        return pemesananService;
    }
    public static void main(String[] args) {
        launch();
    }
    
}
