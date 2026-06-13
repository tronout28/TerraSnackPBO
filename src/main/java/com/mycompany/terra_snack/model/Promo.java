/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.terra_snack.model;

/**
 *
 * @author YodhaAce
 */
public class Promo {

    private int promoId;
    private String kodePromo;
    private String namaPromo;
    private String jenisDiskon;
    private double nilaiDiskon;
    private String tanggalMulai;
    private String tanggalSelesai;
    private String status;

    public Promo(int promoId, String kodePromo, String namaPromo, String jenisDiskon, double nilaiDiskon, String tanggalMulai, String tanggalSelesai, String status) {
        this.promoId = promoId;
        this.kodePromo = kodePromo;
        this.namaPromo = namaPromo;
        this.jenisDiskon = jenisDiskon;
        this.nilaiDiskon = nilaiDiskon;
        this.tanggalMulai = tanggalMulai;
        this.tanggalSelesai = tanggalSelesai;
        this.status = status;
    }

    public int getPromoId() {
        return promoId;
    }
    public void setPromoId(int promoId) {
        this.promoId = promoId;
    }
    public String getKodePromo() {
        return kodePromo;
    }
    public void setKodePromo(String kodePromo) {
        this.kodePromo = kodePromo;
    }
    public String getNamaPromo() {
        return namaPromo;
    }
    public void setNamaPromo(String namaPromo) {
        this.namaPromo = namaPromo;
    }
    public String getJenisDiskon() {
        return jenisDiskon;
    }
    public void setJenisDiskon(String jenisDiskon) {
        this.jenisDiskon = jenisDiskon;
    }
    public double getNilaiDiskon() {
        return nilaiDiskon;
    }
    public void setNilaiDiskon(double nilaiDiskon) {
        this.nilaiDiskon = nilaiDiskon;
    }
    public String getTanggalMulai() {
        return tanggalMulai;
    }
    public void setTanggalMulai(String tanggalMulai) {
        this.tanggalMulai = tanggalMulai;
    }
    public String getTanggalSelesai() {
        return tanggalSelesai;
    }
    public void setTanggalSelesai(String tanggalSelesai) {
        this.tanggalSelesai = tanggalSelesai;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
