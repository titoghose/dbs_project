package com.example.android.requiry;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DiscussionActivity extends AppCompatActivity {

    private DiscussionsAdapter mAdapter;
    private Button postBtn;
    private EditText message;

    private String pName;
    private String pId;
    private int uId;

    private Timer timer;
    private TimerTask timerTask;

    final Handler handler = new Handler();
    private ListView listViewToDo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        pName = bundle.getString("pName");
        pId = bundle.getString("pID");
        SharedPreferences sp = getSharedPreferences("User",MODE_PRIVATE);
        uId = sp.getInt("uID",0);

        ActionBar actionBar = getSupportActionBar();
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.action_bar, null);
        TextView actionbar_title = (TextView)v.findViewById(R.id.action_bar_title);
        actionbar_title.setText(pName);
        assert actionBar != null;
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(v);

        mAdapter = new DiscussionsAdapter(this, R.layout.discussions_message);
        listViewToDo = (ListView) findViewById(R.id.msgview);
        listViewToDo.setAdapter(mAdapter);

        message = (EditText) findViewById(R.id.msg);

        startTimer();

        postBtn = (Button) findViewById(R.id.postBtn);
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        stopTimer();
        finish();
    }

    public void startTimer(){
        timer = new Timer();
        initializeTimerTask();
        timer.schedule(timerTask, 100, 3000);
    }

    public void stopTimer(){
        timer.cancel();
    }

    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        refreshItemsFromTable();
                    }
                });
            }
        };
    }


    private void refreshItemsFromTable() {

        mAdapter.setNotifyOnChange(false);
        mAdapter.clear();

        JSONObject obj = new JSONObject();
        try {
            obj.put("uProjectId", ""+pId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        SubmitAsyncTask.InformComplete mycallback = new SubmitAsyncTask.InformComplete() {
            @Override
            public void postData(String result) {

                try {
                    JSONArray jsonArray = new JSONArray(result);
                    for(int i = 0; i<jsonArray.length(); i++){
                        JSONObject obj = jsonArray.getJSONObject(i);
                        String uName = obj.getString("uName");
                        String msg = obj.getString("msg");
                        String time_stamp = obj.getString("sTime");
                        SimpleDateFormat date = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd,yyyy");
                        String s_date = null;
                        try {
                            Date st_date = date.parse(time_stamp);
                            // Log.e("ProFeed","start date:"+st_date);
                            // Log.e("ProFeed","end date:"+ed_date);
                            s_date = simpleDateFormat.format(st_date);
                        } catch (Exception e) {
                            Log.e("Pro Feed", "Parsing failed miserably " + e);
                        }
                        Discussions d = new Discussions(pName, uName, msg, s_date);
                        mAdapter.add(d);
                    }
                    mAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        String url = "http://192.168.43.19:5000/DiscussionsQuery";
        new SubmitAsyncTask(DiscussionActivity.this, url, obj, mycallback).execute();
    }

    private void sendMessage() {
        String msg = message.getText().toString().trim();

        JSONObject obj = new JSONObject();
        try {
            obj.put("uId", ""+uId);
            obj.put("msg", msg);
            obj.put("uProjectId", ""+pId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        SubmitAsyncTask.InformComplete mycallback = new SubmitAsyncTask.InformComplete() {
            @Override
            public void postData(String result) {

            }
        };
        message.setText("");
        String url = "http://192.168.43.19:5000/Discussions";
        new SubmitAsyncTask(DiscussionActivity.this, url, obj, mycallback).execute();
        refreshItemsFromTable();

    }
}
