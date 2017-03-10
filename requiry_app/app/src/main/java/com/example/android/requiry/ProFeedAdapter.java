package com.example.android.requiry;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by MAHE on 05-Mar-17.
 */
public class ProFeedAdapter extends RecyclerView.Adapter<ProFeedAdapter.ViewHolder> {
    TextView mProjectNameTextView;
    TextView mCircularTextView;
    TextView mCreatedByTextView;
    TextView mStartDateTextView;
    TextView mEndDateTextView;
    TextView mDomainTextView;
    private final ListItemClickListener mOnClickListener;
    ProFeedData curProFeedData;
    private ArrayList<ProFeedData> extracted_Data;
    public ProFeedAdapter(ListItemClickListener mOnClickListener,ArrayList<ProFeedData> data) {
        this.mOnClickListener = mOnClickListener;
        extracted_Data = data;
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemId);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.profeed_recycle_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view  = inflater.inflate(layoutIdForListItem, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        curProFeedData = extracted_Data.get(position);
        mProjectNameTextView.setText(curProFeedData.getPname());
        mCreatedByTextView.setText(curProFeedData.getCreator());
        mDomainTextView.setText(curProFeedData.getDomain());
      //  mEndDateTextView.setText(curProFeedData.getEnd_date());
      //  mStartDateTextView.setText(curProFeedData.getStart_date());
        mCircularTextView.setText(String.valueOf(curProFeedData.getPname().charAt(0)));
    }

    @Override
    public int getItemCount() {
        if(extracted_Data!=null)
            return extracted_Data.size();
        else
            return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ViewHolder(View itemView) {
            super(itemView);
            mProjectNameTextView = (TextView) itemView.findViewById(R.id.project_name);
            mCircularTextView = (TextView) itemView.findViewById(R.id.text_circle);
            mCreatedByTextView = (TextView) itemView.findViewById(R.id.created_by);
          //  mStartDateTextView = (TextView) itemView.findViewById(R.id.start_date);
          //  mEndDateTextView = (TextView) itemView.findViewById(R.id.end_date);
            mDomainTextView = (TextView) itemView.findViewById(R.id.domain);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedItemIndex = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedItemIndex);
        }
    }
}
