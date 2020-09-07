/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rmi.client;

import com.rmi.api.entity.Barang;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import rmi.rmi.api.service.BarangService;

/**
 * FXML Controller class
 *
 * @author STU
 */
public class FormBarangController implements Initializable {
    @FXML
    private TextField txt_id_barang;
    @FXML
    private TextField txt_nama_barang;
    @FXML
    private TextField txt_harga_barang;
    @FXML
    private TextField txt_jenis_barang;
    @FXML
    private TableView<Barang> table_barang;
    @FXML
    private TableColumn<Barang, Integer> col_id_barang;
    @FXML
    private TableColumn<Barang, String> col_nama_barang;
    @FXML
    private TableColumn<Barang, String> col_jenis_barang;
    @FXML
    private TableColumn<Barang, Float> col_harga_barang;

    private Main main;
    
    private BarangService barangService;
    
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
                txt_nama_barang.setText(newBarang.getNama_barang());
                txt_jenis_barang.setText(newBarang.getJenis());
                txt_harga_barang.setText(String.valueOf(newBarang.getHarga()));
                txt_id_barang.setText(String.valueOf(newBarang.getId_barang()));
            }
        });
    }    

    @FXML
    private void onInsert(ActionEvent event) {
        if(isFieldValid()){
            Barang barang = new Barang();
            try {
                barang.setNama_barang(txt_nama_barang.getText());
                barang.setJenis(txt_jenis_barang.getText());
                barang.setHarga(Float.valueOf(txt_harga_barang.getText()));
                barangService.insertBarang(barang);

                table_barang.getItems().add(barang);

                clearFields();
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
            onRefresh(event);
        }
    }

    @FXML
    private void onDelete(ActionEvent event) {
        try {
            Barang barang = table_barang.getSelectionModel().getSelectedItem();
            if(barang == null){
                return;
            }
            barangService.deleteBarang(barang.getId_barang());
            table_barang.getItems().remove(barang);
            clearFields();
        }
        catch (RemoteException ex) {
            ex.printStackTrace();
        }
        onRefresh(event);
    }

    @FXML
    private void onUpdate(ActionEvent event) {
        int index = table_barang.getSelectionModel().getSelectedIndex();
        
        if(index == -1){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("Update");
            alert.setHeaderText("No Barang Selected");
            alert.setContentText("Please Select Barang in the table");
            alert.showAndWait();
            return;
        }
        if(isFieldValid()){
            try {
                Barang barang = new Barang();
                barang.setId_barang(Integer.valueOf(txt_id_barang.getText()));
                barang.setNama_barang(txt_nama_barang.getText());
                barang.setJenis(txt_jenis_barang.getText());
                barang.setHarga(Float.valueOf(txt_harga_barang.getText()));
                barangService.updateBarang(barang);
            }
            catch(RemoteException ex) {
                ex.printStackTrace();
            }
        }
        onRefresh(event);
    }

    @FXML
    private void onRefresh(ActionEvent event) {
        try {
            table_barang.getItems().setAll(barangService.getAllBarang());
        }
        catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }
    
    public void setMain(Main main){
        this.main = main;
        this.barangService = main.getBarangService();
        
        try {
            table_barang.getItems().setAll(barangService.getAllBarang());
        }
        catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }

    private void clearFields() {
        txt_nama_barang.setText("");
        txt_jenis_barang.setText("");
        txt_harga_barang.setText("");
    }

    private boolean isFieldValid() {
        String errorMessage = "";
        if(txt_nama_barang.getText().isEmpty() || txt_nama_barang.getText() == null){
            errorMessage += "Field nama tidak boleh kosong !!! \n";
        }
        if(txt_jenis_barang.getText().isEmpty() || txt_jenis_barang.getText() == null){
            errorMessage += "Field jenis tidak boleh kosong !!! \n";
        }
        if(txt_harga_barang.getText().isEmpty() || txt_harga_barang.getText() == null){
            errorMessage += "Field harga tidak boleh kosong !!! \n";
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
}
