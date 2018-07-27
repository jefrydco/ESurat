package com.example.esurat.utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.esurat.R;

public class RecyclerItemClickSupportUtils {
    /**
     * The recycler view object.
     */
    private final RecyclerView mRecyclerView;

    /**
     * The item click listener object.
     */
    private OnItemClickListener mOnItemClickListener;

    /**
     * The item long click listener object.
     */
    private OnItemLongClickListener mOnItemLongClickListener;

    /**
     * Instantiate click listener object.
     */
    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(v);
                mOnItemClickListener.onItemClicked(mRecyclerView, holder.getAdapterPosition(), v);
            }
        }
    };

    /**
     * Instantiate long click listener object.
     */
    private View.OnLongClickListener mOnLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            if (mOnItemLongClickListener != null) {
                RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(v);
                return mOnItemLongClickListener.onItemLongClicked(mRecyclerView,
                        holder.getAdapterPosition(), v);
            }
            return false;
        }
    };

    /**
     * Instantiate child attach state change listener object.
     */
    private RecyclerView.OnChildAttachStateChangeListener mAttachListener
            = new RecyclerView.OnChildAttachStateChangeListener() {
        @Override
        public void onChildViewAttachedToWindow(View view) {
            if (mOnItemClickListener != null) {
                view.setOnClickListener(mOnClickListener);
            }
            if (mOnItemLongClickListener != null) {
                view.setOnLongClickListener(mOnLongClickListener);
            }
        }

        @Override
        public void onChildViewDetachedFromWindow(View view) {

        }
    };

    /**
     * Construct a new instance of {@link RecyclerItemClickSupportUtils}.
     *
     * @param recyclerView The recycler view to add item click support.
     */
    private RecyclerItemClickSupportUtils(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mRecyclerView.setTag(R.id.item_click_support, this);
        mRecyclerView.addOnChildAttachStateChangeListener(mAttachListener);
    }

    /**
     * Add item click support to the recycler view object.
     *
     * @param view The recycler view object to add item click support functionality.
     * @return The item click support object.
     */
    public static RecyclerItemClickSupportUtils addTo(RecyclerView view) {
        RecyclerItemClickSupportUtils support = (RecyclerItemClickSupportUtils) view.getTag(R.id.item_click_support);
        if (support == null) {
            support = new RecyclerItemClickSupportUtils(view);
        }
        return support;
    }

    /**
     * Remove item click support to the recycler view object.
     *
     * @param view The recycler view object to remove item click support functionality.
     * @return The item click support object.
     */
    public static RecyclerItemClickSupportUtils removeFrom(RecyclerView view) {
        RecyclerItemClickSupportUtils support = (RecyclerItemClickSupportUtils) view.getTag(R.id.item_click_support);
        if (support != null) {
            support.detach(view);
        }
        return support;
    }

    /**
     * Add set item click listener.
     *
     * @param listener Item click listener object to add.
     * @return The item click support object.
     */
    public RecyclerItemClickSupportUtils setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
        return this;
    }

    /**
     * Add set item long click listener.
     *
     * @param listener Item long click listener object to add.
     * @return The item click support object.
     */
    public RecyclerItemClickSupportUtils setOnItemLongClickListener(OnItemLongClickListener listener) {
        mOnItemLongClickListener = listener;
        return this;
    }

    /**
     * Detach state change listener.
     *
     * @param view The recycler view object to detach.
     */
    private void detach(RecyclerView view) {
        view.removeOnChildAttachStateChangeListener(mAttachListener);
        view.setTag(R.id.item_click_support, null);
    }

    /**
     * Item click listener interface we should implement in order to add item click support.
     */
    public interface OnItemClickListener {
        void onItemClicked(RecyclerView recyclerView, int position, View v);
    }

    /**
     * Item long click listener interface we should implement in order to add item long click support.
     */
    public interface OnItemLongClickListener {
        boolean onItemLongClicked(RecyclerView recyclerView, int position, View v);
    }
}
