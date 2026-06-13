package com.mycompany.terra_snack.services;

import java.util.List;
import java.util.Map;

import com.mycompany.terra_snack.dao.LaporanDAO;

public class ReportService {

    private final LaporanDAO dao = new LaporanDAO();

    public List<String> getLaporanPenjualan() {
        return dao.getLaporanPenjualan();
    }

    public double getPendapatan() {
        return dao.getTotalPendapatan();
    }

    public Map<String, Integer> getProdukTerlaris() {
        return dao.getProdukTerlaris();
    }
}