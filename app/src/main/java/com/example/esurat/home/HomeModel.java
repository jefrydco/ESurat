package com.example.esurat.home;

public class HomeModel {
    private String perihal;
    private String noAgenda;
    private String noSurat;
    private String tanggalSurat;
    private String tanggalTerima;
    private String dari;
    private String keterangan;
    private String status;

    public HomeModel(String perihal, String noAgenda, String noSurat, String tanggalSurat, String tanggalTerima, String dari, String keterangan, String status) {
        this.perihal = perihal;
        this.noAgenda = noAgenda;
        this.noSurat = noSurat;
        this.tanggalSurat = tanggalSurat;
        this.tanggalTerima = tanggalTerima;
        this.dari = dari;
        this.keterangan = keterangan;
        this.status = status;
    }

    public String getPerihal() {
        return perihal;
    }

    public void setPerihal(String perihal) {
        this.perihal = perihal;
    }

    public String getNoAgenda() {
        return noAgenda;
    }

    public void setNoAgenda(String noAgenda) {
        this.noAgenda = noAgenda;
    }

    public String getNoSurat() {
        return noSurat;
    }

    public void setNoSurat(String noSurat) {
        this.noSurat = noSurat;
    }

    public String getTanggalSurat() {
        return tanggalSurat;
    }

    public void setTanggalSurat(String tanggalSurat) {
        this.tanggalSurat = tanggalSurat;
    }

    public String getTanggalTerima() {
        return tanggalTerima;
    }

    public void setTanggalTerima(String tanggalTerima) {
        this.tanggalTerima = tanggalTerima;
    }

    public String getDari() {
        return dari;
    }

    public void setDari(String dari) {
        this.dari = dari;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
