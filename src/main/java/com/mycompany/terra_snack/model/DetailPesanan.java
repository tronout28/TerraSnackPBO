/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.terra_snack.model;

import java.math.BigDecimal;

/**
 *
 * @author ftih_28
 */
public class DetailPesanan {

    private int detailId;
    private int pesananId;
    private int produkId;
    private int varianId;
    private int qty;
    private BigDecimal hargaSatuan;
    private BigDecimal subtotal;

    public DetailPesanan() {
    }

    public DetailPesanan(int detailId, int pesananId, int produkId,
            int varianId, int qty, BigDecimal hargaSatuan,
            BigDecimal subtotal) {

        this.detailId = detailId;
        this.pesananId = pesananId;
        this.produkId = produkId;
        this.varianId = varianId;
        this.qty = qty;
        this.hargaSatuan = hargaSatuan;
        this.subtotal = subtotal;
    }

    public int getDetailId() {
        return detailId;
    }

    public void setDetailId(int detailId) {
        this.detailId = detailId;
    }

    public int getPesananId() {
        return pesananId;
    }

    public void setPesananId(int pesananId) {
        this.pesananId = pesananId;
    }

    public int getProdukId() {
        return produkId;
    }

    public void setProdukId(int produkId) {
        this.produkId = produkId;
    }

    public int getVarianId() {
        return varianId;
    }

    public void setVarianId(int varianId) {
        this.varianId = varianId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public BigDecimal getHargaSatuan() {
        return hargaSatuan;
    }

    public void setHargaSatuan(BigDecimal hargaSatuan) {
        this.hargaSatuan = hargaSatuan;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}
