/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.terra_snack.model;

/**
 *
 * @author YodhaAce
 */
public class VarianProduk {

    private int varianId;
    private int produkId;
    private String namaVarian;
    private int stokVarian;

<<<<<<< HEAD
    public VarianProduk(){
        
    }
    
    public VarianProduk(int varianId, int produkId, String namaVarian, int stokVarian) {
=======
    public VarianProduk() {
    }

    public VarianProduk(int varianId, int produkId,
                        String namaVarian, int stokVarian) {
>>>>>>> e255fd5ea5af0527288b86b52ec74349be95f51a
        this.varianId = varianId;
        this.produkId = produkId;
        this.namaVarian = namaVarian;
        this.stokVarian = stokVarian;
    }
    
    public int getVarianId() {
        return varianId;
    }
    public void setVarianId(int varianId) {
        this.varianId = varianId;
    }
    public int getProdukId() {
        return produkId;
    }
    public void setProdukId(int produkId) {
        this.produkId = produkId;
    }
    public String getNamaVarian() {
        return namaVarian;
    }
    public void setNamaVarian(String namaVarian) {
        this.namaVarian = namaVarian;
    }
    public int getStokVarian() {
        return stokVarian;
    }
    public void setStokVarian(int stokVarian) {
        this.stokVarian = stokVarian;
    }
}
