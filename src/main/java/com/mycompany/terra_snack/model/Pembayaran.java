/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.terra_snack.model;

/**
 *
 * @author ASUS
 */
import java.time.LocalDateTime;

public class Pembayaran {
    private int pembayaranId;
    private int pesananId;
    private String buktiQris;
    private LocalDateTime tanggalUpload;
    private String statusVerifikasi; // Menggunakan String biasa agar lebih mudah dipahami pemula daripada Enum
    private String catatanAdmin;

    // Konstruktor untuk menyimpan data baru
    public Pembayaran(int pesananId, String buktiQris, LocalDateTime tanggalUpload) {
        this.pesananId = pesananId;
        this.buktiQris = buktiQris;
        this.tanggalUpload = tanggalUpload;
        this.statusVerifikasi = "MENUNGGU"; // Default saat baru upload
    }

    // Getter dan Setter (Wajib ada agar DAO bisa membaca/menulis data)
    public int getPesananId() { return pesananId; }
    public String getBuktiQris() { return buktiQris; }
    public LocalDateTime getTanggalUpload() { return tanggalUpload; }
    public String getStatusVerifikasi() { return statusVerifikasi; }
    public void setStatusVerifikasi(String statusVerifikasi) { this.statusVerifikasi = statusVerifikasi; }
    public int getPembayaranId() { return pembayaranId; }
    public void setPembayaranId(int pembayaranId) { this.pembayaranId = pembayaranId; }
    public String getCatatanAdmin() { return catatanAdmin; }
    public void setCatatanAdmin(String catatanAdmin) { this.catatanAdmin = catatanAdmin; }
}
