package com.example.esurat.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.example.esurat.R;
import com.example.esurat.databinding.ItemMainBinding;
import com.example.esurat.model.Surat;
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;

public class MainViewHolder extends SortedListAdapter.ViewHolder<Surat> {
    private static final String TAG = "MainViewHolder";

    private ItemMainBinding mItemMainBinding;
    private Context context;

    MainViewHolder(Context context, ItemMainBinding mItemEncylopediaBinding) {
        super(mItemEncylopediaBinding.getRoot());
        this.mItemMainBinding = mItemEncylopediaBinding;
        this.context = context;
    }

    @Override
    protected void performBind(@NonNull Surat item) {
        if (getLayoutPosition() % 2 == 1) {
            mItemMainBinding.itemMainCardView.setBackgroundColor(ContextCompat.getColor(context, R.color.light_white));
        } else {
            mItemMainBinding.itemMainCardView.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }
        if (item.getStatus().equals(MainConstant.BELUM)) {
            mItemMainBinding.itemMainTextViewStatus.setTextColor(ContextCompat.getColor(context, R.color.error));
        }
        if (item.getStatus().equals(MainConstant.PROSSES)) {
            mItemMainBinding.itemMainTextViewStatus.setTextColor(ContextCompat.getColor(context, R.color.warning));
        }
        if (item.getStatus().equals(MainConstant.DISPOSISI)) {
            mItemMainBinding.itemMainTextViewStatus.setTextColor(ContextCompat.getColor(context, R.color.info));
        }
        if (item.getStatus().equals(MainConstant.SELESAI)) {
            mItemMainBinding.itemMainTextViewStatus.setTextColor(ContextCompat.getColor(context, R.color.success));
        }
        mItemMainBinding.setSurat(item);
    }
}