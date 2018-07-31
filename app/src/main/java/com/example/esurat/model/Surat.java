package com.example.esurat.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.example.esurat.utils.DateUtils;
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class Surat implements Serializable, Parcelable, SortedListAdapter.ViewModel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("rank")
    @Expose
    private Integer rank;
    @SerializedName("perihal")
    @Expose
    private String perihal;
    @SerializedName("dari")
    @Expose
    private String dari;
    @SerializedName("tanggalTerima")
    @Expose
    private String tanggalTerima;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("noAgenda")
    @Expose
    private String noAgenda;
    @SerializedName("noSurat")
    @Expose
    private String noSurat;
    @SerializedName("sifat")
    @Expose
    private String sifat;
    @SerializedName("tanggalSurat")
    @Expose
    private String tanggalSurat;
    @SerializedName("keterangan")
    @Expose
    private String keterangan;
    @SerializedName("linkLihatSurat")
    @Expose
    private String linkLihatSurat;
    public final static Parcelable.Creator<Surat> CREATOR = new Creator<Surat>() {

        @SuppressWarnings({
                "unchecked"
        })
        public Surat createFromParcel(Parcel in) {
            return new Surat(in);
        }

        public Surat[] newArray(int size) {
            return (new Surat[size]);
        }

    };

    private final static long serialVersionUID = 2711785585814504457L;

    protected Surat(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.rank = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.perihal = ((String) in.readValue((String.class.getClassLoader())));
        this.dari = ((String) in.readValue((String.class.getClassLoader())));
        this.tanggalTerima = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.noAgenda = ((String) in.readValue((String.class.getClassLoader())));
        this.noSurat = ((String) in.readValue((String.class.getClassLoader())));
        this.sifat = ((String) in.readValue((String.class.getClassLoader())));
        this.tanggalSurat = ((String) in.readValue((String.class.getClassLoader())));
        this.keterangan = ((String) in.readValue((String.class.getClassLoader())));
        this.linkLihatSurat = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public Surat() {
    }

    /**
     *
     * @param id
     * @param tanggalSurat
     * @param rank
     * @param noSurat
     * @param status
     * @param keterangan
     * @param perihal
     * @param dari
     * @param tanggalTerima
     * @param linkLihatSurat
     * @param sifat
     * @param noAgenda
     */
    public Surat(String id, Integer rank, String perihal, String dari, String tanggalTerima, String status, String noAgenda, String noSurat, String sifat, String tanggalSurat, String keterangan, String linkLihatSurat) {
        super();
        this.id = id;
        this.rank = rank;
        this.perihal = perihal;
        this.dari = dari;
        this.tanggalTerima = tanggalTerima;
        this.status = status;
        this.noAgenda = noAgenda;
        this.noSurat = noSurat;
        this.sifat = sifat;
        this.tanggalSurat = tanggalSurat;
        this.keterangan = keterangan;
        this.linkLihatSurat = linkLihatSurat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Surat withId(String id) {
        this.id = id;
        return this;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Surat withRank(Integer rank) {
        this.rank = rank;
        return this;
    }

    public String getPerihal() {
        return perihal;
    }

    public void setPerihal(String perihal) {
        this.perihal = perihal;
    }

    public Surat withPerihal(String perihal) {
        this.perihal = perihal;
        return this;
    }

    public String getDari() {
        return dari;
    }

    public void setDari(String dari) {
        this.dari = dari;
    }

    public Surat withDari(String dari) {
        this.dari = dari;
        return this;
    }

    public String getTanggalTerima() {
        return DateUtils.formatDate(Long.valueOf(tanggalTerima));
    }

    public void setTanggalTerima(String tanggalTerima) {
        this.tanggalTerima = tanggalTerima;
    }

    public Surat withTanggalTerima(String tanggalTerima) {
        this.tanggalTerima = tanggalTerima;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Surat withStatus(String status) {
        this.status = status;
        return this;
    }

    public String getNoAgenda() {
        return noAgenda;
    }

    public void setNoAgenda(String noAgenda) {
        this.noAgenda = noAgenda;
    }

    public Surat withNoAgenda(String noAgenda) {
        this.noAgenda = noAgenda;
        return this;
    }

    public String getNoSurat() {
        return noSurat;
    }

    public void setNoSurat(String noSurat) {
        this.noSurat = noSurat;
    }

    public Surat withNoSurat(String noSurat) {
        this.noSurat = noSurat;
        return this;
    }

    public String getSifat() {
        return sifat;
    }

    public void setSifat(String sifat) {
        this.sifat = sifat;
    }

    public Surat withSifat(String sifat) {
        this.sifat = sifat;
        return this;
    }

    public String getTanggalSurat() {
        return DateUtils.formatDate(Long.valueOf(tanggalSurat));
    }

    public void setTanggalSurat(String tanggalSurat) {
        this.tanggalSurat = tanggalSurat;
    }

    public Surat withTanggalSurat(String tanggalSurat) {
        this.tanggalSurat = tanggalSurat;
        return this;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public Surat withKeterangan(String keterangan) {
        this.keterangan = keterangan;
        return this;
    }

    public String getLinkLihatSurat() {
        return linkLihatSurat;
    }

    public void setLinkLihatSurat(String linkLihatSurat) {
        this.linkLihatSurat = linkLihatSurat;
    }

    public Surat withLinkLihatSurat(String linkLihatSurat) {
        this.linkLihatSurat = linkLihatSurat;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("rank", rank).append("perihal", perihal).append("dari", dari).append("tanggalTerima", tanggalTerima).append("status", status).append("noAgenda", noAgenda).append("noSurat", noSurat).append("sifat", sifat).append("tanggalSurat", tanggalSurat).append("keterangan", keterangan).append("linkLihatSurat", linkLihatSurat).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(noSurat).append(status).append(perihal).append(tanggalTerima).append(noAgenda).append(id).append(tanggalSurat).append(rank).append(keterangan).append(dari).append(linkLihatSurat).append(sifat).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Surat)) {
            return false;
        }
        Surat rhs = ((Surat) other);
        return new EqualsBuilder().append(noSurat, rhs.noSurat).append(status, rhs.status).append(perihal, rhs.perihal).append(tanggalTerima, rhs.tanggalTerima).append(noAgenda, rhs.noAgenda).append(id, rhs.id).append(tanggalSurat, rhs.tanggalSurat).append(rank, rhs.rank).append(keterangan, rhs.keterangan).append(dari, rhs.dari).append(linkLihatSurat, rhs.linkLihatSurat).append(sifat, rhs.sifat).isEquals();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(rank);
        dest.writeValue(perihal);
        dest.writeValue(dari);
        dest.writeValue(tanggalTerima);
        dest.writeValue(status);
        dest.writeValue(noAgenda);
        dest.writeValue(noSurat);
        dest.writeValue(sifat);
        dest.writeValue(tanggalSurat);
        dest.writeValue(keterangan);
        dest.writeValue(linkLihatSurat);
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public <T> boolean isSameModelAs(@NonNull T model) {
        return equals(model);
    }

    @Override
    public <T> boolean isContentTheSameAs(@NonNull T model) {
        return equals(model);
    }
}
