/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.terra_snack.dao;

/**
 *
 * @author ASUS
 */
import com.mycompany.terra_snack.database.DatabaseConnection;
import com.mycompany.terra_snack.model.Pembayaran;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

public class PembayaranDAO {

    // 1. Fungsi untuk menyimpan bukti pembayaran baru
    public void simpanPembayaran(Pembayaran p) {
        String sql = "INSERT INTO pembayaran (pesanan_id, bukti_qris, tanggal_upload, status_verifikasi) VALUES (?, ?, ?, ?)";
        
        // try-with-resources: Koneksi akan otomatis ditutup setelah kode di dalam kurung selesai
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, p.getPesananId());
            pstmt.setString(2, p.getBuktiQris());
            pstmt.setTimestamp(3, Timestamp.valueOf(p.getTanggalUpload()));
            pstmt.setString(4, p.getStatusVerifikasi());
            
            pstmt.executeUpdate(); // Jalankan perintah INSERT
            System.out.println("Data pembayaran berhasil disimpan.");
            
        } catch (Exception e) {
            System.out.println("Error saat menyimpan: " + e.getMessage());
        }
    }

    // 2. Fungsi untuk Admin memvalidasi (mengubah status)
    public void updateStatus(int pembayaranId, String statusBaru, String catatan) {
        String sql = "UPDATE pembayaran SET status_verifikasi = ?, catatan_admin = ? WHERE pembayaran_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, statusBaru);
            pstmt.setString(2, catatan);
            pstmt.setInt(3, pembayaranId);
            
            pstmt.executeUpdate(); // Jalankan perintah UPDATE
            System.out.println("Status pembayaran berhasil diupdate.");
            
        } catch (Exception e) {
            System.out.println("Error saat update: " + e.getMessage());
        }
    }
}
