/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.terra_snack.view;

import java.math.BigDecimal;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.mycompany.terra_snack.dao.DetailPesananDAO;
import com.mycompany.terra_snack.dao.PesananDAO;
import com.mycompany.terra_snack.dao.ProdukDAO;
import com.mycompany.terra_snack.dao.VarianProdukDAO;
import com.mycompany.terra_snack.model.ComboItem;
import com.mycompany.terra_snack.model.DetailPesanan;
import com.mycompany.terra_snack.model.Pesanan;
import com.mycompany.terra_snack.model.Produk;
import com.mycompany.terra_snack.model.VarianProduk;

/**
 *
 * @author LOQ
 */
public class DetailPesananForm extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger
            .getLogger(DetailPesananForm.class.getName());
    private DetailPesananDAO detailDAO = new DetailPesananDAO();
    private PesananDAO pesananDAO = new PesananDAO();
    private ProdukDAO produkDAO = new ProdukDAO();
    private VarianProdukDAO varianDAO = new VarianProdukDAO();

    private DefaultTableModel model;
    private int selectedId = -1;

    /**
     * Creates new form DetailPesananForm
     */
    public DetailPesananForm() {
        initComponents();
        model = (DefaultTableModel) JDetail.getModel();

        loadPesanan();
        loadProduk();
        loadVarian();

        JDetail.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                pilihData();
            }
        });

        // Auto-calculate subtotal when qty or harga changes
        JQty.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                hitungSubtotal();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                hitungSubtotal();
            }

            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                hitungSubtotal();
            }
        });

        JHargasatuan.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                hitungSubtotal();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                hitungSubtotal();
            }

            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                hitungSubtotal();
            }

        });

        JPesanan.addActionListener(e -> {

            ComboItem item = (ComboItem) JPesanan.getSelectedItem();

            if (item != null) {
                loadTable(item.getId());
            }
        });
    }

    private void loadPesanan() {

        JPesanan.removeAllItems();

        for (Pesanan p : pesananDAO.getAllPesanan()) {

            JPesanan.addItem(
                    new ComboItem(
                            p.getPesananId(),
                            "Pesanan #" + p.getPesananId()));
        }

        if (JPesanan.getItemCount() > 0) {

            ComboItem item = (ComboItem) JPesanan.getSelectedItem();

            loadTable(item.getId());
        }
    }

    private void loadProduk() {

        JProduk.removeAllItems();

        for (Produk p : produkDAO.getAllProduk()) {

            JProduk.addItem(
                    new ComboItem(
                            p.getProdukId(),
                            p.getNamaProduk()));
        }
    }

    private void loadVarian() {

        JParian.removeAllItems();

        for (VarianProduk v : varianDAO.getAll()) {

            JParian.addItem(
                    new ComboItem(
                            v.getVarianId(),
                            v.getNamaVarian()));
        }
    }

    private void loadTable(int pesananId) {

        model.setRowCount(0);

        int no = 1;

        for (DetailPesanan dp : detailDAO.getByPesananId(pesananId)) {

            model.addRow(new Object[] {
                    no++,
                    dp.getDetailId(),
                    dp.getPesananId(),
                    dp.getProdukId(),
                    dp.getVarianId(),
                    dp.getQty(),
                    dp.getHargaSatuan(),
                    dp.getSubtotal()
            });
        }
    }

    private void clearForm() {

        JQty.setText("");
        JHargasatuan.setText("");
        JSubtotal.setText("");
        JCatatan.setText("");
        selectedId = -1;
        JDetail.clearSelection();
    }

    private void hitungSubtotal() {
        try {
            if (!JQty.getText().isEmpty() && !JHargasatuan.getText().isEmpty()) {
                int qty = Integer.parseInt(JQty.getText());
                BigDecimal harga = new BigDecimal(JHargasatuan.getText());
                BigDecimal subtotal = harga.multiply(BigDecimal.valueOf(qty));
                JSubtotal.setText(subtotal.toString());
            }
        } catch (Exception e) {
            // Ignore parsing errors
        }
    }

    private void pilihData() {
        int row = JDetail.getSelectedRow();

        if (row == -1) {
            return;
        }

        try {
            selectedId = Integer.parseInt(model.getValueAt(row, 1).toString());

            // Load data from selected row
            JQty.setText(model.getValueAt(row, 5).toString());
            JHargasatuan.setText(model.getValueAt(row, 6).toString());
            JSubtotal.setText(model.getValueAt(row, 7).toString());

            // Get catatan from database
            DetailPesanan dp = detailDAO.getById(selectedId);
            if (dp != null && dp.getCatatan() != null) {
                JCatatan.setText((String) dp.getCatatan());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JPesanan = new javax.swing.JComboBox<>();
        JProduk = new javax.swing.JComboBox<>();
        JParian = new javax.swing.JComboBox<>();
        JQty = new javax.swing.JTextField();
        JHargasatuan = new javax.swing.JTextField();
        JSubtotal = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        JDetail = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        JCatatan = new javax.swing.JTextArea();
        JTambah = new javax.swing.JButton();
        JUpdate = new javax.swing.JButton();
        JHapus = new javax.swing.JButton();
        JRefresh = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnDashboard = new javax.swing.JButton();
        btnPesanan = new javax.swing.JButton();
        btnPorduk = new javax.swing.JButton();
        btnLaporan = new javax.swing.JButton();
        btnPengguna = new javax.swing.JButton();
        btnVerifikasi = new javax.swing.JButton();
        btnEvent = new javax.swing.JButton();
        btnTracking = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        JPesanan.setModel(new javax.swing.DefaultComboBoxModel<>());
        JProduk.setModel(new javax.swing.DefaultComboBoxModel<>());
        JParian.setModel(new javax.swing.DefaultComboBoxModel<>());
        JQty.setText("jTextField1");
        JQty.addActionListener(this::JQtyActionPerformed);

        JHargasatuan.setText("jTextField2");
        JHargasatuan.addActionListener(this::JHargasatuanActionPerformed);

        JSubtotal.setText("jTextField3");
        JSubtotal.addActionListener(this::JSubtotalActionPerformed);

        JDetail.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        JDetail.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {

                },
                new String[] {
                        "NO", "Detail ID", "Pesanan ID", "Produk ID", "Varian ID", "Qty", "Harga Satuan", "Catatan"
                }) {
            boolean[] canEdit = new boolean[] {
                    false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        jScrollPane1.setViewportView(JDetail);

        JCatatan.setColumns(20);
        JCatatan.setRows(5);
        JCatatan.setName(""); // NOI18N
        jScrollPane2.setViewportView(JCatatan);

        JTambah.setBackground(new java.awt.Color(102, 204, 0));
        JTambah.setForeground(new java.awt.Color(255, 255, 255));
        JTambah.setText("Tambah Detail");
        JTambah.addActionListener(this::JTambahActionPerformed);

        JUpdate.setBackground(new java.awt.Color(102, 204, 255));
        JUpdate.setForeground(new java.awt.Color(255, 255, 255));
        JUpdate.setText("Update Detail");
        JUpdate.addActionListener(this::JUpdateActionPerformed);

        JHapus.setBackground(new java.awt.Color(255, 51, 51));
        JHapus.setForeground(new java.awt.Color(255, 255, 255));
        JHapus.setText("Hapus Detail");
        JHapus.addActionListener(this::JHapusActionPerformed);

        JRefresh.setBackground(new java.awt.Color(204, 204, 204));
        JRefresh.setForeground(new java.awt.Color(255, 255, 255));
        JRefresh.setText("Refresh");
        JRefresh.addActionListener(this::JRefreshActionPerformed);

        jPanel1.setBackground(new java.awt.Color(11, 45, 107));

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(29, 29, 29)
                                                .addComponent(jLabel1))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(57, 57, 57)
                                                .addGroup(jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING,
                                                                false)
                                                        .addComponent(btnDashboard,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(btnPesanan, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(btnPorduk, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(btnTracking, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(btnEvent, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(btnVerifikasi,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(btnPengguna, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(btnLaporan, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                Short.MAX_VALUE))))
                                .addContainerGap(38, Short.MAX_VALUE)));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
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
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                false)
                                                        .addComponent(jScrollPane1)
                                                        .addComponent(jScrollPane2,
                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout
                                                                .createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.TRAILING,
                                                                        false)
                                                                        .addComponent(JParian,
                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                0, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                        .addComponent(JProduk,
                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                0, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                        .addComponent(JPesanan,
                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                0, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                        .addComponent(JQty,
                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                121, Short.MAX_VALUE))
                                                                .addGap(18, 18, 18)
                                                                .addComponent(JHargasatuan,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 241,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(56, 56, 56)
                                                                .addComponent(JSubtotal,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE, 267,
                                                                        Short.MAX_VALUE)))
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(38, 38, 38)
                                                .addComponent(JTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 126,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(JUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 125,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(JHapus, javax.swing.GroupLayout.DEFAULT_SIZE, 204,
                                                        Short.MAX_VALUE)
                                                .addGap(18, 18, 18)
                                                .addComponent(JRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 135,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(244, 244, 244)))));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(JPesanan, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(JProduk, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(JParian, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(JQty, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(JHargasatuan,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(JSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(22, 22, 22)
                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(24, 24, 24)
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 268,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(JUpdate)
                                        .addComponent(JHapus)
                                        .addComponent(JTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(JRefresh))
                                .addContainerGap(186, Short.MAX_VALUE)));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDashboardActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnDashboardActionPerformed
        // TODO add your handling code here:
        try {
            this.dispose(); // Tutup form Tracking saat ini untuk membebaskan memori
            new DashboardAdmin().setVisible(true);
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Gagal membuka Dashboard: " + e.getMessage());
        }
    }// GEN-LAST:event_btnDashboardActionPerformed

    private void btnPesananActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnPesananActionPerformed
        // TODO add your handling code here:
        try {
            this.dispose();
            new PesananForm().setVisible(true);
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Gagal membuka Pesanan: " + e.getMessage());
        }
    }// GEN-LAST:event_btnPesananActionPerformed

    private void btnPordukActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnPordukActionPerformed
        // TODO add your handling code here:
        try {
            this.dispose();
            new ProdukForm().setVisible(true);
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Gagal membuka Produk: " + e.getMessage());
        }
    }// GEN-LAST:event_btnPordukActionPerformed

    private void btnLaporanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnLaporanActionPerformed
        // TODO add your handling code here:
        try {
            this.dispose();
            new LaporanPenjualanFormFrame().setVisible(true);
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Gagal membuka Laporan: " + e.getMessage());
        }
    }// GEN-LAST:event_btnLaporanActionPerformed

    private void btnPenggunaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnPenggunaActionPerformed
        // TODO add your handling code here:
        try {
            this.dispose();
            new PelangganForm().setVisible(true);
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Gagal membuka Pengguna: " + e.getMessage());
        }
    }// GEN-LAST:event_btnPenggunaActionPerformed

    private void btnVerifikasiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnVerifikasiActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_btnVerifikasiActionPerformed

    private void btnEventActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnEventActionPerformed
        // TODO add your handling code here:
        try {
            this.dispose();
            new EventBazarFormFrame().setVisible(true);
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Gagal membuka Event: " + e.getMessage());
        }
    }// GEN-LAST:event_btnEventActionPerformed

    private void btnTrackingActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnTrackingActionPerformed
        // TODO add your handling code here:
        try {
            this.dispose();
            new TrackingPesananForm().setVisible(true);
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Gagal membuka Tracking: " + e.getMessage());
        }
    }// GEN-LAST:event_btnTrackingActionPerformed

    private void JPesananActionPerformed(java.awt.event.ActionEvent evt) {

        ComboItem pesanan = (ComboItem) JPesanan.getSelectedItem();

        if (pesanan != null) {

            System.out.println("Pesanan dipilih: " + pesanan.getId());

            loadTable(pesanan.getId());
        }
    }// GEN-LAST:event_JPesananActionPerformed

    private void JQtyActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_JQtyActionPerformed
        // TODO add your handling code here:
        hitungSubtotal();
    }// GEN-LAST:event_JQtyActionPerformed

    private void JHargasatuanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_JHargasatuanActionPerformed
        // TODO add your handling code here:
        hitungSubtotal();
    }// GEN-LAST:event_JHargasatuanActionPerformed

    private void JSubtotalActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_JSubtotalActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_JSubtotalActionPerformed

    private void JParianActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_JParianActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_JParianActionPerformed

    private void JProdukActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_JProdukActionPerformed
        // TODO add your handling code here:
        try {

            ComboItem produk = (ComboItem) JProduk.getSelectedItem();

            if (produk == null) {
                return;
            }

            JParian.removeAllItems();

            for (VarianProduk v : varianDAO.getByProdukId(produk.getId())) {

                JParian.addItem(
                        new ComboItem(
                                v.getVarianId(),
                                v.getNamaVarian()));
            }

        } catch (Exception e) {

        }
    }// GEN-LAST:event_JProdukActionPerformed

    private void JRefreshActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_JRefreshActionPerformed
        resetForm();
        JOptionPane.showMessageDialog(this, "Data berhasil di-refresh!");
    }// GEN-LAST:event_JRefreshActionPerformed

    private void JHapusActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_JHapusActionPerformed
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data yang akan dihapus dari tabel!");
            return;
        }

        int konfirmasi = JOptionPane.showConfirmDialog(
                this,
                "Apakah Anda yakin ingin menghapus data ini?",
                "Konfirmasi Hapus",
                JOptionPane.YES_NO_OPTION);

        if (konfirmasi == JOptionPane.YES_OPTION) {
            if (detailDAO.delete(selectedId)) {

                ComboItem pesanan = (ComboItem) JPesanan.getSelectedItem();

                loadTable(pesanan.getId());

                JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
                resetForm();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menghapus data!");
            }
        }
    }// GEN-LAST:event_JHapusActionPerformed

    private void JUpdateActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_JUpdateActionPerformed
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data yang akan diupdate dari tabel!");
            return;
        }

        try {
            if (JProduk.getSelectedItem() == null || JParian.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Produk dan Varian harus dipilih!");
                return;
            }

            if (JQty.getText().isEmpty() || JHargasatuan.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Qty dan Harga Satuan harus diisi!");
                return;
            }

            ComboItem produk = (ComboItem) JProduk.getSelectedItem();
            ComboItem varian = (ComboItem) JParian.getSelectedItem();

            int qty = Integer.parseInt(JQty.getText());
            BigDecimal harga = new BigDecimal(JHargasatuan.getText());
            BigDecimal subtotal = harga.multiply(BigDecimal.valueOf(qty));

            DetailPesanan dp = detailDAO.getById(selectedId);
            if (dp == null) {
                JOptionPane.showMessageDialog(this, "Data tidak ditemukan!");
                return;
            }

            dp.setProdukId(produk.getId());
            dp.setVarianId(varian.getId());
            dp.setQty(qty);
            dp.setHargaSatuan(harga);
            dp.setSubtotal(subtotal);
            dp.setCatatan(JCatatan.getText().isEmpty() ? null : JCatatan.getText());

            if (detailDAO.update(dp)) {

                ComboItem pesanan = (ComboItem) JPesanan.getSelectedItem();

                loadTable(pesanan.getId());

                JOptionPane.showMessageDialog(this, "Data berhasil diupdate!");
                resetForm();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal mengupdate data!");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Qty dan Harga harus berupa angka!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }// GEN-LAST:event_JUpdateActionPerformed

    private void JTambahActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_JTambahActionPerformed
        // TODO add your handling code here:
        try {
            // Validasi input
            if (JPesanan.getSelectedItem() == null || JProduk.getSelectedItem() == null
                    || JParian.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Semua field harus dipilih!");
                return;
            }

            if (JQty.getText().isEmpty() || JHargasatuan.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Qty dan Harga Satuan harus diisi!");
                return;
            }

            ComboItem pesanan = (ComboItem) JPesanan.getSelectedItem();
            ComboItem produk = (ComboItem) JProduk.getSelectedItem();
            ComboItem varian = (ComboItem) JParian.getSelectedItem();

            int qty = Integer.parseInt(JQty.getText());
            BigDecimal harga = new BigDecimal(JHargasatuan.getText());
            BigDecimal subtotal = harga.multiply(BigDecimal.valueOf(qty));

            DetailPesanan dp = new DetailPesanan();
            dp.setPesananId(pesanan.getId());
            dp.setProdukId(produk.getId());
            dp.setVarianId(varian.getId());
            dp.setQty(qty);
            dp.setHargaSatuan(harga);
            dp.setSubtotal(subtotal);
            dp.setCatatan(JCatatan.getText().isEmpty() ? null : JCatatan.getText());

            if (detailDAO.insert(dp)) {

                ComboItem p = (ComboItem) JPesanan.getSelectedItem();

                loadTable(p.getId());

                JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan!");
                resetForm();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menambahkan data!");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Qty dan Harga harus berupa angka!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }// GEN-LAST:event_JTambahActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
        // (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the default
         * look and feel.
         * For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
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
        // </editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new DetailPesananForm().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea JCatatan;
    private javax.swing.JTable JDetail;
    private javax.swing.JButton JHapus;
    private javax.swing.JTextField JHargasatuan;
    private javax.swing.JComboBox<ComboItem> JParian;
    private javax.swing.JComboBox<ComboItem> JPesanan;
    private javax.swing.JComboBox<ComboItem> JProduk;
    private javax.swing.JTextField JQty;
    private javax.swing.JButton JRefresh;
    private javax.swing.JTextField JSubtotal;
    private javax.swing.JButton JTambah;
    private javax.swing.JButton JUpdate;
    private javax.swing.JButton btnDashboard;
    private javax.swing.JButton btnEvent;
    private javax.swing.JButton btnLaporan;
    private javax.swing.JButton btnPengguna;
    private javax.swing.JButton btnPesanan;
    private javax.swing.JButton btnPorduk;
    private javax.swing.JButton btnTracking;
    private javax.swing.JButton btnVerifikasi;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables

    private void resetForm() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                       // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
