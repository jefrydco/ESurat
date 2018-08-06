package com.example.esurat.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class Page implements Serializable, Parcelable
{

    @SerializedName("currentPage")
    @Expose
    private Long currentPage;
    @SerializedName("totalPage")
    @Expose
    private Long totalPage;
    public final static Parcelable.Creator<Page> CREATOR = new Creator<Page>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Page createFromParcel(Parcel in) {
            return new Page(in);
        }

        public Page[] newArray(int size) {
            return (new Page[size]);
        }

    }
            ;
    private final static long serialVersionUID = 5189237552766779626L;

    protected Page(Parcel in) {
        this.currentPage = ((Long) in.readValue((Long.class.getClassLoader())));
        this.totalPage = ((Long) in.readValue((Long.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public Page() {
    }

    /**
     *
     * @param currentPage
     * @param totalPage
     */
    public Page(Long currentPage, Long totalPage) {
        super();
        this.currentPage = currentPage;
        this.totalPage = totalPage;
    }

    public Long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Long currentPage) {
        this.currentPage = currentPage;
    }

    public Page withCurrentPage(Long currentPage) {
        this.currentPage = currentPage;
        return this;
    }

    public Long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Long totalPage) {
        this.totalPage = totalPage;
    }

    public Page withTotalPage(Long totalPage) {
        this.totalPage = totalPage;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("currentPage", currentPage).append("totalPage", totalPage).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(currentPage).append(totalPage).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Page) == false) {
            return false;
        }
        Page rhs = ((Page) other);
        return new EqualsBuilder().append(currentPage, rhs.currentPage).append(totalPage, rhs.totalPage).isEquals();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(currentPage);
        dest.writeValue(totalPage);
    }

    public int describeContents() {
        return 0;
    }

}