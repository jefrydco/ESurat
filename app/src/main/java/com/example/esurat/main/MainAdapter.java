package com.example.esurat.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.esurat.databinding.ItemMainBinding;
import com.example.esurat.model.Surat;
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;

import java.util.Comparator;

public class MainAdapter extends SortedListAdapter<Surat> {

    private Context context;

    MainAdapter(@NonNull Context context, @NonNull Class<Surat> itemClass, @NonNull Comparator<Surat> comparator) {
        super(context, Surat.class, comparator);
        this.context = context;
    }

    @NonNull
    @Override
    protected ViewHolder<? extends Surat> onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent, int viewType) {
        final ItemMainBinding itemMainBinding = ItemMainBinding.inflate(inflater, parent, false);
        return new MainViewHolder(context, itemMainBinding);
    }
}
