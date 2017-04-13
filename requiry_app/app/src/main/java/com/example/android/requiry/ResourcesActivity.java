package com.example.android.requiry;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ResourcesActivity extends AppCompatActivity {

    private ResourcesAdapter mAdapter;
    private ListView listView;

    private String dName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources);

        Intent intent = getIntent();
        dName = intent.getStringExtra("dName");

        ActionBar actionBar = getSupportActionBar();
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.action_bar, null);
        TextView actionbar_title = (TextView)v.findViewById(R.id.action_bar_title);
        actionbar_title.setText(dName);
        assert actionBar != null;
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(v);

        mAdapter = new ResourcesAdapter(this, R.layout.resource_link);
        listView = (ListView) findViewById(R.id.resources_list);
        listView.setAdapter(mAdapter);

        refreshItemsFromTable();

    }

    private void refreshItemsFromTable() {

        mAdapter.setNotifyOnChange(false);
        mAdapter.clear();

        JSONObject obj = new JSONObject();
        try {
            obj.put("dName", dName);
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
                        String link = obj.getString("rLink");
                        Resources r = new Resources(link);
                        mAdapter.add(r);
                    }
                    mAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        String url = "http:///ResourcesQuery";//TODO Add URL here
        new SubmitAsyncTask(ResourcesActivity.this, url, obj, mycallback).execute();
    }
}
