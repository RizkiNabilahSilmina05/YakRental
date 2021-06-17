package com.project.yakrental.Model;

public class CarModel {
    private String nama, noKendaraan, tipe, warna, harga;
    int gambar;

    public CarModel(String name, String no, String type, String color, String price, int image){
        nama = name;
        noKendaraan = no;
        tipe = type;
        warna = color;
        harga = price;
        gambar = image;
    }

    public String getNama() {
        return nama;
    }

    public String getNoKendaraan() {
        return noKendaraan;
    }

    public String getTipe() {
        return tipe;
    }

    public String getWarna() {
        return warna;
    }

    public String getHarga() {
        return harga;
    }

    public int getGambar() {
        return gambar;
    }
}
