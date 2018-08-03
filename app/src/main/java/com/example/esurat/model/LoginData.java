
package com.example.esurat.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class LoginData implements Serializable, Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("id_induk")
    @Expose
    private String idInduk;
    @SerializedName("jabatan")
    @Expose
    private String jabatan;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("hp")
    @Expose
    private String hp;
    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("pass")
    @Expose
    private String pass;
    public final static Parcelable.Creator<LoginData> CREATOR = new Creator<LoginData>() {


        @SuppressWarnings({
                "unchecked"
        })
        public LoginData createFromParcel(Parcel in) {
            return new LoginData(in);
        }

        public LoginData[] newArray(int size) {
            return (new LoginData[size]);
        }

    }
            ;
    private final static long serialVersionUID = -6649302474272761799L;

    protected LoginData(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.idInduk = ((String) in.readValue((String.class.getClassLoader())));
        this.jabatan = ((String) in.readValue((String.class.getClassLoader())));
        this.nama = ((String) in.readValue((String.class.getClassLoader())));
        this.hp = ((String) in.readValue((String.class.getClassLoader())));
        this.user = ((String) in.readValue((String.class.getClassLoader())));
        this.pass = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public LoginData() {
    }

    /**
     *
     * @param jabatan
     * @param id
     * @param hp
     * @param idInduk
     * @param nama
     * @param user
     * @param pass
     */
    public LoginData(String id, String idInduk, String jabatan, String nama, String hp, String user, String pass) {
        super();
        this.id = id;
        this.idInduk = idInduk;
        this.jabatan = jabatan;
        this.nama = nama;
        this.hp = hp;
        this.user = user;
        this.pass = pass;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LoginData withId(String id) {
        this.id = id;
        return this;
    }

    public String getIdInduk() {
        return idInduk;
    }

    public void setIdInduk(String idInduk) {
        this.idInduk = idInduk;
    }

    public LoginData withIdInduk(String idInduk) {
        this.idInduk = idInduk;
        return this;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public LoginData withJabatan(String jabatan) {
        this.jabatan = jabatan;
        return this;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public LoginData withNama(String nama) {
        this.nama = nama;
        return this;
    }

    public String getHp() {
        return hp;
    }

    public void setHp(String hp) {
        this.hp = hp;
    }

    public LoginData withHp(String hp) {
        this.hp = hp;
        return this;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public LoginData withUser(String user) {
        this.user = user;
        return this;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public LoginData withPass(String pass) {
        this.pass = pass;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("idInduk", idInduk).append("jabatan", jabatan).append("nama", nama).append("hp", hp).append("user", user).append("pass", pass).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(jabatan).append(id).append(hp).append(idInduk).append(nama).append(user).append(pass).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof LoginData) == false) {
            return false;
        }
        LoginData rhs = ((LoginData) other);
        return new EqualsBuilder().append(jabatan, rhs.jabatan).append(id, rhs.id).append(hp, rhs.hp).append(idInduk, rhs.idInduk).append(nama, rhs.nama).append(user, rhs.user).append(pass, rhs.pass).isEquals();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(idInduk);
        dest.writeValue(jabatan);
        dest.writeValue(nama);
        dest.writeValue(hp);
        dest.writeValue(user);
        dest.writeValue(pass);
    }

    public int describeContents() {
        return 0;
    }

}