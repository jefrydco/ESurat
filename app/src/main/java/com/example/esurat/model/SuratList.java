package com.example.esurat.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

public class SuratList implements Serializable, Parcelable {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<Surat> data = null;
    public final static Parcelable.Creator<SuratList> CREATOR = new Creator<SuratList>() {

        @SuppressWarnings({
                "unchecked"
        })
        public SuratList createFromParcel(Parcel in) {
            return new SuratList(in);
        }

        public SuratList[] newArray(int size) {
            return (new SuratList[size]);
        }

    };

    private final static long serialVersionUID = 2776766752783775788L;

    protected SuratList(Parcel in) {
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.data, (com.example.esurat.model.Surat.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public SuratList() {
    }

    /**
     *
     * @param status
     * @param data
     */
    public SuratList(String status, List<Surat> data) {
        super();
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SuratList withStatus(String status) {
        this.status = status;
        return this;
    }

    public List<Surat> getData() {
        return data;
    }

    public void setData(List<Surat> data) {
        this.data = data;
    }

    public SuratList withData(List<Surat> data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("status", status).append("data", data).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(status).append(data).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof SuratList)) {
            return false;
        }
        SuratList rhs = ((SuratList) other);
        return new EqualsBuilder().append(status, rhs.status).append(data, rhs.data).isEquals();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(status);
        dest.writeList(data);
    }

    public int describeContents() {
        return 0;
    }

}