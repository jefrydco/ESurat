package com.example.esurat.home;

import android.support.annotation.NonNull;
import android.view.View;

import com.bumptech.glide.request.RequestOptions;
import com.example.esurat.databinding.ItemHomeBinding;
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class HomeViewHolder extends SortedListAdapter.ViewHolder<HomeModel> {
    private ItemHomeBinding mItemHomeBinding;

    HomeViewHolder(ItemHomeBinding mItemEncylopediaBinding) {
        super(mItemEncylopediaBinding.getRoot());
        this.mItemHomeBinding = mItemEncylopediaBinding;
    }

    @Override
    protected void performBind(@NonNull HomeModel item) {
        mItemHomeBinding.itemHomeTextViewDari.setText(item.getDari());
        mItemHomeBinding.itemHomeTextViewJudul.setText(item.getPerihal());
        mItemHomeBinding.itemHomeTextViewStatus.setText(item.getStatus());
        mItemHomeBinding.itemHomeTextViewTanggalTerima.setText(item.getTanggalTerima());
    }
}