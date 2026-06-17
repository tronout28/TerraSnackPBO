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
import com.mycompany.terra_snack.model.Pesanan;

/**
 *
 * @author Tannn939
 */
public class PesananDAO {

    public boolean insert(Pesanan pesanan) {

        String sql =
                "INSERT INTO pesanan "
                + "(pelanggan_id, tanggal_pesanan, status_pesanan) "
                + "VALUES (?, ?, ?)";

        try (Connection conn =
                     DatabaseConnection.getConnection();
             PreparedStatement ps =
                     conn.prepareStatement(sql)) {

            ps.setInt(1, pesanan.getPelangganId());
            ps.setTimestamp(2, pesanan.getTanggalPesanan());
            ps.setString(3, pesanan.getStatusPesanan());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    public List<Pesanan> getAllPesanan() {

        List<Pesanan> list = new ArrayList<>();

        String sql = "SELECT * FROM pesanan";

        try (Connection conn =
                     DatabaseConnection.getConnection();
             Statement st =
                     conn.createStatement();
             ResultSet rs =
                     st.executeQuery(sql)) {

            while (rs.next()) {

                Pesanan pesanan = new Pesanan();

                pesanan.setPesananId(rs.getInt("pesanan_id"));
                pesanan.setPelangganId(rs.getInt("pelanggan_id"));
                pesanan.setTanggalPesanan(rs.getTimestamp("tanggal_pesanan"));
                pesanan.setStatusPesanan(rs.getString("status_pesanan"));

                list.add(pesanan);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }


    public Pesanan getPesananById(int id) {

        String sql =
                "SELECT * FROM pesanan WHERE pesanan_id = ?";

        try (Connection conn =
                     DatabaseConnection.getConnection();
             PreparedStatement ps =
                     conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Pesanan pesanan = new Pesanan();

                pesanan.setPesananId(rs.getInt("pesanan_id"));
                pesanan.setPelangganId(rs.getInt("pelanggan_id"));
                pesanan.setTanggalPesanan(rs.getTimestamp("tanggal_pesanan"));
                pesanan.setStatusPesanan(rs.getString("status_pesanan"));

                return pesanan;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    public boolean update(Pesanan pesanan) {

        String sql =
                "UPDATE pesanan SET "
                + "pelanggan_id = ?, "
                + "tanggal_pesanan = ?, "
                + "status_pesanan = ? "
                + "WHERE pesanan_id = ?";

        try (Connection conn =
                     DatabaseConnection.getConnection();
             PreparedStatement ps =
                     conn.prepareStatement(sql)) {

            ps.setInt(1, pesanan.getPelangganId());
            ps.setTimestamp(2, pesanan.getTanggalPesanan());
            ps.setString(3, pesanan.getStatusPesanan());
            ps.setInt(4, pesanan.getPesananId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    public boolean delete(int id) {

        String sql =
                "DELETE FROM pesanan WHERE pesanan_id = ?";

        try (Connection conn =
                     DatabaseConnection.getConnection();
             PreparedStatement ps =
                     conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
