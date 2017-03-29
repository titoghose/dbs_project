package com.example.android.requiry;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by MAHE on 10-Mar-17.
 */
public class ContributorAdapter extends RecyclerView.Adapter<ContributorAdapter.ViewHolder> {
    private static final String LOG_TAG = ContributorAdapter.class.getSimpleName();
    private TextView mCircleTextView;
    private TextView mContributorName;
    final private ListItemClickListener mOnClickListener;
    private ArrayList<UserData> extracted_Data;
    private UserData curContributorData;
    public ContributorAdapter(ListItemClickListener mOnClickListener,ArrayList<UserData> data) {
        this.mOnClickListener = mOnClickListener;
        extracted_Data = data;
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.contributors_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        curContributorData = extracted_Data.get(position);
        mCircleTextView.setText(""+curContributorData.getName().charAt(0));
        mContributorName.setText(curContributorData.getName());
    }

    @Override
    public int getItemCount() {
        if(extracted_Data!=null)
            return extracted_Data.size();
        else
            return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ViewHolder(View itemView) {
            super(itemView);
            mCircleTextView = (TextView) itemView.findViewById(R.id.circle_text_view);
            mContributorName = (TextView) itemView.findViewById(R.id.contributor_name_text_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnClickListener.onListItemClick(getAdapterPosition());
        }
    }
}
