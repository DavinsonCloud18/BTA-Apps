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
public class Barang implements Externalizable {
    private final IntegerProperty id_barang = new SimpleIntegerProperty();
    private final StringProperty nama_barang = new SimpleStringProperty();
    private final StringProperty jenis = new SimpleStringProperty();
    private final FloatProperty harga = new SimpleFloatProperty();

    public float getHarga() {
        return harga.get();
    }

    public void setHarga(float value) {
        harga.set(value);
    }

    public FloatProperty hargaProperty() {
        return harga;
    }
        
    
    public String getJenis() {
        return jenis.get();
    }

    public void setJenis(String value) {
        jenis.set(value);
    }

    public StringProperty jenisProperty() {
        return jenis;
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
    
    
    
    public int getId_barang() {
        return id_barang.get();
    }

    public void setId_barang(int value) {
        id_barang.set(value);
    }

    public IntegerProperty id_barangProperty() {
        return id_barang;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(getId_barang());
        out.writeObject(getNama_barang());
        out.writeObject(getJenis());
        out.writeFloat(getHarga());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        setId_barang(in.readInt());
        setNama_barang((String) in.readObject());
        setJenis((String) in.readObject());
        setHarga(in.readFloat());
                
    }
    
}
