/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.terra_snack.model;

import java.sql.Timestamp;

/**
 *
 * @author Tannn939
 */
public class Pesanan {

    private int       pesananId;
    private int       pelangganId;
    private Timestamp tanggalPesanan;
    private String    statusPesanan;

    public Pesanan() {
    }

    public Pesanan(int pesananId, int pelangganId,
                   Timestamp tanggalPesanan, String statusPesanan) {
        this.pesananId      = pesananId;
        this.pelangganId    = pelangganId;
        this.tanggalPesanan = tanggalPesanan;
        this.statusPesanan  = statusPesanan;
    }

    public int getPesananId() {
        return pesananId;
    }

    public void setPesananId(int pesananId) {
        this.pesananId = pesananId;
    }

    public int getPelangganId() {
        return pelangganId;
    }

    public void setPelangganId(int pelangganId) {
        this.pelangganId = pelangganId;
    }

    public Timestamp getTanggalPesanan() {
        return tanggalPesanan;
    }

    public void setTanggalPesanan(Timestamp tanggalPesanan) {
        this.tanggalPesanan = tanggalPesanan;
    }

    public String getStatusPesanan() {
        return statusPesanan;
    }

    public void setStatusPesanan(String statusPesanan) {
        this.statusPesanan = statusPesanan;
    }
}
