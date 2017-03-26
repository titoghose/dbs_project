package com.example.android.requiry;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import static com.example.android.requiry.R.id.username;

public class DiscussionActivity extends AppCompatActivity {

    private DiscussionsAdapter mAdapter;
    private Button postBtn;
    private EditText message;

    private int pId;
    private int uId;

    private Timer timer;
    private TimerTask timerTask;

    final Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion);

        mAdapter = new DiscussionsAdapter(this, R.layout.discussions_message);
        ListView listViewToDo = (ListView) findViewById(R.id.msgview);
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

    public void startTimer(){
        timer = new Timer();
        initializeTimerTask();
        timer.schedule(timerTask, 100, 3000);
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
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        String url = "http://192.168.43.19:5000/Discussions";
        new SubmitAsyncTask(DiscussionActivity.this, url, obj, mycallback).execute();
    }

    private void sendMessage() {
        String msg = message.getText().toString().trim();

        JSONObject obj = new JSONObject();
        try {
            obj.put("uId", ""+uId);
            obj.put("uMessage", msg);
            obj.put("uProjectId", ""+pId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        SubmitAsyncTask.InformComplete mycallback = new SubmitAsyncTask.InformComplete() {
            @Override
            public void postData(String result) {

            }
        };
        String url = "http://192.168.43.19:5000/Discussions";
        new SubmitAsyncTask(DiscussionActivity.this, url, obj, mycallback).execute();

    }
}
