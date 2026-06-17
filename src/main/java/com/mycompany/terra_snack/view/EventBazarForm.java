package com.mycompany.terra_snack.view;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class EventBazarForm extends JFrame {

    private JTextField namaEvent;
    private JTextField lokasi;

    public EventBazarForm() {
        setTitle("Event Bazar");
        setSize(300, 200);
        setLayout(new GridLayout(3, 2));

        namaEvent = new JTextField();
        lokasi = new JTextField();

        add(new JLabel("Nama Event"));
        add(namaEvent);

        add(new JLabel("Lokasi"));
        add(lokasi);

        JButton btnSimpan = new JButton("Simpan");
        add(new JLabel()); // spacer
        add(btnSimpan);

        btnSimpan.addActionListener(e -> {
            String nama = namaEvent.getText();
            String loc = lokasi.getText();

            JOptionPane.showMessageDialog(
                this,
                "Event Berhasil Disimpan:\n" + nama + " - " + loc
            );
        });
    }
}
