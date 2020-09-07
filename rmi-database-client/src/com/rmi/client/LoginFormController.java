/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rmi.client;

import com.rmi.api.entity.User;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import rmi.rmi.api.service.UserService;

/**
 * FXML Controller class
 *
 * @author STU
 */
public class LoginFormController implements Initializable {
    @FXML
    private TextField txt_login_nim;
    @FXML
    private PasswordField txt_login_password;
    @FXML
    private Button btn_login;
    @FXML
    private Button btn_register;
    
    private Main main;
    private UserService userService;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void loginValidation(ActionEvent event) {
        if(isFieldValid()){
//            try {
                User user = new User();
                String user_nim = txt_login_nim.getText();
                String password = txt_login_password.getText();
                user.setNim(user_nim);
                user.setKata_sandi(password);
//                user = userService.getUserByNim(user_nim);
//                if(user != null && user.getKata_sandi() == password){
                    AnchorPane pane;
                    try {
                        pane = FXMLLoader.load(getClass().getResource("FormBarang.fxml"));
                        pane.getChildren().setAll(pane);
                    }
                    catch (IOException ex) {
                        Logger.getLogger(LoginFormController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
//                }
//            }
//            catch (RemoteException ex) {
//                ex.printStackTrace();
//            }
        }
    }

    @FXML
    private void registerUser(ActionEvent event) {
    }
    private boolean isFieldValid() {
        String errorMessage = "";
        if(txt_login_nim.getText().isEmpty() || txt_login_nim.getText() == null){
            errorMessage += "Field NIM tidak boleh kosong !!! \n";
        }
        if(txt_login_password.getText().isEmpty() || txt_login_password.getText() == null){
            errorMessage += "Field Password tidak boleh kosong !!! \n";
        }
        if(errorMessage.length() == 0){
            return true;
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Mohon isi semua field");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }
//    public void setMain(Main main){
//        this.main = main;
//        this.userService = main.getUserService();
//        
//        try {
//            table_barang.getItems().setAll(barangService.getAllBarang());
//        }
//        catch (RemoteException ex) {
//            ex.printStackTrace();
//        }
//    }
}
