/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.terra_snack.view;

/**
 *
 * @author ASUS
 */
import com.mycompany.terra_snack.database.DatabaseConnection;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class TrackingPesananForm extends javax.swing.JFrame {

    private DefaultTableModel tableModel;
    private int selectedPesananId = -1;
    private String selectedKodePesanan = "";

    public TrackingPesananForm() {
        initComponents();
        setLocationRelativeTo(null);
        initTable();
        loadTabelPesanan();
        initProgressLabels();
    }

    // Inisialisasi tabel pesanan
    private void initTable() {
        String[] kolom = {"ID Pesanan", "Kode Pesanan", "Nama Pelanggan", "Tanggal", "Total", "Status"};
        tableModel = new DefaultTableModel(kolom, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Table read-only
            }
        };

        TPesanan.setModel(tableModel);
        TPesanan.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        TPesanan.setRowHeight(35);
        TPesanan.getTableHeader().setBackground(new Color(227, 242, 253));
        TPesanan.getTableHeader().setFont(new Font("Poppins", Font.BOLD, 12));
        TPesanan.setFont(new Font("Poppins", Font.PLAIN, 11));

        // Event listener untuk klik baris tabel
        TPesanan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
    }

    // Load semua pesanan ke tabel
    private void loadTabelPesanan() {
        tableModel.setRowCount(0);

        String sql = "SELECT ps.pesanan_id, ps.kode_pesanan, pl.nama, "
                + "ps.tanggal_pesanan, ps.total_harga, ps.status_pesanan "
                + "FROM pesanan ps "
                + "JOIN pelanggan pl ON ps.pelanggan_id = pl.pelanggan_id "
                + "ORDER BY ps.tanggal_pesanan DESC";

        try (Connection conn = DatabaseConnection.getConnection(); java.sql.Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Object[] row = {
                    rs.getInt("pesanan_id"),
                    rs.getString("kode_pesanan"),
                    rs.getString("nama"),
                    rs.getTimestamp("tanggal_pesanan"),
                    "Rp " + String.format("%,d", (int) rs.getDouble("total_harga")),
                    formatStatus(rs.getString("status_pesanan"))
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

    // Search/filter tabel
    private void searchTabel() {
        String keyword = txtCari.getText().trim();

        if (keyword.isEmpty()) {
            loadTabelPesanan();
            return;
        }

        tableModel.setRowCount(0);

        String sql = "SELECT ps.pesanan_id, ps.kode_pesanan, pl.nama, "
                + "ps.tanggal_pesanan, ps.total_harga, ps.status_pesanan "
                + "FROM pesanan ps "
                + "JOIN pelanggan pl ON ps.pelanggan_id = pl.pelanggan_id "
                + "WHERE ps.kode_pesanan LIKE ? OR pl.nama LIKE ? "
                + "ORDER BY ps.tanggal_pesanan DESC";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + keyword + "%");
            pstmt.setString(2, "%" + keyword + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Object[] row = {
                    rs.getInt("pesanan_id"),
                    rs.getString("kode_pesanan"),
                    rs.getString("nama"),
                    rs.getTimestamp("tanggal_pesanan"),
                    "Rp " + String.format("%,d", (int) rs.getDouble("total_harga")),
                    formatStatus(rs.getString("status_pesanan"))
                };
                tableModel.addRow(row);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error mencari data: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Event handler saat klik baris tabel
    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {
        int selectedRow = TPesanan.getSelectedRow();

        if (selectedRow == -1) {
            return;
        }

        // Ambil data dari baris yang dipilih
        selectedPesananId = (int) tableModel.getValueAt(selectedRow, 0);
        selectedKodePesanan = (String) tableModel.getValueAt(selectedRow, 1);

        // Load detail pesanan
        loadDetailPesanan(selectedKodePesanan);
    }

    // Load detail pesanan ke panel atas
    private void loadDetailPesanan(String kodePesanan) {
        // Clear semua label dulu
        lblOrderId.setText("Order ID: -");
        lblCustomer.setText("Customer: -");
        lblDate.setText("Date: -");
        lblMethod.setText("Method: -");
        lblTotal.setText("Total: -");
        lblNote.setText("Note: -");

        // Reset progress bar
        resetProgressIndicator();

        if (kodePesanan == null || kodePesanan.trim().isEmpty()) {
            return;
        }

        String sql = "SELECT ps.kode_pesanan, ps.pesanan_id, "
                + "pl.nama, pl.no_hp, "
                + "ps.tanggal_pesanan, ps.metode_fulfillment, "
                + "ps.total_harga, ps.catatan, ps.status_pesanan "
                + "FROM pesanan ps "
                + "JOIN pelanggan pl ON ps.pelanggan_id = pl.pelanggan_id "
                + "WHERE ps.kode_pesanan = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, kodePesanan);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String namaCustomer = rs.getString("nama");
                Timestamp tanggalPesanan = rs.getTimestamp("tanggal_pesanan");
                String metode = rs.getString("metode_fulfillment");
                double totalHarga = rs.getDouble("total_harga");
                String catatan = rs.getString("catatan");
                String status = rs.getString("status_pesanan");

                // Tampilkan ke JLabel
                lblOrderId.setText("Order ID: " + kodePesanan);
                lblCustomer.setText("Customer: " + namaCustomer);

                if (tanggalPesanan != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy, HH:mm");
                    lblDate.setText("Date: " + sdf.format(tanggalPesanan));
                }

                lblMethod.setText("Method: " + metode);
                lblTotal.setText("Total: Rp " + String.format("%,d", (int) totalHarga));
                lblNote.setText("Note: " + (catatan.isEmpty() ? "-" : catatan));

                // Update progress indicator
                updateProgressIndicator(status);

                // Set combo box ke status saat ini
                setComboBoxStatus(status);

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error load detail: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Format status untuk tabel
    private String formatStatus(String status) {
        switch (status) {
            case "MENUNGGU_PEMBAYARAN":
                return "Menunggu Pembayaran";
            case "MENUNGGU_VERIFIKASI":
                return "Menunggu Verifikasi";
            case "DIPROSES":
                return "Diproses";
            case "SIAP":
                return "Siap";
            case "SELESAI":
                return "Selesai";
            case "DITOLAK":
                return "Ditolak";
            default:
                return status;
        }
    }

    // Inisialisasi label progress bar
    private void initProgressLabels() {
        Font font = new Font("Poppins", Font.PLAIN, 12);
        lblPemPending.setFont(font);
        lblVerifikasi.setFont(font);
        lblProses.setFont(font);
        lblSelesai.setFont(font);

        resetProgressIndicator();
    }

    // Method untuk update progress indicator
    private void updateProgressIndicator(String status) {
        // Reset semua ke default
        lblPemPending.setText("○ Menunggu Pembayaran");
        lblPemPending.setForeground(new Color(189, 189, 189));

        lblVerifikasi.setText("○ Menunggu Verifikasi");
        lblVerifikasi.setForeground(new Color(189, 189, 189));

        lblProses.setText("○ Diproses");
        lblProses.setForeground(new Color(189, 189, 189));

        lblSelesai.setText("○ Selesai");
        lblSelesai.setForeground(new Color(189, 189, 189));

        Font fontBold = new Font("Poppins", Font.BOLD, 12);
        Font fontNormal = new Font("Poppins", Font.PLAIN, 12);

        switch (status) {
            case "MENUNGGU_PEMBAYARAN":
                lblPemPending.setText("● Menunggu Pembayaran");
                lblPemPending.setForeground(new Color(255, 152, 0));
                lblPemPending.setFont(fontBold);
                break;

            case "MENUNGGU_VERIFIKASI":
                lblPemPending.setText("✓ Menunggu Pembayaran");
                lblPemPending.setForeground(new Color(76, 175, 80));
                lblPemPending.setFont(fontNormal);

                lblVerifikasi.setText("● Menunggu Verifikasi");
                lblVerifikasi.setForeground(new Color(255, 152, 0));
                lblVerifikasi.setFont(fontBold);
                break;

            case "DIPROSES":
                lblPemPending.setText("✓ Menunggu Pembayaran");
                lblPemPending.setForeground(new Color(76, 175, 80));
                lblPemPending.setFont(fontNormal);

                lblVerifikasi.setText("✓ Menunggu Verifikasi");
                lblVerifikasi.setForeground(new Color(76, 175, 80));
                lblVerifikasi.setFont(fontNormal);

                lblProses.setText("● Diproses");
                lblProses.setForeground(new Color(33, 150, 243));
                lblProses.setFont(fontBold);
                break;

            case "SIAP":
                lblPemPending.setText("✓ Menunggu Pembayaran");
                lblPemPending.setForeground(new Color(76, 175, 80));
                lblPemPending.setFont(fontNormal);

                lblVerifikasi.setText("✓ Menunggu Verifikasi");
                lblVerifikasi.setForeground(new Color(76, 175, 80));
                lblVerifikasi.setFont(fontNormal);

                lblProses.setText("✓ Diproses");
                lblProses.setForeground(new Color(76, 175, 80));
                lblProses.setFont(fontNormal);

                lblSelesai.setText("● Selesai");
                lblSelesai.setForeground(new Color(33, 150, 243));
                lblSelesai.setFont(fontBold);
                break;

            case "SELESAI":
                lblPemPending.setText("✓ Menunggu Pembayaran");
                lblPemPending.setForeground(new Color(76, 175, 80));
                lblPemPending.setFont(fontNormal);

                lblVerifikasi.setText("✓ Menunggu Verifikasi");
                lblVerifikasi.setForeground(new Color(76, 175, 80));
                lblVerifikasi.setFont(fontNormal);

                lblProses.setText("✓ Diproses");
                lblProses.setForeground(new Color(76, 175, 80));
                lblProses.setFont(fontNormal);

                lblSelesai.setText("✓ Selesai");
                lblSelesai.setForeground(new Color(76, 175, 80));
                lblSelesai.setFont(fontBold);
                break;

            case "DITOLAK":
                lblPemPending.setText("✗ Menunggu Pembayaran");
                lblPemPending.setForeground(new Color(244, 67, 54));
                lblPemPending.setFont(fontBold);
                break;
        }
    }

    // Reset progress indicator
    private void resetProgressIndicator() {
        Font fontNormal = new Font("Poppins", Font.PLAIN, 12);

        lblPemPending.setText("○ Menunggu Pembayaran");
        lblPemPending.setForeground(new Color(189, 189, 189));
        lblPemPending.setFont(fontNormal);

        lblVerifikasi.setText("○ Menunggu Verifikasi");
        lblVerifikasi.setForeground(new Color(189, 189, 189));
        lblVerifikasi.setFont(fontNormal);

        lblProses.setText("○ Diproses");
        lblProses.setForeground(new Color(189, 189, 189));
        lblProses.setFont(fontNormal);

        lblSelesai.setText("○ Selesai");
        lblSelesai.setForeground(new Color(189, 189, 189));
        lblSelesai.setFont(fontNormal);
    }

    // Set combo box sesuai status database
    private void setComboBoxStatus(String statusDB) {
        switch (statusDB) {
            case "MENUNGGU_PEMBAYARAN":
                cbUpdate.setSelectedItem("Menunggu Pembayaran");
                break;
            case "MENUNGGU_VERIFIKASI":
                cbUpdate.setSelectedItem("Menunggu Verifikasi");
                break;
            case "DIPROSES":
                cbUpdate.setSelectedItem("Diproses");
                break;
            case "SIAP":
                cbUpdate.setSelectedItem("Siap");
                break;
            case "SELESAI":
                cbUpdate.setSelectedItem("Selesai");
                break;
        }
    }

    // Update status pesanan
    private void updateStatusPesanan() {
        if (selectedKodePesanan == null || selectedKodePesanan.equals("")) {
            JOptionPane.showMessageDialog(this,
                    "Pilih pesanan dari tabel terlebih dahulu!",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String statusBaruText = (String) cbUpdate.getSelectedItem();
        String statusDB = convertToDatabaseValue(statusBaruText);

        int konfirmasi = JOptionPane.showConfirmDialog(this,
                "Update status pesanan " + selectedKodePesanan + " menjadi " + statusBaruText + "?",
                "Konfirmasi", JOptionPane.YES_NO_OPTION);

        if (konfirmasi == JOptionPane.YES_OPTION) {
            try {
                String sql = "UPDATE pesanan SET status_pesanan = ? WHERE kode_pesanan = ?";
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, statusDB);
                pstmt.setString(2, selectedKodePesanan);

                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this,
                            "Status berhasil diupdate!",
                            "Sukses", JOptionPane.INFORMATION_MESSAGE);

                    // Refresh tabel dan detail
                    loadTabelPesanan();
                    loadDetailPesanan(selectedKodePesanan);
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Error update status: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    // Convert text combo box ke nilai database
    private String convertToDatabaseValue(String text) {
        switch (text) {
            case "Menunggu Pembayaran":
                return "MENUNGGU_PEMBAYARAN";
            case "Menunggu Verifikasi":
                return "MENUNGGU_VERIFIKASI";
            case "Diproses":
                return "DIPROSES";
            case "Siap":
                return "SIAP";
            case "Selesai":
                return "SELESAI";
            default:
                return text;
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

        jPanel1 = new javax.swing.JPanel();
        btnDashboard = new javax.swing.JButton();
        btnPesanan = new javax.swing.JButton();
        btnLaporan = new javax.swing.JButton();
        btnPengguna = new javax.swing.JButton();
        btnVerifikasi = new javax.swing.JButton();
        btnEvent = new javax.swing.JButton();
        btnTracking = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnProduk = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        lblOrderId = new javax.swing.JLabel();
        lblDate = new javax.swing.JLabel();
        lblCustomer = new javax.swing.JLabel();
        lblMethod = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        lblNote = new javax.swing.JLabel();
        lblPemPending = new javax.swing.JLabel();
        lblVerifikasi = new javax.swing.JLabel();
        lblProses = new javax.swing.JLabel();
        lblSelesai = new javax.swing.JLabel();
        btnSearch = new javax.swing.JButton();
        txtCari = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        btnUpdate = new javax.swing.JButton();
        cbUpdate = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        TPesanan = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(11, 45, 107));

        btnDashboard.setBackground(new java.awt.Color(11, 45, 107));
        btnDashboard.setForeground(new java.awt.Color(255, 255, 255));
        btnDashboard.setText("Dashboard");
        btnDashboard.addActionListener(this::btnDashboardActionPerformed);

        btnPesanan.setBackground(new java.awt.Color(11, 45, 107));
        btnPesanan.setForeground(new java.awt.Color(255, 255, 255));
        btnPesanan.setText("Pesanan");
        btnPesanan.addActionListener(this::btnPesananActionPerformed);

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

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Terra Snack");

        btnProduk.setBackground(new java.awt.Color(11, 45, 107));
        btnProduk.setForeground(new java.awt.Color(255, 255, 255));
        btnProduk.setText("Produk");
        btnProduk.addActionListener(this::btnProdukActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnTracking, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEvent, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnVerifikasi, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnLaporan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnPengguna, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnProduk, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnPesanan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnDashboard, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel1)))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(btnDashboard)
                .addGap(27, 27, 27)
                .addComponent(btnPesanan)
                .addGap(28, 28, 28)
                .addComponent(btnProduk)
                .addGap(29, 29, 29)
                .addComponent(btnLaporan)
                .addGap(27, 27, 27)
                .addComponent(btnPengguna)
                .addGap(29, 29, 29)
                .addComponent(btnVerifikasi)
                .addGap(29, 29, 29)
                .addComponent(btnEvent)
                .addGap(32, 32, 32)
                .addComponent(btnTracking)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblOrderId.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblOrderId.setText("Order ID:");

        lblDate.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblDate.setText("Date:");

        lblCustomer.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblCustomer.setText("Customer:");

        lblMethod.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblMethod.setText("Method:");

        lblTotal.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTotal.setText("Total:");

        lblNote.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblNote.setText("Note:");

        lblPemPending.setBackground(new java.awt.Color(255, 255, 255));
        lblPemPending.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPemPending.setText(" ●  Menunggu Pembayaran");
        lblPemPending.setOpaque(true);
        lblPemPending.setPreferredSize(new java.awt.Dimension(100, 100));

        lblVerifikasi.setBackground(new java.awt.Color(255, 255, 255));
        lblVerifikasi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblVerifikasi.setText("○ Menunggu Verifikasi");
        lblVerifikasi.setOpaque(true);

        lblProses.setBackground(new java.awt.Color(255, 255, 255));
        lblProses.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblProses.setText("○ Diproses");
        lblProses.setOpaque(true);
        lblProses.setPreferredSize(new java.awt.Dimension(100, 100));

        lblSelesai.setBackground(new java.awt.Color(255, 255, 255));
        lblSelesai.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSelesai.setText("○ Selesai");
        lblSelesai.setOpaque(true);
        lblSelesai.setPreferredSize(new java.awt.Dimension(100, 100));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblNote)
                            .addComponent(lblTotal))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblOrderId)
                            .addComponent(lblCustomer)
                            .addComponent(lblDate)
                            .addComponent(lblMethod))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblVerifikasi, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPemPending, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblProses, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblSelesai, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblOrderId)
                    .addComponent(lblPemPending, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCustomer)
                    .addComponent(lblVerifikasi))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDate)
                    .addComponent(lblProses, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMethod)
                    .addComponent(lblSelesai, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblNote)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        btnSearch.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSearch.setText("Search");
        btnSearch.addActionListener(this::btnSearchActionPerformed);

        txtCari.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("Update Status:");

        btnUpdate.setBackground(new java.awt.Color(25, 118, 210));
        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setText("Update Status");
        btnUpdate.addActionListener(this::btnUpdateActionPerformed);

        cbUpdate.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menunggu Pembayaran", "Menunggu Verifikasi", "Diproses", "Siap", "Selesai" }));
        cbUpdate.addActionListener(this::cbUpdateActionPerformed);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 94, Short.MAX_VALUE)
                .addComponent(cbUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnUpdate)
                .addGap(30, 30, 30))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(btnUpdate)
                    .addComponent(cbUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        TPesanan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Kode Pesanan", "Nama Pelanggan", "Tanggal", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(TPesanan);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(184, 184, 184)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 563, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 177, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(194, 194, 194))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(16, 16, 16))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        searchTabel();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void cbUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbUpdateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbUpdateActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        updateStatusPesanan();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDashboardActionPerformed
        // TODO add your handling code here:
        try {
            this.dispose(); // Tutup form Tracking saat ini untuk membebaskan memori
            new DashboardAdmin().setVisible(true);
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Gagal membuka Dashboard: " + e.getMessage());
        }
    }//GEN-LAST:event_btnDashboardActionPerformed

    private void btnVerifikasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerifikasiActionPerformed
        // TODO add your handling code here:
        try {
            this.dispose();
            new FormUploadPembayaran().setVisible(true);
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Gagal membuka Verifikasi: " + e.getMessage());
        }
    }//GEN-LAST:event_btnVerifikasiActionPerformed

    private void btnPesananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesananActionPerformed
        // TODO add your handling code here:
        try {
            this.dispose();
            new PesananForm().setVisible(true);
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Gagal membuka Pesanan: " + e.getMessage());
        }
    }//GEN-LAST:event_btnPesananActionPerformed

    private void btnProdukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProdukActionPerformed
        // TODO add your handling code here:
        try {
            this.dispose();
            new ProdukForm().setVisible(true);
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Gagal membuka Produk: " + e.getMessage());
        }
    }//GEN-LAST:event_btnProdukActionPerformed

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
    }//GEN-LAST:event_btnTrackingActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new TrackingPesananForm().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TPesanan;
    private javax.swing.JButton btnDashboard;
    private javax.swing.JButton btnEvent;
    private javax.swing.JButton btnLaporan;
    private javax.swing.JButton btnPengguna;
    private javax.swing.JButton btnPesanan;
    private javax.swing.JButton btnProduk;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnTracking;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnVerifikasi;
    private javax.swing.JComboBox<String> cbUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCustomer;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblMethod;
    private javax.swing.JLabel lblNote;
    private javax.swing.JLabel lblOrderId;
    private javax.swing.JLabel lblPemPending;
    private javax.swing.JLabel lblProses;
    private javax.swing.JLabel lblSelesai;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblVerifikasi;
    private javax.swing.JTextField txtCari;
    // End of variables declaration//GEN-END:variables
}
