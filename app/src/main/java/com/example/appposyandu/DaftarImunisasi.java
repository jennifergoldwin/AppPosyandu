package com.example.appposyandu;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class DaftarImunisasi implements Serializable {
    private String nama;
    private String tanggal;
    private String urutan;
    private String imunisasi;
    private String key;

    public DaftarImunisasi(){

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getUrutan() {
        return urutan;
    }

    public void setUrutan(String urutan) {
        this.urutan = urutan;
    }

    public String getImunisasi() {
        return imunisasi;
    }

    public void setImunisasi(String imunisasi) {
        this.imunisasi = imunisasi;
    }

    @Override
    public String toString() {
        return " "+nama+"\n" +
                " "+tanggal +"\n" +
                " "+urutan +"\n" +
                " "+imunisasi;
    }

    public DaftarImunisasi(String nm, String tgl, String urtn, String imns){
        nama = nm;
        tanggal = tgl;
        urutan = urtn;
        imunisasi = imns;
    }
}
