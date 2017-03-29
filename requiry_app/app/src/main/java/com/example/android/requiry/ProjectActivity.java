package com.example.android.requiry;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

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

        SharedPreferences sp = getSharedPreferences("User", MODE_PRIVATE);
        String uName = sp.getString("uName", "");

        mName = (TextView) findViewById(R.id.pName_textView);
        mCreator = (TextView) findViewById(R.id.pCreator_textView);
        mDomain = (TextView) findViewById(R.id.pDomain_textView);
        mDesc = (TextView) findViewById(R.id.pDesc_textView);
        mLinks = (TextView) findViewById(R.id.pLinks_textView);
        mStarted = (TextView) findViewById(R.id.pStarted_textView);
        mETC = (TextView) findViewById(R.id.pETC_textView);

        final Bundle dataFromProFeed = getIntent().getExtras();
        pId = dataFromProFeed.getString("pID");
        mName.setText(dataFromProFeed.getString("pName"));
        mCreator.setText(dataFromProFeed.getString("pCreator"));
        mDomain.setText(dataFromProFeed.getString("pDomain"));
        mDesc.setText(dataFromProFeed.getString("pDesc"));
        mLinks.setText(dataFromProFeed.getString("pLinks"));
        mStarted.setText(dataFromProFeed.getString("pDateStarts"));
        mETC.setText(dataFromProFeed.getString("pDateEnds"));

        Button mApply = (Button) findViewById(R.id.apply_button);
        Button mDiscussion = (Button) findViewById(R.id.discuss_button);
        Button mContributors = (Button) findViewById(R.id.contributors_button);
        mContributors.setPaintFlags(mContributors.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        ActionBar actionBar = getSupportActionBar();
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.action_bar, null);
        TextView actionbar_title = (TextView)v.findViewById(R.id.action_bar_title);
        actionbar_title.setText(mName.getText().toString().trim());
        assert actionBar != null;
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(v);


        if(!(uName.equalsIgnoreCase(mCreator.getText().toString().trim()))){
            mApply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //sendEmail();
                }
            });
        }
        else{
            mApply.setText("Delete");
            mApply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("pID", pId);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    SubmitAsyncTask.InformComplete myCallback = new SubmitAsyncTask.InformComplete() {
                        @Override
                        public void postData(String result) {
                            if(result.equals("success")) {
                                Toast.makeText(ProjectActivity.this, "Project Deleted", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ProjectActivity.this, ProFeedActivity.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(ProjectActivity.this,"Error Occurred",Toast.LENGTH_SHORT).show();
                            }
                        }
                    };
                    String url = "http://192.168.43.19:5000/DeleteProject";
                    new SubmitAsyncTask(ProjectActivity.this, url, jsonObject, myCallback).execute();
                }
            });
        }

        mDiscussion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent discussion = new Intent(ProjectActivity.this, DiscussionActivity.class).putExtras(dataFromProFeed);
                startActivity(discussion);
            }
        });

        mContributors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("ProjectActivity",""+pId);
                Intent  intent = new Intent(ProjectActivity.this,ContributionActivity.class).putExtra("pID",pId);
                startActivity(intent);
            }
        });

    }

    /*
    protected void sendEmail() {
        Log.i("Send email", "");
        String[] TO = {emailId};
        String[] CC = {""};
        String sample="Dear "+authorName+",\n\n(Your email goes here)\n\n(Please upload your CV and Cover Letter as attachments)\n\nsent via Requiry";
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Application For : "+projectName);
        emailIntent.putExtra(Intent.EXTRA_TEXT, sample);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Email Sent", "");
        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(ProjectsActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
    */
}
