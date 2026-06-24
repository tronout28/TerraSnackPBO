/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.terra_snack.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mycompany.terra_snack.database.DatabaseConnection;
import com.mycompany.terra_snack.model.DetailPesanan;

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

    String cekStok =
        "SELECT stok_varian FROM varian_produk WHERE varian_id = ?";

    String updateStok =
        "UPDATE varian_produk SET stok_varian = stok_varian - ? WHERE varian_id = ?";

    try {

        conn.setAutoCommit(false);

        // cek stok
        PreparedStatement psCek = conn.prepareStatement(cekStok);
        psCek.setInt(1, dp.getVarianId());

        ResultSet rs = psCek.executeQuery();

        if (rs.next()) {
            int stok = rs.getInt("stok_varian");

            if (stok < dp.getQty()) {
                conn.rollback();
                System.out.println("Stok tidak cukup");
                return false;
            }
        }

        // insert detail pesanan
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1, dp.getPesananId());
        ps.setInt(2, dp.getProdukId());
        ps.setInt(3, dp.getVarianId());
        ps.setInt(4, dp.getQty());
        ps.setBigDecimal(5, dp.getHargaSatuan());
        ps.setBigDecimal(6, dp.getSubtotal());

        int hasil = ps.executeUpdate();

        if (hasil > 0) {

            // kurangi stok
            PreparedStatement psStok =
                    conn.prepareStatement(updateStok);

            psStok.setInt(1, dp.getQty());
            psStok.setInt(2, dp.getVarianId());

            psStok.executeUpdate();
        }

        conn.commit();
        return true;

    } catch (SQLException e) {

        try {
            conn.rollback();
        } catch (SQLException ex) {
        }

        System.out.println("Error insert DetailPesanan : "
                + e.getMessage());
    } finally {

        try {
            conn.setAutoCommit(true);
        } catch (SQLException e) {
        }
    }

    return false;
}

    public boolean update(DetailPesanan dp) {

    String ambil =
        "SELECT qty FROM detail_pesanan WHERE detail_id=?";

    String update =
        """
        UPDATE detail_pesanan
        SET pesanan_id=?,
            produk_id=?,
            varian_id=?,
            qty=?,
            harga_satuan=?,
            subtotal=?
        WHERE detail_id=?
        """;

    String updateStok =
        "UPDATE varian_produk SET stok_varian = stok_varian - ? WHERE varian_id=?";

    try {

        conn.setAutoCommit(false);

        int qtyLama = 0;

        PreparedStatement ps1 =
                conn.prepareStatement(ambil);

        ps1.setInt(1, dp.getDetailId());

        ResultSet rs = ps1.executeQuery();

        if (rs.next()) {
            qtyLama = rs.getInt("qty");
        }

        int selisih = dp.getQty() - qtyLama;

        if (selisih != 0) {

            PreparedStatement ps2 =
                    conn.prepareStatement(updateStok);

            ps2.setInt(1, selisih);
            ps2.setInt(2, dp.getVarianId());

            ps2.executeUpdate();
        }

        PreparedStatement ps3 =
                conn.prepareStatement(update);

        ps3.setInt(1, dp.getPesananId());
        ps3.setInt(2, dp.getProdukId());
        ps3.setInt(3, dp.getVarianId());
        ps3.setInt(4, dp.getQty());
        ps3.setBigDecimal(5, dp.getHargaSatuan());
        ps3.setBigDecimal(6, dp.getSubtotal());
        ps3.setInt(7, dp.getDetailId());

        int hasil = ps3.executeUpdate();

        conn.commit();

        return hasil > 0;

    } catch (SQLException e) {

        try {
            conn.rollback();
        } catch (SQLException ex) {
        }

        System.out.println(e.getMessage());
    } finally {

        try {
            conn.setAutoCommit(true);
        } catch (SQLException e) {
        }
    }

    return false;
}

    public boolean delete(int detailId) {

    String ambil =
        "SELECT varian_id, qty FROM detail_pesanan WHERE detail_id=?";

    String tambahStok =
        "UPDATE varian_produk SET stok_varian = stok_varian + ? WHERE varian_id=?";

    String hapus =
        "DELETE FROM detail_pesanan WHERE detail_id=?";

    try {

        conn.setAutoCommit(false);

        int varianId = 0;
        int qty = 0;

        PreparedStatement ps1 =
                conn.prepareStatement(ambil);

        ps1.setInt(1, detailId);

        ResultSet rs = ps1.executeQuery();

        if (rs.next()) {
            varianId = rs.getInt("varian_id");
            qty = rs.getInt("qty");
        }

        PreparedStatement ps2 =
                conn.prepareStatement(tambahStok);

        ps2.setInt(1, qty);
        ps2.setInt(2, varianId);

        ps2.executeUpdate();

        PreparedStatement ps3 =
                conn.prepareStatement(hapus);

        ps3.setInt(1, detailId);

        int hasil = ps3.executeUpdate();

        conn.commit();

        return hasil > 0;

    } catch (SQLException e) {

        try {
            conn.rollback();
        } catch (SQLException ex) {
        }

        System.out.println(e.getMessage());
    } finally {

        try {
            conn.setAutoCommit(true);
        } catch (SQLException e) {
        }
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
