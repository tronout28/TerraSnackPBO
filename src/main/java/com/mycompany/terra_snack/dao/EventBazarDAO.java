package com.mycompany.terra_snack.dao;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ADMIN
 */
import com.mycompany.terra_snack.database.DatabaseConnection;
import com.mycompany.terra_snack.model.EventBazar;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EventBazarDAO {

    public boolean insert(EventBazar event) {

        String sql = "INSERT INTO event_bazar (nama_event, lokasi, tanggal_event, status) VALUES (?, ?, ?, ?)";
 
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
 
            ps.setString(1, event.getNamaEvent());
            ps.setString(2, event.getLokasi());
            ps.setDate(3, new java.sql.Date(event.getTanggalMulai().getTime()));
            ps.setString(4, "PREORDER_BUKA");
 
            return ps.executeUpdate() > 0;
 
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<EventBazar> getAllEvent() {
 
        List<EventBazar> list = new ArrayList<>();
        String sql = "SELECT * FROM event_bazar ORDER BY event_id";
 
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
 
            while (rs.next()) {
                list.add(mapRow(rs));
            }
 
        } catch (SQLException e) {
            e.printStackTrace();
        }
 
        return list;
    }

    public EventBazar getEventById(int idEvent) {
 
        String sql = "SELECT * FROM event_bazar WHERE event_id = ?";
 
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
 
            ps.setInt(1, idEvent);
 
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
 
        } catch (SQLException e) {
            e.printStackTrace();
        }
 
        return null;
    }

    public boolean update(EventBazar event) {
 
        String sql = "UPDATE event_bazar SET nama_event = ?, lokasi = ?, tanggal_event = ? WHERE event_id = ?";
 
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
 
            ps.setString(1, event.getNamaEvent());
            ps.setString(2, event.getLokasi());
            ps.setDate(3, new java.sql.Date(event.getTanggalMulai().getTime()));
            ps.setInt(4, event.getIdEvent());
 
            return ps.executeUpdate() > 0;
 
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int idEvent) {
 
        String sql = "DELETE FROM event_bazar WHERE event_id = ?";
 
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
 
            ps.setInt(1, idEvent);
 
            return ps.executeUpdate() > 0;
 
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
 
    private EventBazar mapRow(ResultSet rs) throws SQLException {
        EventBazar event = new EventBazar();
        event.setIdEvent(rs.getInt("event_id"));
        event.setNamaEvent(rs.getString("nama_event"));
        event.setLokasi(rs.getString("lokasi"));
        event.setTanggalMulai(rs.getDate("tanggal_event"));
        return event;
    }
}