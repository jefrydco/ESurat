package com.example.esurat.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.example.esurat.R;
import com.example.esurat.databinding.ItemHomeBinding;
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;

public class HomeViewHolder extends SortedListAdapter.ViewHolder<HomeModel> {
    private ItemHomeBinding mItemHomeBinding;
    private Context context;

    HomeViewHolder(Context context, ItemHomeBinding mItemEncylopediaBinding) {
        super(mItemEncylopediaBinding.getRoot());
        this.mItemHomeBinding = mItemEncylopediaBinding;
        this.context = context;
    }

    @Override
    protected void performBind(@NonNull HomeModel item) {
        switch (item.getStatus()) {
            case "PROSES":
                mItemHomeBinding.itemHomeTextViewStatus.setTextColor(ContextCompat.getColor(context, R.color.error));
                break;
            case "SELESAI":
                mItemHomeBinding.itemHomeTextViewStatus.setTextColor(ContextCompat.getColor(context, R.color.info));
                break;
            default:
                mItemHomeBinding.itemHomeTextViewStatus.setTextColor(ContextCompat.getColor(context, R.color.error));
                break;
        }
        mItemHomeBinding.setHome(item);
    }
}