package com.mycompany.terra_snack.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.mycompany.terra_snack.services.ReportService;

public class LaporanPenjualanForm extends JFrame {

    private final JTextArea area;
    private final ReportService service;

    public LaporanPenjualanForm() {
        setTitle("Laporan Penjualan");
        setSize(400, 400);
        setLayout(new BorderLayout());

        service = new ReportService();
        
        area = new JTextArea();

        loadData();

        add(new JScrollPane(area), BorderLayout.CENTER);
    }

    private void loadData() {
        StringBuilder sb = new StringBuilder();

        for (String data : service.getLaporanPenjualan()) {
            sb.append(data).append("\n");
        }

        sb.append("\nTotal Pendapatan: Rp")
          .append(service.getPendapatan());

        area.setText(sb.toString());
    }
}