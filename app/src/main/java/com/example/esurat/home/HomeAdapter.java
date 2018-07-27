package com.example.esurat.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.esurat.databinding.ItemHomeBinding;
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;

import java.util.Comparator;

public class HomeAdapter extends SortedListAdapter<HomeModel> {

    public HomeAdapter(@NonNull Context context, @NonNull Class<HomeModel> itemClass, @NonNull Comparator<HomeModel> comparator) {
        super(context, HomeModel.class, comparator);
    }

    @NonNull
    @Override
    protected ViewHolder<? extends HomeModel> onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent, int viewType) {
        final ItemHomeBinding itemHomeBinding = ItemHomeBinding.inflate(inflater, parent, false);
        return new HomeViewHolder(itemHomeBinding);
    }
}
