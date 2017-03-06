package com.example.android.requiry;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by MAHE on 05-Mar-17.
 */
public class ProFeedAdapter extends RecyclerView.Adapter<ProFeedAdapter.ViewHolder> {
    private final ListItemClickListener mOnClickListener;
    private int mNumberOfItems;

    public ProFeedAdapter(int numberOfItems,ListItemClickListener mOnClickListener) {
        mNumberOfItems = numberOfItems;
        this.mOnClickListener = mOnClickListener;
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = 0;   //Add a layout ID here
        LayoutInflater inflater = LayoutInflater.from(context);
        View view  = inflater.inflate(layoutIdForListItem, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mNumberOfItems;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
