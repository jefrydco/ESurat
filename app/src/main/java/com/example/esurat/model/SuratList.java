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

public class SuratList implements Serializable, Parcelable
{

    @SerializedName("data")
    @Expose
    private List<Surat> data = null;
    @SerializedName("page")
    @Expose
    private Page page;
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
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

    }
            ;
    private final static long serialVersionUID = 8500328989317952717L;

    protected SuratList(Parcel in) {
        in.readList(this.data, (com.example.esurat.model.Surat.class.getClassLoader()));
        this.page = ((Page) in.readValue((Page.class.getClassLoader())));
        this.error = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public SuratList() {
    }

    /**
     *
     * @param message
     * @param error
     * @param page
     * @param data
     */
    public SuratList(List<Surat> data, Page page, Boolean error, String message) {
        super();
        this.data = data;
        this.page = page;
        this.error = error;
        this.message = message;
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

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public SuratList withPage(Page page) {
        this.page = page;
        return this;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public SuratList withError(Boolean error) {
        this.error = error;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SuratList withMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("data", data).append("page", page).append("error", error).append("message", message).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(message).append(error).append(page).append(data).toHashCode();
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
        return new EqualsBuilder().append(message, rhs.message).append(error, rhs.error).append(page, rhs.page).append(data, rhs.data).isEquals();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(data);
        dest.writeValue(page);
        dest.writeValue(error);
        dest.writeValue(message);
    }

    public int describeContents() {
        return 0;
    }

}

