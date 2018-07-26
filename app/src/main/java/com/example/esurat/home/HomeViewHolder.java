package com.example.esurat.home;

import android.support.annotation.NonNull;

import com.bumptech.glide.request.RequestOptions;
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class HomeViewHolder extends SortedListAdapter.ViewHolder<HomeModel> {
    private ItemHomeBinding mItemEncylopediaBinding;

    HomeViewHolder(ItemHomeBinding mItemEncylopediaBinding) {
        super(mItemEncylopediaBinding.getRoot());
        this.mItemEncylopediaBinding = mItemEncylopediaBinding;
    }

    @Override
    protected void performBind(@NonNull HomeModel item) {

    }

/*    @Override
    protected void performBind(@NonNull HomeModel item) {
        GlideApp.with(mItemEncylopediaBinding.itemHomeImageViewThumbnail)
                .load(item.getImageUrl())
                .placeholder(R.drawable.ic_local_florist_pink_64dp)
                .override(64, 64)
                .centerCrop()
                .apply(RequestOptions.circleCropTransform())
                .transition(withCrossFade())
                .into(mItemEncylopediaBinding.itemHomeImageViewThumbnail);
        mItemEncylopediaBinding.setHome(item);
    }*/
}