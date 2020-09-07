/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rmi.api.entity;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author STU
 */
public class Pemesanan implements Externalizable{
    private final IntegerProperty id_transaksi = new SimpleIntegerProperty();
    private final StringProperty nim = new SimpleStringProperty();
    private final IntegerProperty id_barang = new SimpleIntegerProperty();
    private final StringProperty lokasi_transaksi = new SimpleStringProperty();
    private final IntegerProperty kuantitas = new SimpleIntegerProperty();
    private final StringProperty nama_barang = new SimpleStringProperty();
    private final StringProperty jenis_barang = new SimpleStringProperty();
    private final FloatProperty harga_barang = new SimpleFloatProperty();

    public float getHarga_barang() {
        return harga_barang.get();
    }

    public void setHarga_barang(float value) {
        harga_barang.set(value);
    }

    public FloatProperty harga_barangProperty() {
        return harga_barang;
    }

    public String getJenis_barang() {
        return jenis_barang.get();
    }

    public void setJenis_barang(String value) {
        jenis_barang.set(value);
    }

    public StringProperty jenis_barangProperty() {
        return jenis_barang;
    }

    public String getNama_barang() {
        return nama_barang.get();
    }

    public void setNama_barang(String value) {
        nama_barang.set(value);
    }

    public StringProperty nama_barangProperty() {
        return nama_barang;
    }

    public int getKuantitas() {
        return kuantitas.get();
    }

    public void setKuantitas(int value) {
        kuantitas.set(value);
    }

    public IntegerProperty kuantitasProperty() {
        return kuantitas;
    }
    
    public String getLokasi_transaksi() {
        return lokasi_transaksi.get();
    }

    public void setLokasi_transaksi(String value) {
        lokasi_transaksi.set(value);
    }

    public StringProperty lokasi_transaksiProperty() {
        return lokasi_transaksi;
    }
    
    public int getId_barang() {
        return id_barang.get();
    }

    public void setId_barang(int value) {
        id_barang.set(value);
    }

    public IntegerProperty id_barangProperty() {
        return id_barang;
    }
    
    public String getNim() {
        return nim.get();
    }

    public void setNim(String value) {
        nim.set(value);
    }

    public StringProperty nimProperty() {
        return nim;
    }
        
    public int getId_transaksi() {
        return id_transaksi.get();
    }

    public void setId_transaksi(int value) {
        id_transaksi.set(value);
    }

    public IntegerProperty id_transaksiProperty() {
        return id_transaksi;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(getId_transaksi());
        out.writeObject(getNim());
        out.writeInt(getId_barang());
        out.writeObject(getLokasi_transaksi());
        out.writeInt(getKuantitas());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        setId_transaksi(in.readInt());
        setNim((String)in.readObject());
        setId_barang(in.readInt());
        setLokasi_transaksi((String)in.readObject());
        setKuantitas(in.readInt());
    }
    
}
