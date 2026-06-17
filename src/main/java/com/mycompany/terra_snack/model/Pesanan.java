/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.terra_snack.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 *
 * @author 
 */
public class Pesanan {

    private int       pesananId;
    private String    kodePesanan;
    private int       pelangganId;
    private String    jenisPesanan;     
    private Timestamp tanggalPesanan;
    private String    metodeFulfillment; 
    private String    catatan;
    private BigDecimal totalHarga;
    private String    statusPesanan;
   
    public Pesanan() {}

    public Pesanan(String kodePesanan, int pelangganId, String jenisPesanan,
                   Timestamp tanggalPesanan, String metodeFulfillment,
                   String catatan, BigDecimal totalHarga, String statusPesanan) {
        this.kodePesanan       = kodePesanan;
        this.pelangganId       = pelangganId;
        this.jenisPesanan      = jenisPesanan;
        this.tanggalPesanan    = tanggalPesanan;
        this.metodeFulfillment = metodeFulfillment;
        this.catatan           = catatan;
        this.totalHarga        = totalHarga;
        this.statusPesanan     = statusPesanan;
    }

    
    public int getPesananId() {
        return pesananId;
    }

    public void setPesananId(int pesananId) {
        this.pesananId = pesananId;
    }

    public String getKodePesanan() {
        return kodePesanan;
    }

    public void setKodePesanan(String kodePesanan) {
        this.kodePesanan = kodePesanan;
    }

    public int getPelangganId() {
        return pelangganId;
    }

    public void setPelangganId(int pelangganId) {
        this.pelangganId = pelangganId;
    }

    public String getJenisPesanan() {
        return jenisPesanan;
    }

    public void setJenisPesanan(String jenisPesanan) {
        this.jenisPesanan = jenisPesanan;
    }

    public Timestamp getTanggalPesanan() {
        return tanggalPesanan;
    }

    public void setTanggalPesanan(Timestamp tanggalPesanan) {
        this.tanggalPesanan = tanggalPesanan;
    }

    public String getMetodeFulfillment() {
        return metodeFulfillment;
    }

    public void setMetodeFulfillment(String metodeFulfillment) {
        this.metodeFulfillment = metodeFulfillment;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }

    public BigDecimal getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(BigDecimal totalHarga) {
        this.totalHarga = totalHarga;
    }

    public String getStatusPesanan() {
        return statusPesanan;
    }

    public void setStatusPesanan(String statusPesanan) {
        this.statusPesanan = statusPesanan;
    }

   
    @Override
    public String toString() {
        return "Pesanan{"
                + "pesananId=" + pesananId
                + ", kodePesanan='" + kodePesanan + '\''
                + ", pelangganId=" + pelangganId
                + ", jenisPesanan='" + jenisPesanan + '\''
                + ", tanggalPesanan=" + tanggalPesanan
                + ", metodeFulfillment='" + metodeFulfillment + '\''
                + ", catatan='" + catatan + '\''
                + ", totalHarga=" + totalHarga
                + ", statusPesanan='" + statusPesanan + '\''
                + '}';
    }
}
