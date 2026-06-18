/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.terra_snack.services;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import com.mycompany.terra_snack.dao.PesananDAO;
import com.mycompany.terra_snack.model.Pesanan;

/**
 *
 * @author Tannn939
 */
public class OrderService {

    private final PesananDAO pesananDAO;

    public OrderService() {
        this.pesananDAO = new PesananDAO();
    }

    public boolean buatPesanan(int pelangganId) {

        if (pelangganId <= 0) {
            System.out.println("[OrderService] pelanggan_id tidak valid.");
            return false;
        }

        Pesanan pesanan = new Pesanan();
        pesanan.setPelangganId(pelangganId);
        pesanan.setTanggalPesanan(Timestamp.valueOf(LocalDateTime.now()));
        pesanan.setStatusPesanan("MENUNGGU_PEMBAYARAN");

        boolean hasil = pesananDAO.insert(pesanan);

        if (hasil) {
            System.out.println("[OrderService] Pesanan baru berhasil dibuat"
                    + " untuk pelanggan_id=" + pelangganId);
        } else {
            System.out.println("[OrderService] Gagal membuat pesanan.");
        }

        return hasil;
    }

    public List<Pesanan> getAllPesanan() {
        List<Pesanan> list = pesananDAO.getAllPesanan();
        System.out.println("[OrderService] Total pesanan ditemukan: " + list.size());
        return list;
    }

    public Pesanan getPesananById(int pesananId) {

        Pesanan pesanan = pesananDAO.getPesananById(pesananId);

        if (pesanan != null) {
            System.out.println("[OrderService] Pesanan ditemukan: id=" + pesananId);
        } else {
            System.out.println("[OrderService] Pesanan id=" + pesananId + " tidak ditemukan.");
        }

        return pesanan;
    }

    public boolean verifikasiPembayaran(int pesananId) {
        return ubahStatus(pesananId, "MENUNGGU_PEMBAYARAN", "DIPROSES");
    }

    public boolean tandaiSiap(int pesananId) {
        return ubahStatus(pesananId, "DIPROSES", "SIAP");
    }

    public boolean selesaikanPesanan(int pesananId) {
        return ubahStatus(pesananId, "SIAP", "SELESAI");
    }

    public boolean tolakPesanan(int pesananId) {

        Pesanan pesanan = pesananDAO.getPesananById(pesananId);

        if (pesanan == null) {
            System.out.println("[OrderService] Pesanan id=" + pesananId + " tidak ditemukan.");
            return false;
        }

        if (pesanan.getStatusPesanan().equals("SELESAI")) {
            System.out.println("[OrderService] Pesanan yang sudah SELESAI tidak bisa ditolak.");
            return false;
        }

        pesanan.setStatusPesanan("DITOLAK");
        boolean hasil = pesananDAO.update(pesanan);

        if (hasil) {
            System.out.println("[OrderService] Pesanan id=" + pesananId + " berhasil ditolak.");
        } else {
            System.out.println("[OrderService] Gagal menolak pesanan id=" + pesananId);
        }

        return hasil;
    }

    public boolean hapusPesanan(int pesananId) {

        Pesanan pesanan = pesananDAO.getPesananById(pesananId);

        if (pesanan == null) {
            System.out.println("[OrderService] Pesanan id=" + pesananId + " tidak ditemukan.");
            return false;
        }

        if (!pesanan.getStatusPesanan().equals("MENUNGGU_PEMBAYARAN")) {
            System.out.println("[OrderService] Pesanan hanya bisa dihapus"
                    + " saat status MENUNGGU_PEMBAYARAN."
                    + " Status saat ini: " + pesanan.getStatusPesanan());
            return false;
        }

        boolean hasil = pesananDAO.delete(pesananId);

        if (hasil) {
            System.out.println("[OrderService] Pesanan id=" + pesananId + " berhasil dihapus.");
        } else {
            System.out.println("[OrderService] Gagal menghapus pesanan id=" + pesananId);
        }

        return hasil;
    }

    private boolean ubahStatus(int pesananId,
                                String statusDiharapkan,
                                String statusBaru) {

        Pesanan pesanan = pesananDAO.getPesananById(pesananId);

        if (pesanan == null) {
            System.out.println("[OrderService] Pesanan id=" + pesananId + " tidak ditemukan.");
            return false;
        }

        if (!pesanan.getStatusPesanan().equals(statusDiharapkan)) {
            System.out.println("[OrderService] Gagal — status saat ini '"
                    + pesanan.getStatusPesanan()
                    + "', harus '" + statusDiharapkan + "' untuk pindah ke '" + statusBaru + "'.");
            return false;
        }

        pesanan.setStatusPesanan(statusBaru);
        boolean hasil = pesananDAO.update(pesanan);

        if (hasil) {
            System.out.println("[OrderService] Status pesanan id=" + pesananId
                    + " berhasil diubah: " + statusDiharapkan + " → " + statusBaru);
        } else {
            System.out.println("[OrderService] Gagal mengubah status pesanan id=" + pesananId);
        }

        return hasil;
    }
}
