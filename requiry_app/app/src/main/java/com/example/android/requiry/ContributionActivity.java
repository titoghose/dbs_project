package com.example.android.requiry;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ContributionActivity extends AppCompatActivity implements ContributorAdapter.ListItemClickListener {
    private ContributorAdapter contributorAdapter;
    private RecyclerView recyclerView;
    ArrayList<UserData> extractedData;
    int pID;
    JSONObject jsonObject;
    private final String url = "http:///Contributor";//TODO Add URL here

    @Override
    protected void onCreate(Bundle savedInstanceState)   {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contribution);
        recyclerView = (RecyclerView) findViewById(R.id.recycle_view_contributors);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        contributorAdapter = new ContributorAdapter(this,extractedData);
        recyclerView.setAdapter(contributorAdapter);
        pID = Integer.parseInt(getIntent().getStringExtra("pID"));
        try {
            jsonObject = new JSONObject();
            jsonObject.put("pID", pID);
        }
        catch (Exception e){
            Log.e("ContributionActivity",""+e);
        }
        SubmitAsyncTask.InformComplete mycallback = new SubmitAsyncTask.InformComplete() {
            @Override
            public void postData(String result) {
               extractedData = extractUserData(result);
                contributorAdapter = new ContributorAdapter(ContributionActivity.this,extractedData);
                recyclerView.setAdapter(contributorAdapter);
            }
        };
        new SubmitAsyncTask(this,url,jsonObject,mycallback).execute();

    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        UserData userData = extractedData.get(clickedItemIndex);
        Bundle sendDatatoProfile = new Bundle();
        sendDatatoProfile.putString("identifier", "notloggedInUser");
        sendDatatoProfile.putString("uID", "" + userData.getuID());
        sendDatatoProfile.putString("uName",userData.getName());
        sendDatatoProfile.putString("uNumber", userData.getNumber());
        sendDatatoProfile.putString("uEmail", userData.getEmail());
        sendDatatoProfile.putString("uUsername",userData.getUsername());
        sendDatatoProfile.putString("uWho", "" + userData.getWho());
        sendDatatoProfile.putString("uDesc", userData.getDesc());
        Intent intent = new Intent(this,ProfileActivity.class).putExtras(sendDatatoProfile);
        startActivity(intent);
    }

    public static ArrayList<UserData> extractUserData(String SAMPLE_JSON_RESPONSE) {
        if(TextUtils.isEmpty(SAMPLE_JSON_RESPONSE))
            return null;
        ArrayList<UserData> dbdatas = new ArrayList<>();

        Log.e("ContributionActivity",SAMPLE_JSON_RESPONSE);
        try {


            JSONArray jsonArray = new JSONArray(SAMPLE_JSON_RESPONSE);
            for(int i=0;i<jsonArray.length();i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                int uid = obj.getInt("uID");
                String uname = obj.getString("uName");
                String unumber = obj.getString("uNumber");
                String  uemail = obj.getString("uEmail");
                String uusername = obj.getString("uUsername");
                int uWho = obj.getInt("uWho");
                String udesc = obj.getString("uDesc");
                dbdatas.add(new UserData(uid,uname,unumber,uemail,uusername,uWho,udesc));
            }

        } catch (JSONException e) {

            Log.e("ProFeedAct", "Problem parsing the earthquake JSON results", e);
        }


        return dbdatas;
    }
}
