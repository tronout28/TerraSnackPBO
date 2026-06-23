/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.terra_snack.view;

/**
 *
 * @author ASUS
 */
import com.mycompany.terra_snack.dao.PembayaranDAO;
import com.mycompany.terra_snack.database.DatabaseConnection;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FormUploadPembayaran extends javax.swing.JFrame {

    private PembayaranDAO pembayaranDAO;
    private DefaultTableModel tableModel;
    private int selectedPembayaranId = -1;
    private String selectedKodePesanan = "";

    public FormUploadPembayaran() {
        pembayaranDAO = new PembayaranDAO();
        initComponents();
        setLocationRelativeTo(null);
        initTable();
        loadPembayaran();
    }

    // Inisialisasi tabel
    private void initTable() {
        String[] kolom = {"ID Pembayaran", "ID Pesanan", "Nama Pelanggan", "Jumlah", "Status", "Tanggal"};
        tableModel = new DefaultTableModel(kolom, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Table read-only
            }
        };

        tPembayaran.setModel(tableModel);
        tPembayaran.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tPembayaran.setRowHeight(35);
        tPembayaran.getTableHeader().setBackground(new Color(227, 242, 253));
        tPembayaran.getTableHeader().setFont(new java.awt.Font("Poppins", java.awt.Font.BOLD, 12));
        tPembayaran.setFont(new java.awt.Font("Poppins", java.awt.Font.PLAIN, 11));
    }

    private void loadPembayaran() {
        tableModel.setRowCount(0);

        // SQL Query JOIN 3 tabel: pembayaran, pesanan, pelanggan
        String sql = "SELECT p.pembayaran_id, ps.kode_pesanan, pl.nama, "
                + "ps.total_harga, p.status_verifikasi, p.tanggal_upload "
                + "FROM pembayaran p "
                + "JOIN pesanan ps ON p.pesanan_id = ps.pesanan_id "
                + "JOIN pelanggan pl ON ps.pelanggan_id = pl.pelanggan_id "
                + "WHERE p.status_verifikasi = 'MENUNGGU' "
                + "ORDER BY p.tanggal_upload DESC";

        try (Connection conn = DatabaseConnection.getConnection(); java.sql.Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Object[] row = {
                    rs.getInt("pembayaran_id"),
                    rs.getString("kode_pesanan"),
                    rs.getString("nama"),
                    "Rp " + String.format("%,d", (int) rs.getDouble("total_harga")),
                    rs.getString("status_verifikasi"),
                    rs.getTimestamp("tanggal_upload")
                };
                tableModel.addRow(row);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error load data: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Method untuk cari pembayaran
    private void cariPembayaran() {
        String keyword = tfPencarian.getText().trim();

        if (keyword.isEmpty()) {
            loadPembayaran();
            return;
        }

        tableModel.setRowCount(0);

        String sql = "SELECT p.pembayaran_id, ps.kode_pesanan, pl.nama, "
                + "ps.total_harga, p.status_verifikasi, p.tanggal_upload "
                + "FROM pembayaran p "
                + "JOIN pesanan ps ON p.pesanan_id = ps.pesanan_id "
                + "JOIN pelanggan pl ON ps.pelanggan_id = pl.pelanggan_id "
                + "WHERE (ps.kode_pesanan LIKE ? OR pl.nama LIKE ?) "
                + "AND p.status_verifikasi = 'MENUNGGU' "
                + "ORDER BY p.tanggal_upload DESC";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + keyword + "%");
            pstmt.setString(2, "%" + keyword + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Object[] row = {
                    rs.getInt("pembayaran_id"),
                    rs.getString("kode_pesanan"),
                    rs.getString("nama"),
                    "Rp " + String.format("%,d", (int) rs.getDouble("total_harga")),
                    rs.getString("status_verifikasi"),
                    rs.getTimestamp("tanggal_upload")
                };
                tableModel.addRow(row);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error mencari data: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method untuk lihat bukti QRIS
    private void lihatBuktiQRIS() {
        // 1. Validasi: Pastikan ada baris yang dipilih di tabel
        int selectedRow = tPembayaran.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Pilih data pembayaran di tabel terlebih dahulu!",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 2. Ambil ID Pembayaran dari kolom pertama (index 0) tabel
        int pembayaranId = (int) tableModel.getValueAt(selectedRow, 0);

        try {
            // 3. Query database untuk mengambil nama file bukti_qris
            String sql = "SELECT bukti_qris FROM pembayaran WHERE pembayaran_id = ?";
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, pembayaranId);
            ResultSet rs = pstmt.executeQuery();

            // 4. Tampilkan pesan berdasarkan hasil query
            if (rs.next()) {
                String namaFile = rs.getString("bukti_qris");

                if (namaFile != null && !namaFile.trim().isEmpty()) {
                    // Jika nama file ada di database
                    JOptionPane.showMessageDialog(this,
                            "Nama File Bukti Pembayaran:\n\n" + namaFile,
                            "Informasi Bukti QRIS",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // Jika field bukti_qris NULL atau kosong
                    JOptionPane.showMessageDialog(this,
                            "Pelanggan belum mengupload bukti pembayaran.",
                            "Bukti Tidak Tersedia",
                            JOptionPane.WARNING_MESSAGE);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Gagal mengambil data bukti: " + e.getMessage(),
                    "Error Database", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Method untuk verifikasi pembayaran (Setujui/Tolak)
    private void verifikasiPembayaran(boolean disetujui) {
        int selectedRow = tPembayaran.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Pilih pembayaran yang akan diverifikasi!",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        selectedPembayaranId = (int) tableModel.getValueAt(selectedRow, 0);
        selectedKodePesanan = (String) tableModel.getValueAt(selectedRow, 1);

        // Konfirmasi
        String pesan = disetujui
                ? "Setujui pembayaran untuk pesanan " + selectedKodePesanan + "?"
                : "Tolak pembayaran untuk pesanan " + selectedKodePesanan + "?";

        int konfirmasi = JOptionPane.showConfirmDialog(this,
                pesan, "Konfirmasi", JOptionPane.YES_NO_OPTION);

        if (konfirmasi == JOptionPane.YES_OPTION) {
            try {
                String statusBaru = disetujui ? "VALID" : "DITOLAK";
                String catatan = disetujui ? "Pembayaran sesuai" : "Bukti tidak valid";

                // Update status pembayaran via DAO
                pembayaranDAO.updateStatus(selectedPembayaranId, statusBaru, catatan);

                // Jika disetujui, update status pesanan jadi DIPROSES
                if (disetujui) {
                    updateStatusPesanan(selectedKodePesanan, "DIPROSES");
                }

                JOptionPane.showMessageDialog(this,
                        "Pembayaran berhasil " + (disetujui ? "disetujui" : "ditolak"));

                // Refresh tabel
                loadPembayaran();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Error update status: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Helper method untuk update status pesanan
    private void updateStatusPesanan(String kodePesanan, String statusBaru) {
        String sql = "UPDATE pesanan SET status_pesanan = ? WHERE kode_pesanan = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, statusBaru);
            pstmt.setString(2, kodePesanan);
            pstmt.executeUpdate();

        } catch (Exception e) {
            System.err.println("Error update status pesanan: " + e.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sideBarP = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnDashboard = new javax.swing.JButton();
        btnPesanan = new javax.swing.JButton();
        btnPorduk = new javax.swing.JButton();
        btnLaporan = new javax.swing.JButton();
        btnPengguna = new javax.swing.JButton();
        btnVerifikasi = new javax.swing.JButton();
        btnEvent = new javax.swing.JButton();
        btnTracking = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tPembayaran = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        tfPencarian = new javax.swing.JTextField();
        bCari = new javax.swing.JButton();
        bTolak = new javax.swing.JButton();
        bSetuju = new javax.swing.JButton();
        bQris = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(81, 133, 211));

        sideBarP.setBackground(new java.awt.Color(11, 45, 107));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Terra Snack");

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

        javax.swing.GroupLayout sideBarPLayout = new javax.swing.GroupLayout(sideBarP);
        sideBarP.setLayout(sideBarPLayout);
        sideBarPLayout.setHorizontalGroup(
            sideBarPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sideBarPLayout.createSequentialGroup()
                .addGroup(sideBarPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(sideBarPLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jLabel1))
                    .addGroup(sideBarPLayout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addGroup(sideBarPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnDashboard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnPesanan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnPorduk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(sideBarPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(btnTracking, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnEvent, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnVerifikasi, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnPengguna, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnLaporan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        sideBarPLayout.setVerticalGroup(
            sideBarPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sideBarPLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel1)
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

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tPembayaran.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Pembayaran", "ID Pesanan", "Nama Pelanggan", "Status", "Tanggal"
            }
        ));
        jScrollPane1.setViewportView(tPembayaran);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 598, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Cari Pembayaran");
        jLabel2.setToolTipText("");

        tfPencarian.addActionListener(this::tfPencarianActionPerformed);

        bCari.setBackground(new java.awt.Color(33, 150, 243));
        bCari.setText("Cari");
        bCari.addActionListener(this::bCariActionPerformed);

        bTolak.setBackground(new java.awt.Color(244, 67, 54));
        bTolak.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        bTolak.setForeground(new java.awt.Color(255, 255, 255));
        bTolak.setText("Tolak");
        bTolak.addActionListener(this::bTolakActionPerformed);

        bSetuju.setBackground(new java.awt.Color(76, 175, 80));
        bSetuju.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        bSetuju.setForeground(new java.awt.Color(255, 255, 255));
        bSetuju.setText("Setujui");
        bSetuju.addActionListener(this::bSetujuActionPerformed);

        bQris.setBackground(new java.awt.Color(25, 118, 210));
        bQris.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        bQris.setForeground(new java.awt.Color(255, 255, 255));
        bQris.setText("Lihat Bukti Qris");
        bQris.addActionListener(this::bQrisActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(sideBarP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bQris)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bSetuju, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bTolak, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(tfPencarian, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(bCari)))))
                .addContainerGap(78, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bCari)
                    .addComponent(tfPencarian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bTolak)
                    .addComponent(bSetuju)
                    .addComponent(bQris))
                .addGap(129, 129, 129))
            .addComponent(sideBarP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tfPencarianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfPencarianActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfPencarianActionPerformed

    private void bCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCariActionPerformed
        // TODO add your handling code here:
        cariPembayaran();
    }//GEN-LAST:event_bCariActionPerformed

    private void bTolakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bTolakActionPerformed
        // TODO add your handling code here:
        verifikasiPembayaran(false);
    }//GEN-LAST:event_bTolakActionPerformed

    private void bSetujuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSetujuActionPerformed
        // TODO add your handling code here:
        verifikasiPembayaran(true);
    }//GEN-LAST:event_bSetujuActionPerformed

    private void bQrisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bQrisActionPerformed
        // TODO add your handling code here:
        lihatBuktiQRIS();
    }//GEN-LAST:event_bQrisActionPerformed

    private void btnTrackingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTrackingActionPerformed
        // TODO add your handling code here:
        try {
            this.dispose();
            new TrackingPesananForm().setVisible(true);
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Gagal membuka Tracking: " + e.getMessage());
        }
    }//GEN-LAST:event_btnTrackingActionPerformed

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
        try {
            this.dispose();
            new LaporanPenjualanFormFrame().setVisible(true);
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Gagal membuka Laporan: " + e.getMessage());
        }
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

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {

        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new FormUploadPembayaran().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bCari;
    private javax.swing.JButton bQris;
    private javax.swing.JButton bSetuju;
    private javax.swing.JButton bTolak;
    private javax.swing.JButton btnDashboard;
    private javax.swing.JButton btnEvent;
    private javax.swing.JButton btnLaporan;
    private javax.swing.JButton btnPengguna;
    private javax.swing.JButton btnPesanan;
    private javax.swing.JButton btnPorduk;
    private javax.swing.JButton btnTracking;
    private javax.swing.JButton btnVerifikasi;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel sideBarP;
    private javax.swing.JTable tPembayaran;
    private javax.swing.JTextField tfPencarian;
    // End of variables declaration//GEN-END:variables
}
