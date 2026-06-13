/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.terra_snack.dao;

/**
 *
 * @author YodhaAce
 */
import com.mycompany.terra_snack.database.DatabaseConnection;
import com.mycompany.terra_snack.model.Promo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PromoDAO {

    public List<Promo> getAllPromo() {
        List<Promo> list = new ArrayList<>();
        String sql = "SELECT * FROM promo";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Promo pr = new Promo(
                        rs.getInt("promo_id"),
                        rs.getString("kode_promo"),
                        rs.getString("nama_promo"),
                        rs.getString("jenis_diskon"),
                        rs.getDouble("nilai_diskon"),
                        rs.getString("tanggal_mulai"),
                        rs.getString("tanggal_selesai"),
                        rs.getString("status")
                );
                list.add(pr);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insertPromo(Promo pr) {
        String sql = "INSERT INTO promo (kode_promo, nama_promo, jenis_diskon, nilai_diskon, tanggal_mulai, tanggal_selesai, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, pr.getKodePromo());
            ps.setString(2, pr.getNamaPromo());
            ps.setString(3, pr.getJenisDiskon());
            ps.setDouble(4, pr.getNilaiDiskon());
            ps.setString(5, pr.getTanggalMulai());
            ps.setString(6, pr.getTanggalSelesai());
            ps.setString(7, pr.getStatus());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePromo(Promo pr) {
        String sql = "UPDATE promo SET kode_promo = ?, nama_promo = ?, jenis_diskon = ?, nilai_diskon = ?, tanggal_mulai = ?, tanggal_selesai = ?, status = ? WHERE promo_id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, pr.getKodePromo());
            ps.setString(2, pr.getNamaPromo());
            ps.setString(3, pr.getJenisDiskon());
            ps.setDouble(4, pr.getNilaiDiskon());
            ps.setString(5, pr.getTanggalMulai());
            ps.setString(6, pr.getTanggalSelesai());
            ps.setString(7, pr.getStatus());
            ps.setInt(8, pr.getPromoId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletePromo(int promoId) {
        String sql = "DELETE FROM promo WHERE promo_id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, promoId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
