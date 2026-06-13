/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.terra_snack.services;

import com.mycompany.terra_snack.database.DatabaseConnection;
import com.mycompany.terra_snack.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 *
 * @author ftih_28
 */
public class AuthServices {

      public User login(String username,
                      String password) {

        String sql =
                "SELECT * FROM users "
                + "WHERE username=? "
                + "AND password=?";

        try (Connection conn =
                     DatabaseConnection.getConnection();
             PreparedStatement ps =
                     conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                User user = new User();

                user.setUserId(
                        rs.getInt("user_id"));

                user.setNama(
                        rs.getString("nama"));

                user.setUsername(
                        rs.getString("username"));

                user.setPassword(
                        rs.getString("password"));

                user.setRole(
                        rs.getString("role"));

                user.setNoHp(
                        rs.getString("no_hp"));

                return user;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    
}
