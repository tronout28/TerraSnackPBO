/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.terra_snack.model;

/**
 *
 * @author ftih_28
 */
public class User {
        private int userId;
    private String nama;
    private String username;
    private String password;
    private String role;
    private String noHp;

    public User() {
    }

    public User(int userId, String nama, String username,
                String password, String role, String noHp) {

        this.userId = userId;
        this.nama = nama;
        this.username = username;
        this.password = password;
        this.role = role;
        this.noHp = noHp;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }
}
