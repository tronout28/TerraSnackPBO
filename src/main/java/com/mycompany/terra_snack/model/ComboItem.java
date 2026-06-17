package com.mycompany.terra_snack.model;

public class ComboItem {
       private int id;
    private String nama;

    public ComboItem(int id, String nama) {
        this.id = id;
        this.nama = nama;
    }

    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    @Override
    public String toString() {
        return nama;
    }
}
