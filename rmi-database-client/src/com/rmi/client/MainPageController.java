/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rmi.client;

import com.rmi.api.entity.Barang;
import com.rmi.api.entity.Pemesanan;
import com.rmi.api.entity.User;
import com.socket.chat.Client;
import com.socket.chat.NetworkConnection;
import com.socket.chat.Server;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import rmi.rmi.api.service.BarangService;
import rmi.rmi.api.service.PemesananService;
import rmi.rmi.api.service.UserService;

/**
 * FXML Controller class
 *
 * @author STU
 */
public class MainPageController implements Initializable {
    @FXML
    private TextField txt_login_nim;
    @FXML
    private PasswordField txt_login_password;
    @FXML
    private Button btn_login;
    @FXML
    private Button btn_register;
    @FXML
    private Label selected_barang_nama;
    @FXML
    private Label selected_barang_jenis;
    @FXML
    private Label selected_barang_harga;
    @FXML
    private Label selected_id_barang;
    @FXML
    private TextField txt_jumlah_pemesanan;
    @FXML
    private TableColumn<Barang, Integer> col_id_barang;
    @FXML
    private TableColumn<Barang, String> col_nama_barang;
    @FXML
    private TableColumn<Barang, String> col_jenis_barang;
    @FXML
    private TableColumn<Barang, Float> col_harga_barang;
    @FXML
    private Tab tab_login;
    @FXML
    private Tab tab_pemesanan;
    @FXML
    private TableView table_barang;
    
    private String NimAkunLogin;
    
    private UserService userService; 
    
    private BarangService barangService;
    
    private PemesananService pemesananService;
    
    private Main main;
    @FXML
    private Tab tab_register_user;
    @FXML
    private TextField register_nim;
    @FXML
    private TextField register_nama;
    @FXML
    private TextField register_pass;
    @FXML
    private TextField register_repass;
    @FXML
    private TextField register_email;
    @FXML
    private Button btn_logout;
    @FXML
    private Tab tab_history_pemesanan;
    @FXML
    private TableColumn<Pemesanan, Integer> col_history_id_pemesanan;
    @FXML
    private TableColumn<Pemesanan, String> col_history_lokasi;
    @FXML
    private TableColumn<Pemesanan, Integer> col_history_kuantitas;
    @FXML
    private TableColumn<Pemesanan, Integer> col_history_id_barang;
    @FXML
    private TableColumn<Pemesanan, String> col_history_nim;
    @FXML
    private Label lbl_nim;
    @FXML
    private Label lbl_nama;
    @FXML
    private Label lbl_email;
    @FXML
    private TableView<Pemesanan> table_history_transaksi;
    @FXML
    private TextField txt_lokasi_transaksi;
    @FXML
    private Button btn_pemesanan;
    @FXML
    private Button btn_history_refresh;
    @FXML
    private Tab tab_chat_client;
    @FXML
    private TextArea txt_area_chat_client;
    @FXML
    private TextField txt_field_chat_client;
    @FXML
    private Button btn_send_messages_client;
    
    // Chat Attribute
    private boolean isServer = false;
    private NetworkConnection connection = isServer ? createServer() : createClient();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        col_id_barang.setCellValueFactory(new PropertyValueFactory<Barang,Integer> ("id_barang"));
        col_nama_barang.setCellValueFactory(new PropertyValueFactory<Barang,String> ("nama_barang"));
        col_jenis_barang.setCellValueFactory(new PropertyValueFactory<Barang,String> ("jenis"));
        col_harga_barang.setCellValueFactory(new PropertyValueFactory<Barang,Float> ("harga"));
        
        table_barang.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Barang>() {

            @Override
            public void changed(ObservableValue<? extends Barang> observable, Barang oldBarang, Barang newBarang) {
                selected_barang_nama.setText(newBarang.getNama_barang());
                selected_barang_jenis.setText(newBarang.getJenis());
                selected_barang_harga.setText(String.valueOf(newBarang.getHarga()));
                selected_id_barang.setText(String.valueOf(newBarang.getId_barang()));
            }
        });
        
        col_history_id_pemesanan.setCellValueFactory(new PropertyValueFactory<Pemesanan,Integer>("id_transaksi"));
        col_history_id_barang.setCellValueFactory(new PropertyValueFactory<Pemesanan,Integer>("id_barang"));
        col_history_nim.setCellValueFactory(new PropertyValueFactory<Pemesanan,String>("nim"));
        col_history_lokasi.setCellValueFactory(new PropertyValueFactory<Pemesanan,String>("lokasi_transaksi"));
        col_history_kuantitas.setCellValueFactory(new PropertyValueFactory<Pemesanan,Integer>("kuantitas"));
        
        }    

    @FXML
    public void loginValidation(ActionEvent event) {
        if(isLoginFieldValid()){
            try {
                User user = new User();
                String user_nim = txt_login_nim.getText();
                String password = txt_login_password.getText();
                user.setNim(user_nim);
                user.setKata_sandi(password);
                user = userService.getUserByNim(user_nim);
                if(user != null && user.getKata_sandi().equals(password)){
                    LoginSuccess();
                    NimAkunLogin = user_nim;
                    tab_pemesanan.setDisable(false);
                    tab_history_pemesanan.setDisable(false);
                    tab_chat_client.setDisable(false);
                    tab_login.setDisable(true);
                    tab_register_user.setDisable(true);
                    lbl_nim.setText(NimAkunLogin);
                    lbl_nama.setText(user.getNama());
                    lbl_email.setText(user.getEmail());
                }
            }
            catch (RemoteException ex) {
                LoginFailed();
                ex.printStackTrace();
            }
        }
        else{
            LoginFailed();
        }
    }

    @FXML
    public void registerUser(ActionEvent event) {
        if(isRegisterFormValid()){
            try {
                String nim = register_nim.getText();
                String nama = register_nama.getText();
                String pass = register_pass.getText();
                String email = register_email.getText();

                User user = new User();
                user.setNim(nim);
                user.setNama(nama);
                user.setKata_sandi(pass);
                user.setEmail(email);
                
                userService.insertUser(user);
                
                RegisterSuccess();
            }
            catch (RemoteException ex) {
                ex.printStackTrace();
            }
        }
        else{
            RegisterFailed();
        }
    }
    
    @FXML
    private void logout(ActionEvent event) {
        tab_pemesanan.setDisable(true);
        tab_history_pemesanan.setDisable(true);
        tab_login.setDisable(false);
        tab_register_user.setDisable(false);
        tab_chat_client.setDisable(true);
    }

    @FXML
    private void doPemesanan(ActionEvent event) {
        if(isPemesananFormValid()){
            try {
                String nim = NimAkunLogin;
                int id_barang = Integer.valueOf(selected_id_barang.getText());
                String lokasi = txt_lokasi_transaksi.getText();
                int kuantitas = Integer.valueOf(txt_jumlah_pemesanan.getText());

                Pemesanan pemesanan = new Pemesanan();
                pemesanan.setNim(nim);
                pemesanan.setId_barang(id_barang);
                pemesanan.setLokasi_transaksi(lokasi);
                pemesanan.setKuantitas(kuantitas);
                pemesananService.insertPemesanan(pemesanan);
                
                PemesananSuccess();
                
            }
            catch (RemoteException ex) {
                PemesananFailed();
                ex.printStackTrace();
            }
        }
        else{
            PemesananFailed();  
        }
    }
    
    private boolean isLoginFieldValid() {
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
    public void setMain(Main main){
        this.main = main;
        this.barangService = main.getBarangService();
        this.userService = main.getUserService();
        this.pemesananService = main.getPemesananService();
        
        try {
            connection.startConnection();
        }
        catch (Exception ex) {
            Logger.getLogger(MainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            table_history_transaksi.getItems().setAll(pemesananService.getSemuaPemesanan(NimAkunLogin));
            table_barang.getItems().setAll(barangService.getAllBarang());
        }
        catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }
    private boolean isPemesananFormValid(){
        String errorMessage = "";
        if(txt_jumlah_pemesanan.getText().isEmpty() || txt_jumlah_pemesanan.getText() == null){
            errorMessage += "Field jumlah pemesanan tidak boleh kosong !!! \n";
        }
        if(txt_lokasi_transaksi.getText().isEmpty() || txt_lokasi_transaksi.getText() == null){
            errorMessage += "Field lokasi transaksi tidak boleh kosong !!! \n";
        }
        if(errorMessage.length() == 0){
            return true;
        }
        else{
            return false;
        }
    }
    private boolean isRegisterFormValid() {
        String errorMessage = "";
        if(register_nim.getText().isEmpty() || register_nim.getText() == null){
            errorMessage += "Field NIM tidak boleh kosong !!! \n";
        }
        if(register_nama.getText().isEmpty() || register_nama.getText() == null){
            errorMessage += "Field nama tidak boleh kosong !!! \n";
        }
        if(register_pass.getText().isEmpty() || register_pass.getText() == null){
            errorMessage += "Field Password tidak boleh kosong !!! \n";
        }
        if(register_repass.getText().isEmpty() || register_repass.getText() == null){
            errorMessage += "Field Re-Type Password tidak boleh kosong !!! \n";
        }
        if(register_email.getText().isEmpty() || register_email.getText() == null){
            errorMessage += "Field Email tidak boleh kosong !!! \n";
        }
        if(register_pass.getText().equals(register_repass.getText()) == false){
             errorMessage += "Field Password dan Re-type Password harus sama !!! \n";
        }
        if(errorMessage.length() == 0){
            return true;
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Mohon isi semua field dengan baik");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }
    private void RegisterSuccess(){
        String message = "Success";
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("Data Inserting");
            alert.setHeaderText("Registrasi Akun Berhasil Dilakukan");
            alert.setContentText(message);
            alert.showAndWait();
    }
    
    private void RegisterFailed(){
        String message = "Failed";
        Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("Data Inserting");
            alert.setHeaderText("Registrasi Akun Gagal Dilakukan");
            alert.setContentText(message);
            alert.showAndWait();
    }
    
    private void LoginSuccess(){
        String message = "Login Success";
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("Login Validation");
            alert.setHeaderText("Login Berhasil Dilakukan");
            alert.setContentText(message);
            alert.showAndWait();
    }
    private void LoginFailed(){
        String message = "Login Failed";
        Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("Login Validation");
            alert.setHeaderText("Login Gagal Dilakukan");
            alert.setContentText(message);
            alert.showAndWait();
    }
    private void PemesananFailed(){
        String message = "Pemesanan Gagal";
        Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("Validasi Form Pemesanan");
            alert.setHeaderText("Pemesanan Gagal Dilakukan");
            alert.setContentText(message);
            alert.showAndWait();
    }
    private void PemesananSuccess(){
        String message = "Pemesanan Success";
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("Validasi Form Pemesanan");
            alert.setHeaderText("Pemesanan Berhasil Dilakukan");
            alert.setContentText(message);
            alert.showAndWait();
    }

    @FXML
    private void RefreshHistory(ActionEvent event) {
        try {
            table_history_transaksi.getItems().setAll(pemesananService.getSemuaPemesanan(NimAkunLogin));
        }
        catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }

    // Chat Methods
    
    private Server createServer(){
        return new Server(5544, data -> {
            Platform.runLater(() -> {
                txt_area_chat_client.appendText(data.toString() + "\n");
            });
        });
    }
    
    private Client createClient(){
        return new Client("127.0.0.1",5544, data -> {
            Platform.runLater(() -> {
                txt_area_chat_client.appendText(data.toString() + "\n");
            });
        });
    }
    
    @FXML
    private void sendMessages(ActionEvent event) {
        String message = isServer ? "Server: " : "Client: ";
        message += txt_field_chat_client.getText();
        txt_field_chat_client.clear();
        
        txt_area_chat_client.appendText(message + "\n");
        try {
            connection.send(message);
        }
        catch (Exception ex) {
            txt_area_chat_client.appendText("Failed to send message\n");
        }
    }
}
