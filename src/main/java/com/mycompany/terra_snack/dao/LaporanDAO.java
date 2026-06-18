package com.mycompany.terra_snack.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mycompany.terra_snack.database.DatabaseConnection;

public class LaporanDAO {

    private final Connection conn;

    public LaporanDAO() {
        conn = DatabaseConnection.getConnection();
    }

    public double getTotalPendapatan() {
        double total = 0;

        try {
            String sql = "SELECT SUM(total_harga) AS total FROM pesanan";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                total = rs.getDouble("total");
            }

        } catch (Exception e) {
            System.out.println("Error getTotalPendapatan: " + e.getMessage());
        }

        return total;
    }

    public Map<String, Integer> getProdukTerlaris() {
        Map<String, Integer> data = new HashMap<>();

        try {
            String sql = "SELECT p.nama_produk, SUM(dp.qty) AS total " +
                    "FROM detail_pesanan dp " +
                    "JOIN produk p ON dp.produk_id = p.produk_id " +
                    "GROUP BY p.nama_produk " +
                    "ORDER BY total DESC";

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String nama = rs.getString("nama_produk");
                int total = rs.getInt("total");

                data.put(nama, total);
            }

        } catch (Exception e) {
            System.out.println("Error getProdukTerlaris: " + e.getMessage());
        }

        return data;
    }

    public List<String> getLaporanPenjualan() {
        List<String> list = new ArrayList<>();

        try {
            String sql = "SELECT tanggal_pesanan, total_harga FROM pesanan";

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String data = rs.getTimestamp("tanggal_pesanan") +
                        " | Rp" + rs.getDouble("total_harga");

                list.add(data);
            }

        } catch (Exception e) {
            System.out.println("Error getLaporanPenjualan: " + e.getMessage());
        }

        return list;
    }

    public Map<String, Double> getPenjualanPerHari() {

        Map<String, Double> data = new HashMap<>();

        try {

            String sql = "SELECT DATE(tanggal_pesanan) AS tanggal, " +
                    "SUM(total_harga) AS total " +
                    "FROM pesanan " +
                    "GROUP BY DATE(tanggal_pesanan) " +
                    "ORDER BY tanggal";

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                data.put(
                        rs.getString("tanggal"),
                        rs.getDouble("total"));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return data;
    }

    public Map<String, Integer> getJumlahPesananPerStatus() {

        Map<String, Integer> data = new HashMap<>();

        try {

            String sql = "SELECT status_pesanan, COUNT(*) total " +
                    "FROM pesanan " +
                    "GROUP BY status_pesanan";

            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                data.put(
                        rs.getString("status_pesanan"),
                        rs.getInt("total"));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return data;
    }
}