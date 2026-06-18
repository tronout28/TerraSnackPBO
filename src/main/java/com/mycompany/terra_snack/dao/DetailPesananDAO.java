/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.terra_snack.dao;

import com.mycompany.terra_snack.database.DatabaseConnection;
import com.mycompany.terra_snack.model.DetailPesanan;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ftih_28
 */
public class DetailPesananDAO {

    private Connection conn;

    public DetailPesananDAO() {
        conn = DatabaseConnection.getConnection();
    }

    public List<DetailPesanan> getAll() {
        List<DetailPesanan> list = new ArrayList<>();

        String sql = "SELECT * FROM detail_pesanan";

        try (
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql)
        ) {

            while (rs.next()) {

                DetailPesanan dp = new DetailPesanan();

                dp.setDetailId(rs.getInt("detail_id"));
                dp.setPesananId(rs.getInt("pesanan_id"));
                dp.setProdukId(rs.getInt("produk_id"));
                dp.setVarianId(rs.getInt("varian_id"));
                dp.setQty(rs.getInt("qty"));
                dp.setHargaSatuan(rs.getBigDecimal("harga_satuan"));
                dp.setSubtotal(rs.getBigDecimal("subtotal"));

                list.add(dp);
            }

        } catch (SQLException e) {
            System.out.println("Error getAll DetailPesanan: " + e.getMessage());
        }

        return list;
    }

    public boolean insert(DetailPesanan dp) {

        String sql = """
                     INSERT INTO detail_pesanan
                     (pesanan_id, produk_id, varian_id,
                      qty, harga_satuan, subtotal)
                     VALUES (?, ?, ?, ?, ?, ?)
                     """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, dp.getPesananId());
            ps.setInt(2, dp.getProdukId());
            ps.setInt(3, dp.getVarianId());
            ps.setInt(4, dp.getQty());
            ps.setBigDecimal(5, dp.getHargaSatuan());
            ps.setBigDecimal(6, dp.getSubtotal());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error insert DetailPesanan: " + e.getMessage());
        }

        return false;
    }

    public boolean update(DetailPesanan dp) {

        String sql = """
                     UPDATE detail_pesanan
                     SET pesanan_id = ?,
                         produk_id = ?,
                         varian_id = ?,
                         qty = ?,
                         harga_satuan = ?,
                         subtotal = ?
                     WHERE detail_id = ?
                     """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, dp.getPesananId());
            ps.setInt(2, dp.getProdukId());
            ps.setInt(3, dp.getVarianId());
            ps.setInt(4, dp.getQty());
            ps.setBigDecimal(5, dp.getHargaSatuan());
            ps.setBigDecimal(6, dp.getSubtotal());
            ps.setInt(7, dp.getDetailId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error update DetailPesanan: " + e.getMessage());
        }

        return false;
    }

    public boolean delete(int detailId) {

        String sql = "DELETE FROM detail_pesanan WHERE detail_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, detailId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error delete DetailPesanan: " + e.getMessage());
        }

        return false;
    }

    public DetailPesanan getById(int detailId) {

        String sql = "SELECT * FROM detail_pesanan WHERE detail_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, detailId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                DetailPesanan dp = new DetailPesanan();

                dp.setDetailId(rs.getInt("detail_id"));
                dp.setPesananId(rs.getInt("pesanan_id"));
                dp.setProdukId(rs.getInt("produk_id"));
                dp.setVarianId(rs.getInt("varian_id"));
                dp.setQty(rs.getInt("qty"));
                dp.setHargaSatuan(rs.getBigDecimal("harga_satuan"));
                dp.setSubtotal(rs.getBigDecimal("subtotal"));

                return dp;
            }

        } catch (SQLException e) {
            System.out.println("Error getById DetailPesanan: " + e.getMessage());
        }

        return null;
    }
}
