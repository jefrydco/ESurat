package com.example.esurat.home;

import android.support.annotation.NonNull;

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;

public class HomeModel implements SortedListAdapter.ViewModel {
    private final int mId;
    private final int mRank;
    private final String mPerihal;
    private final String mDari;
    private final String mTanggalTerima;
    private final String mStatus;
    private final String mNoAgenda;
    private final String mNoSurat;
    private final String mSifat;
    private final String mTanggalSurat;
    private final String mKeterangan;
    private final String mLinkLihatSurat;

    public HomeModel(int mId, int mRank, String mPerihal, String mDari, String mTanggalTerima, String mStatus, String mNoAgenda, String mNoSurat, String mSifat, String mTanggalSurat, String mKeterangan, String mLinkLihatSurat) {
        this.mId = mId;
        this.mRank = mRank;
        this.mPerihal = mPerihal;
        this.mDari = mDari;
        this.mTanggalTerima = mTanggalTerima;
        this.mStatus = mStatus;
        this.mNoAgenda = mNoAgenda;
        this.mNoSurat = mNoSurat;
        this.mSifat = mSifat;
        this.mTanggalSurat = mTanggalSurat;
        this.mKeterangan = mKeterangan;
        this.mLinkLihatSurat = mLinkLihatSurat;
    }

    public int getId() {
        return mId;
    }

    public int getRank() {
        return mRank;
    }

    public String getPerihal() {
        return mPerihal;
    }

    public String getDari() {
        return mDari;
    }

    public String getTanggalTerima() {
        return mTanggalTerima;
    }

    public String getStatus() {
        return mStatus;
    }

    public String getNoAgenda() {
        return mNoAgenda;
    }

    public String getNoSurat() {
        return mNoSurat;
    }

    public String getSifat() {
        return mSifat;
    }

    public String getTanggalSurat() {
        return mTanggalSurat;
    }

    public String getKeterangan() {
        return mKeterangan;
    }

    public String getLinkLihatSurat() {
        return mLinkLihatSurat;
    }

    @Override
    public <T> boolean isSameModelAs(@NonNull T model) {
        if (model instanceof HomeModel) {
            final HomeModel otherHomeModel = (HomeModel) model;
            return this.mId == otherHomeModel.mId;
        }
        return false;
    }

    @Override
    public <T> boolean isContentTheSameAs(@NonNull T model) {
        if (model instanceof HomeModel) {
            final HomeModel otherHomeModel = (HomeModel) model;
            return this.mRank == otherHomeModel.mRank &&
                    (this.mPerihal != null ?
                            this.mPerihal.equals(otherHomeModel.mPerihal) :
                            otherHomeModel.mPerihal == null);
        }
        return false;
    }
}
