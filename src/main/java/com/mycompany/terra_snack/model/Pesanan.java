package com.mycompany.terra_snack.model;

public class Pesanan {
     private int pesananId;
     private int pelangganId;
     private String tanggalPesanan;
     private String statusPesanan;
     
     public Pesanan() {
     }
     
     public Pesanan(int pesananId, int pelangganId,
                           String tanggalPesanan, String statusPesanan) {
     
          this.pesananId = pesananId;
          this.pelangganId = pelangganId;
          this.tanggalPesanan = tanggalPesanan;
          this.statusPesanan = statusPesanan;
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
     
     public String getTanggalPesanan() {
          return tanggalPesanan;
     }
     
     public void setTanggalPesanan(String tanggalPesanan) {
          this.tanggalPesanan = tanggalPesanan;
     }
     
     public String getStatusPesanan() {
          return statusPesanan;
     }
     
     public void setStatusPesanan(String statusPesanan) {
          this.statusPesanan = statusPesanan;
     }
}
