package com.mycompany.terra_snack.model;

public class Pelanggan {
     private int pelangganId;
     private String nama;
     private String alamat;
     private String noHp;
     
     public Pelanggan() {
     }
     
     public Pelanggan(int pelangganId, String nama,
                           String alamat, String noHp) {
     
          this.pelangganId = pelangganId;
          this.nama = nama;
          this.alamat = alamat;
          this.noHp = noHp;
     }
     
     public int getPelangganId() {
          return pelangganId;
     }
     
     public void setPelangganId(int pelangganId) {
          this.pelangganId = pelangganId;
     }
     
     public String getNama() {
          return nama;
     }
     
     public void setNama(String nama) {
          this.nama = nama;
     }
     
     public String getAlamat() {
          return alamat;
     }
     
     public void setAlamat(String alamat) {
          this.alamat = alamat;
     }
     
     public String getNoHp() {
          return noHp;
     }
     
     public void setNoHp(String noHp) {
          this.noHp = noHp;
     }
}
