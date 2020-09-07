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
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author STU
 */
public class User implements Externalizable{
    private final StringProperty nim = new SimpleStringProperty();
    private final StringProperty nama = new SimpleStringProperty();
    private final StringProperty kata_sandi = new SimpleStringProperty();
    private final StringProperty email = new SimpleStringProperty();

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String value) {
        email.set(value);
    }

    public StringProperty emailProperty() {
        return email;
    }
    
    public String getKata_sandi() {
        return kata_sandi.get();
    }

    public void setKata_sandi(String value) {
        kata_sandi.set(value);
    }

    public StringProperty kata_sandiProperty() {
        return kata_sandi;
    }  
    
    public String getNama() {
        return nama.get();
    }

    public void setNama(String value) {
        nama.set(value);
    }

    public StringProperty namaProperty() {
        return nama;
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

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(getNim());
        out.writeObject(getNama());
        out.writeObject(getKata_sandi());
        out.writeObject(getEmail());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        setNim((String) in.readObject());
        setNama((String) in.readObject());
        setKata_sandi((String) in.readObject());
        setEmail((String) in.readObject());
    }
    
}
