/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.terra_snack.model;

/**
 *
 * @author YodhaAce
 */
public class Produk {

    private int produkId;
    private String namaProduk;
    private double harga;
    private int stok;
    private int minimalOrder;
    private String statusProduk;
    private String gambarProduk;

    public Produk(int produkId, String namaProduk, double harga, int stok, int minimalOrder, String statusProduk, String gambarProduk) {
        this.produkId = produkId;
        this.namaProduk = namaProduk;
        this.harga = harga;
        this.stok = stok;
        this.minimalOrder = minimalOrder;
        this.statusProduk = statusProduk;
        this.gambarProduk = gambarProduk;
    }

    public int getProdukId() {
        return produkId;
    }
    public void setProdukId(int produkId) {
        this.produkId = produkId;
    }
    public String getNamaProduk() {
        return namaProduk;
    }
    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }
    public double getHarga() {
        return harga;
    }
    public void setHarga(double harga) {
        this.harga = harga;
    }
    public int getStok() {
        return stok;
    }
    public void setStok(int stok) {
        this.stok = stok;
    }
    public int getMinimalOrder() {
        return minimalOrder;
    }
    public void setMinimalOrder(int minimalOrder) {
        this.minimalOrder = minimalOrder;
    }
    public String getStatusProduk() {
        return statusProduk;
    }
    public void setStatusProduk(String statusProduk) {
        this.statusProduk = statusProduk;
    }
    public String getGambarProduk() {
        return gambarProduk;
    }
    public void setGambarProduk(String gambarProduk) {
        this.gambarProduk = gambarProduk;
    }
}
