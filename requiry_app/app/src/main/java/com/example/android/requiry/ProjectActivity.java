package com.example.android.requiry;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ProjectActivity extends AppCompatActivity {

    private String pId;
    private TextView mName;
    private TextView mCreator;
    private TextView mDomain;
    private TextView mDesc;
    private TextView mLinks;
    private TextView mStarted;
    private TextView mETC;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        mName = (TextView) findViewById(R.id.pName_textView);
        mCreator = (TextView) findViewById(R.id.pCreator_textView);
        mDomain = (TextView) findViewById(R.id.pDomain_textView);
        mDesc = (TextView) findViewById(R.id.pDesc_textView);
        mLinks = (TextView) findViewById(R.id.pLinks_textView);
        mStarted = (TextView) findViewById(R.id.pStarted_textView);
        mETC = (TextView) findViewById(R.id.pETC_textView);

        final Bundle dataFromProFeed = getIntent().getExtras();
        pId = dataFromProFeed.getString("pId");
        mName.setText(dataFromProFeed.getString("pName"));
        mCreator.setText(dataFromProFeed.getString("pCreator"));
        mDomain.setText(dataFromProFeed.getString("pDomain"));
        mDesc.setText(dataFromProFeed.getString("pDesc"));
        mLinks.setText(dataFromProFeed.getString("pLinks"));
        mStarted.setText(dataFromProFeed.getString("pDateStarts"));
        mETC.setText(dataFromProFeed.getString("pDateEnds"));

        Button mApply = (Button) findViewById(R.id.apply_button);
        Button mDiscussion = (Button) findViewById(R.id.discuss_button);

        mApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mDiscussion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent discussion = new Intent(ProjectActivity.this, DiscussionActivity.class).putExtras(dataFromProFeed);
                discussion.putExtra("pName", mName.getText().toString().trim());
                startActivity(discussion);
            }
        });

        ActionBar actionBar = getSupportActionBar();
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.action_bar, null);
        TextView actionbar_title = (TextView)v.findViewById(R.id.action_bar_title);
        actionbar_title.setText(mName.getText().toString().trim());
        assert actionBar != null;
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(v);
    }
}
