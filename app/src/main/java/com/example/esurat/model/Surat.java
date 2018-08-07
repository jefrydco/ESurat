package com.example.esurat.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class Surat implements Serializable, Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("no")
    @Expose
    private Long no;
    @SerializedName("agenda")
    @Expose
    private String agenda;
    @SerializedName("noSurat")
    @Expose
    private String noSurat;
    @SerializedName("sifat")
    @Expose
    private String sifat;
    @SerializedName("tglSurat")
    @Expose
    private String tglSurat;
    @SerializedName("tglTerima")
    @Expose
    private String tglTerima;
    @SerializedName("dari")
    @Expose
    private String dari;
    @SerializedName("perihal")
    @Expose
    private String perihal;
    @SerializedName("ket")
    @Expose
    private String ket;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("warna")
    @Expose
    private String warna;
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

    }
            ;
    private final static long serialVersionUID = 6307980663519187048L;

    protected Surat(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.no = ((Long) in.readValue((Long.class.getClassLoader())));
        this.agenda = ((String) in.readValue((String.class.getClassLoader())));
        this.noSurat = ((String) in.readValue((String.class.getClassLoader())));
        this.sifat = ((String) in.readValue((String.class.getClassLoader())));
        this.tglSurat = ((String) in.readValue((String.class.getClassLoader())));
        this.tglTerima = ((String) in.readValue((String.class.getClassLoader())));
        this.dari = ((String) in.readValue((String.class.getClassLoader())));
        this.perihal = ((String) in.readValue((String.class.getClassLoader())));
        this.ket = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.warna = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public Surat() {
    }

    /**
     *
     * @param agenda
     * @param id
     * @param warna
     * @param status
     * @param noSurat
     * @param tglTerima
     * @param no
     * @param perihal
     * @param dari
     * @param ket
     * @param tglSurat
     * @param sifat
     */
    public Surat(String id, Long no, String agenda, String noSurat, String sifat, String tglSurat, String tglTerima, String dari, String perihal, String ket, String status, String warna) {
        super();
        this.id = id;
        this.no = no;
        this.agenda = agenda;
        this.noSurat = noSurat;
        this.sifat = sifat;
        this.tglSurat = tglSurat;
        this.tglTerima = tglTerima;
        this.dari = dari;
        this.perihal = perihal;
        this.ket = ket;
        this.status = status;
        this.warna = warna;
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

    public Long getNo() {
        return no;
    }

    public void setNo(Long no) {
        this.no = no;
    }

    public Surat withNo(Long no) {
        this.no = no;
        return this;
    }

    public String getAgenda() {
        return agenda;
    }

    public void setAgenda(String agenda) {
        this.agenda = agenda;
    }

    public Surat withAgenda(String agenda) {
        this.agenda = agenda;
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

    public String getTglSurat() {
        return tglSurat;
    }

    public void setTglSurat(String tglSurat) {
        this.tglSurat = tglSurat;
    }

    public Surat withTglSurat(String tglSurat) {
        this.tglSurat = tglSurat;
        return this;
    }

    public String getTglTerima() {
        return tglTerima;
    }

    public void setTglTerima(String tglTerima) {
        this.tglTerima = tglTerima;
    }

    public Surat withTglTerima(String tglTerima) {
        this.tglTerima = tglTerima;
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

    public String getKet() {
        return ket;
    }

    public void setKet(String ket) {
        this.ket = ket;
    }

    public Surat withKet(String ket) {
        this.ket = ket;
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

    public String getWarna() {
        return warna;
    }

    public void setWarna(String warna) {
        this.warna = warna;
    }

    public Surat withWarna(String warna) {
        this.warna = warna;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("no", no).append("agenda", agenda).append("noSurat", noSurat).append("sifat", sifat).append("tglSurat", tglSurat).append("tglTerima", tglTerima).append("dari", dari).append("perihal", perihal).append("ket", ket).append("status", status).append("warna", warna).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(agenda).append(warna).append(status).append(noSurat).append(tglTerima).append(no).append(perihal).append(id).append(dari).append(ket).append(tglSurat).append(sifat).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Surat) == false) {
            return false;
        }
        Surat rhs = ((Surat) other);
        return new EqualsBuilder().append(agenda, rhs.agenda).append(warna, rhs.warna).append(status, rhs.status).append(noSurat, rhs.noSurat).append(tglTerima, rhs.tglTerima).append(no, rhs.no).append(perihal, rhs.perihal).append(id, rhs.id).append(dari, rhs.dari).append(ket, rhs.ket).append(tglSurat, rhs.tglSurat).append(sifat, rhs.sifat).isEquals();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(no);
        dest.writeValue(agenda);
        dest.writeValue(noSurat);
        dest.writeValue(sifat);
        dest.writeValue(tglSurat);
        dest.writeValue(tglTerima);
        dest.writeValue(dari);
        dest.writeValue(perihal);
        dest.writeValue(ket);
        dest.writeValue(status);
        dest.writeValue(warna);
    }

    public int describeContents() {
        return 0;
    }

}
