/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rmi.server;

import com.rmi.server.service.BarangServiceImpl;
import com.rmi.server.service.PemesananServiceImpl;
import com.rmi.server.service.UserServiceImpl;
import com.rmi.server.utilities.DatabaseConnection;
import com.socket.chat.Client;
import com.socket.chat.NetworkConnection;
import com.socket.chat.Server;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import rmi.rmi.api.service.BarangService;
import rmi.rmi.api.service.PemesananService;
import rmi.rmi.api.service.UserService;

/**
 *
 * @author STU
 */
public class Main extends Application{
    // Chat App Program
    private boolean isServer = true;
    private TextArea messages = new TextArea();
    private NetworkConnection connection = isServer ? createServer() : createClient();
    
    private Parent createContent(){
        messages.setPrefHeight(550);
        TextField input = new TextField();
        Button btn_kirim_pesan = new Button();
        btn_kirim_pesan.setText("Send Message");
        HBox slot_pesan = new HBox(20,input,btn_kirim_pesan);
        VBox root = new VBox(20,messages,slot_pesan);
        
        btn_kirim_pesan.setOnAction(event -> {
            String message = isServer ? "Penjual: " : "Pembeli: ";
            message += input.getText();
            input.clear();
            
            messages.appendText(message + "\n");
            try {
                connection.send(message);
            }
            catch (Exception ex) {
                messages.appendText("Failed to send message\n");
            }
        });
        
        root.setPrefSize(600, 600);
        return root;
    }
    
//    @Override
//    public void init() throws Exception{
//        connection.startConnection();
//    }
//    
    @Override
    public void start(Stage primaryStage) throws Exception {
        DatabaseConnection.getConnection();
        connection.startConnection();
        Registry registry = LocateRegistry.createRegistry(9898);
        
        UserServiceImpl userServiceImpl = new UserServiceImpl();
        UserService userService = (UserService) UnicastRemoteObject.exportObject(userServiceImpl, 0);
        
        BarangServiceImpl barangServiceImpl = new BarangServiceImpl();
        BarangService barangService = (BarangService) UnicastRemoteObject.exportObject(barangServiceImpl,0);
        
        PemesananServiceImpl pemesananServiceImpl = new PemesananServiceImpl();
        PemesananService pemesananService = (PemesananService) UnicastRemoteObject.exportObject(pemesananServiceImpl,0);
        
        registry.rebind("pemesanan_services", pemesananService);
        registry.rebind("user_services", userService);
        registry.rebind("barang_services", barangService);
        
        System.out.println("Server is running...");
        
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
        
//        Platform.exit();
    } 
 
    private Server createServer(){
        return new Server(5544, data -> {
            Platform.runLater(() -> {
                messages.appendText(data.toString() + "\n");
            });
        });
    }
    
    private Client createClient(){
        return new Client("127.0.0.1",5544, data -> {
            Platform.runLater(() -> {
                messages.appendText(data.toString() + "\n");
            });
        });
    }
    
    public static void main(String[] args) {
        launch();
    }
    
}
