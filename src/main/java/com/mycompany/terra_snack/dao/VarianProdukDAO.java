package com.mycompany.terra_snack.dao;

import com.mycompany.terra_snack.database.DatabaseConnection;
import com.mycompany.terra_snack.model.VarianProduk;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VarianProdukDAO {

    private Connection conn;

    public VarianProdukDAO() {
        conn = DatabaseConnection.getConnection();
    }

    public List<VarianProduk> getAll() {

        List<VarianProduk> list = new ArrayList<>();

        String sql = "SELECT * FROM varian_produk";

        try (
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {

                VarianProduk varian = new VarianProduk();

                varian.setVarianId(rs.getInt("varian_id"));
                varian.setProdukId(rs.getInt("produk_id"));
                varian.setNamaVarian(rs.getString("nama_varian"));
                varian.setStokVarian(rs.getInt("stok_varian"));

                list.add(varian);
            }

        } catch (SQLException e) {
            System.out.println("Error getAll VarianProduk: " + e.getMessage());
        }

        return list;
    }

    public VarianProduk getById(int varianId) {

        String sql = "SELECT * FROM varian_produk WHERE varian_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, varianId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                VarianProduk varian = new VarianProduk();

                varian.setVarianId(rs.getInt("varian_id"));
                varian.setProdukId(rs.getInt("produk_id"));
                varian.setNamaVarian(rs.getString("nama_varian"));
                varian.setStokVarian(rs.getInt("stok_varian"));

                return varian;
            }

        } catch (SQLException e) {
            System.out.println("Error getById VarianProduk: " + e.getMessage());
        }

        return null;
    }

    public List<VarianProduk> getByProdukId(int produkId) {

        List<VarianProduk> list = new ArrayList<>();

        String sql = "SELECT * FROM varian_produk WHERE produk_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, produkId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                VarianProduk varian = new VarianProduk();

                varian.setVarianId(rs.getInt("varian_id"));
                varian.setProdukId(rs.getInt("produk_id"));
                varian.setNamaVarian(rs.getString("nama_varian"));
                varian.setStokVarian(rs.getInt("stok_varian"));

                list.add(varian);
            }

        } catch (SQLException e) {
            System.out.println("Error getByProdukId: " + e.getMessage());
        }

        return list;
    }

    public boolean insert(VarianProduk varian) {

        String sql = """
                INSERT INTO varian_produk
                (produk_id, nama_varian, stok_varian)
                VALUES (?, ?, ?)
                """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, varian.getProdukId());
            ps.setString(2, varian.getNamaVarian());
            ps.setInt(3, varian.getStokVarian());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error insert VarianProduk: " + e.getMessage());
        }

        return false;
    }

    public boolean update(VarianProduk varian) {

        String sql = """
                UPDATE varian_produk
                SET produk_id = ?,
                    nama_varian = ?,
                    stok_varian = ?
                WHERE varian_id = ?
                """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, varian.getProdukId());
            ps.setString(2, varian.getNamaVarian());
            ps.setInt(3, varian.getStokVarian());
            ps.setInt(4, varian.getVarianId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error update VarianProduk: " + e.getMessage());
        }

        return false;
    }

    public boolean delete(int varianId) {

        String sql = "DELETE FROM varian_produk WHERE varian_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, varianId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error delete VarianProduk: " + e.getMessage());
        }

        return false;
    }
}