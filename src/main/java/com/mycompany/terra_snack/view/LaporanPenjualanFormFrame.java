/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.terra_snack.view;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.mycompany.terra_snack.dao.LaporanDAO;

/**
 *
 * @author ADMIN
 */
public class LaporanPenjualanFormFrame extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger
            .getLogger(LaporanPenjualanFormFrame.class.getName());

    public LaporanPenjualanFormFrame() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        btnDashboard = new javax.swing.JButton();
        btnPesanan = new javax.swing.JButton();
        btnPorduk = new javax.swing.JButton();
        btnLaporan = new javax.swing.JButton();
        btnPengguna = new javax.swing.JButton();
        btnVerifikasi = new javax.swing.JButton();
        btnEvent = new javax.swing.JButton();
        btnTracking = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        tfSampaiTanggal = new javax.swing.JTextField();
        tfMulaiTanggal = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblLaporanPenjualan = new javax.swing.JTable();
        btnGenerateLaporan = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(11, 45, 107));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Terra Snack");

        btnDashboard.setBackground(new java.awt.Color(11, 45, 107));
        btnDashboard.setForeground(new java.awt.Color(255, 255, 255));
        btnDashboard.setText("Dahboard");
        btnDashboard.addActionListener(this::btnDashboardActionPerformed);

        btnPesanan.setBackground(new java.awt.Color(11, 45, 107));
        btnPesanan.setForeground(new java.awt.Color(255, 255, 255));
        btnPesanan.setText("Pesanan");
        btnPesanan.addActionListener(this::btnPesananActionPerformed);

        btnPorduk.setBackground(new java.awt.Color(11, 45, 107));
        btnPorduk.setForeground(new java.awt.Color(255, 255, 255));
        btnPorduk.setText("Produk");
        btnPorduk.addActionListener(this::btnPordukActionPerformed);

        btnLaporan.setBackground(new java.awt.Color(11, 45, 107));
        btnLaporan.setForeground(new java.awt.Color(255, 255, 255));
        btnLaporan.setText("Laporan");
        btnLaporan.addActionListener(this::btnLaporanActionPerformed);

        btnPengguna.setBackground(new java.awt.Color(11, 45, 107));
        btnPengguna.setForeground(new java.awt.Color(255, 255, 255));
        btnPengguna.setText("Pengguna");
        btnPengguna.addActionListener(this::btnPenggunaActionPerformed);

        btnVerifikasi.setBackground(new java.awt.Color(11, 45, 107));
        btnVerifikasi.setForeground(new java.awt.Color(255, 255, 255));
        btnVerifikasi.setText("Verifikasi");
        btnVerifikasi.addActionListener(this::btnVerifikasiActionPerformed);

        btnEvent.setBackground(new java.awt.Color(11, 45, 107));
        btnEvent.setForeground(new java.awt.Color(255, 255, 255));
        btnEvent.setText("Event");
        btnEvent.addActionListener(this::btnEventActionPerformed);

        btnTracking.setBackground(new java.awt.Color(11, 45, 107));
        btnTracking.setForeground(new java.awt.Color(255, 255, 255));
        btnTracking.setText("Tracking");
        btnTracking.addActionListener(this::btnTrackingActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jLabel6))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnDashboard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnPesanan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnPorduk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnTracking, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEvent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnVerifikasi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnPengguna, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnLaporan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel6)
                .addGap(31, 31, 31)
                .addComponent(btnDashboard)
                .addGap(29, 29, 29)
                .addComponent(btnPesanan)
                .addGap(27, 27, 27)
                .addComponent(btnPorduk)
                .addGap(28, 28, 28)
                .addComponent(btnLaporan)
                .addGap(27, 27, 27)
                .addComponent(btnPengguna)
                .addGap(27, 27, 27)
                .addComponent(btnVerifikasi)
                .addGap(28, 28, 28)
                .addComponent(btnEvent)
                .addGap(26, 26, 26)
                .addComponent(btnTracking)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("LAPORAN PENJUALAN");
        jLabel1.setAlignmentY(0.0F);

        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Mulai Tanggal:");

        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Sampai Tanggal:");

        tblLaporanPenjualan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Nama Product", "Jumlah Penjualan", "Harga Satuan", "Total"
            }
        ));
        jScrollPane1.setViewportView(tblLaporanPenjualan);

        btnGenerateLaporan.setBackground(new java.awt.Color(51, 153, 0));
        btnGenerateLaporan.setForeground(new java.awt.Color(0, 0, 0));
        btnGenerateLaporan.setText("Generate Laporan");
        btnGenerateLaporan.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnGenerateLaporan.setMaximumSize(new java.awt.Dimension(47, 20));
        btnGenerateLaporan.setMinimumSize(new java.awt.Dimension(47, 20));
        btnGenerateLaporan.addActionListener(this::btnGenerateLaporanActionPerformed);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(230, 230, 230)
                        .addComponent(jLabel1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 619, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(tfMulaiTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(101, 101, 101)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tfSampaiTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3)))
                            .addComponent(btnGenerateLaporan, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(158, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel1)
                .addGap(38, 38, 38)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfMulaiTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfSampaiTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnGenerateLaporan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(169, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDashboardActionPerformed
        // TODO add your handling code here:
        try {
            this.dispose(); // Tutup form Tracking saat ini untuk membebaskan memori
            new DashboardAdmin().setVisible(true);
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Gagal membuka Dashboard: " + e.getMessage());
        }
    }//GEN-LAST:event_btnDashboardActionPerformed

    private void btnPesananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesananActionPerformed
        // TODO add your handling code here:
        try {
            this.dispose();
            new PesananForm().setVisible(true);
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Gagal membuka Pesanan: " + e.getMessage());
        }
    }//GEN-LAST:event_btnPesananActionPerformed

    private void btnPordukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPordukActionPerformed
        // TODO add your handling code here:
        try {
            this.dispose();
            new ProdukForm().setVisible(true);
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Gagal membuka Produk: " + e.getMessage());
        }
    }//GEN-LAST:event_btnPordukActionPerformed

    private void btnLaporanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLaporanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLaporanActionPerformed

    private void btnPenggunaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPenggunaActionPerformed
        // TODO add your handling code here:
        try {
            this.dispose();
            new PelangganForm().setVisible(true);
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Gagal membuka Pengguna: " + e.getMessage());
        }
    }//GEN-LAST:event_btnPenggunaActionPerformed

    private void btnVerifikasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerifikasiActionPerformed
        // TODO add your handling code here:
        try {
            this.dispose();
            new FormUploadPembayaran().setVisible(true);
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Gagal membuka Event: " + e.getMessage());
        }
    }//GEN-LAST:event_btnVerifikasiActionPerformed

    private void btnEventActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEventActionPerformed
        // TODO add your handling code here:
        try {
            this.dispose();
            new EventBazarFormFrame().setVisible(true);
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Gagal membuka Event: " + e.getMessage());
        }
    }//GEN-LAST:event_btnEventActionPerformed

    private void btnTrackingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTrackingActionPerformed
        // TODO add your handling code here:
        try {
            this.dispose();
            new TrackingPesananForm().setVisible(true);
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Gagal membuka Tracking: " + e.getMessage());
        }
    }//GEN-LAST:event_btnTrackingActionPerformed

    private void btnGenerateLaporanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnGenerateLaporanActionPerformed
        try {
            String mulaiTanggal = tfMulaiTanggal.getText().trim();
            String sampaiTanggal = tfSampaiTanggal.getText().trim();

            if (mulaiTanggal.isEmpty() || sampaiTanggal.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Isi tanggal terlebih dahulu!\nFormat: yyyy-MM-dd");
                return;
            }

            LaporanDAO dao = new LaporanDAO();
            List<Object[]> laporan = dao.getLaporanPenjualanByTanggal(mulaiTanggal, sampaiTanggal);

            DefaultTableModel model = (DefaultTableModel) tblLaporanPenjualan.getModel();
            model.setRowCount(0);

            double grandTotal = 0;
            for (Object[] row : laporan) {
                model.addRow(row);
                // Asumsi row[3] tidak null. Lihat catatan kritis di bawah.
                grandTotal += ((Number) row[3]).doubleValue(); 
            }

            JOptionPane.showMessageDialog(this, "Total Penjualan : Rp " + String.format("%,.0f", grandTotal));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error : " + e.getMessage());
        }
    }// GEN-LAST:event_btnGenerateLaporanActionPerformed

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> new LaporanPenjualanFormFrame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDashboard;
    private javax.swing.JButton btnEvent;
    private javax.swing.JToggleButton btnGenerateLaporan;
    private javax.swing.JButton btnLaporan;
    private javax.swing.JButton btnPengguna;
    private javax.swing.JButton btnPesanan;
    private javax.swing.JButton btnPorduk;
    private javax.swing.JButton btnTracking;
    private javax.swing.JButton btnVerifikasi;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblLaporanPenjualan;
    private javax.swing.JTextField tfMulaiTanggal;
    private javax.swing.JTextField tfSampaiTanggal;
    // End of variables declaration//GEN-END:variables
}