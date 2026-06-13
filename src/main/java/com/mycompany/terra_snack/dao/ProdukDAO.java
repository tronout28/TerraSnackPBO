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
import com.mycompany.terra_snack.model.Produk;
import com.mycompany.terra_snack.model.VarianProduk;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdukDAO {

    public List<Produk> getAllProduk() {
        List<Produk> list = new ArrayList<>();
        String sql = "SELECT * FROM produk";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Produk p = new Produk(
                        rs.getInt("produk_id"),
                        rs.getString("nama_produk"),
                        rs.getDouble("harga"),
                        rs.getInt("stok"),
                        rs.getInt("minimal_order"),
                        rs.getString("status_produk"),
                        rs.getString("gambar_produk")
                );
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insertProduk(Produk p) {
        String sql = "INSERT INTO produk (nama_produk, harga, stok, minimal_order, status_produk, gambar_produk) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getNamaProduk());
            ps.setDouble(2, p.getHarga());
            ps.setInt(3, p.getStok());
            ps.setInt(4, p.getMinimalOrder());
            ps.setString(5, p.getStatusProduk());
            ps.setString(6, p.getGambarProduk());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateProduk(Produk p) {
        String sql = "UPDATE produk SET nama_produk = ?, harga = ?, stok = ?, minimal_order = ?, status_produk = ?, gambar_produk = ? WHERE produk_id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getNamaProduk());
            ps.setDouble(2, p.getHarga());
            ps.setInt(3, p.getStok());
            ps.setInt(4, p.getMinimalOrder());
            ps.setString(5, p.getStatusProduk());
            ps.setString(6, p.getGambarProduk());
            ps.setInt(7, p.getProdukId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteProduk(int produkId) {
        String sql = "DELETE FROM produk WHERE produk_id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, produkId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Produk> searchProduk(String keyword) {
        List<Produk> list = new ArrayList<>();
        String sql = "SELECT * FROM produk WHERE nama_produk LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Produk p = new Produk(
                            rs.getInt("produk_id"),
                            rs.getString("nama_produk"),
                            rs.getDouble("harga"),
                            rs.getInt("stok"),
                            rs.getInt("minimal_order"),
                            rs.getString("status_produk"),
                            rs.getString("gambar_produk")
                    );
                    list.add(p);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<VarianProduk> getVarianByProduk(int produkId) {
        List<VarianProduk> list = new ArrayList<>();
        String sql = "SELECT * FROM varian_produk WHERE produk_id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, produkId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    VarianProduk v = new VarianProduk(
                            rs.getInt("varian_id"),
                            rs.getInt("produk_id"),
                            rs.getString("nama_varian"),
                            rs.getInt("stok_varian")
                    );
                    list.add(v);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insertVarian(VarianProduk v) {
        String sql = "INSERT INTO varian_produk (produk_id, nama_varian, stok_varian) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, v.getProdukId());
            ps.setString(2, v.getNamaVarian());
            ps.setInt(3, v.getStokVarian());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateVarian(VarianProduk v) {
        String sql = "UPDATE varian_produk SET nama_varian = ?, stok_varian = ? WHERE varian_id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, v.getNamaVarian());
            ps.setInt(2, v.getStokVarian());
            ps.setInt(3, v.getVarianId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteVarian(int varianId) {
        String sql = "DELETE FROM varian_produk WHERE varian_id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, varianId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
