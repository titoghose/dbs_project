package com.example.android.requiry;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DomainsActivity extends AppCompatActivity {

    private DomainsAdapter mAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_domains);

        ActionBar actionBar = getSupportActionBar();
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.action_bar, null);
        TextView actionbar_title = (TextView)v.findViewById(R.id.action_bar_title);
        actionbar_title.setText("Domains");
        assert actionBar != null;
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(v);

        mAdapter = new DomainsAdapter(this, R.layout.domains_block);
        listView = (ListView) findViewById(R.id.domain_list);
        listView.setAdapter(mAdapter);

        refreshItemsFromTable();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                Log.e("Domains","listerner working");
                // When clicked, show a toast with the TextView text
                Domains domains = (Domains) listView.getItemAtPosition(position);
                Intent goToResources = new Intent(DomainsActivity.this, ResourcesActivity.class);
                goToResources.putExtra("dName", domains.getdName());
                startActivity(goToResources);
            }
        });

    }

    private void refreshItemsFromTable() {

        mAdapter.setNotifyOnChange(false);
        mAdapter.clear();

        JSONObject obj = new JSONObject();
        try {
            obj.put("uProjectId", "");
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
                        String dName = obj.getString("dName");
                        int dNumOfProj = obj.getInt("dNumOfProj");
                        Domains d = new Domains(dName, dNumOfProj);
                        mAdapter.add(d);
                    }
                    mAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        String url = "http:///DomainsQuery";//TODO Add URL here
        new SubmitAsyncTask(DomainsActivity.this, url, obj, mycallback).execute();
    }
}
