package com.example.android.requiry;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ProFeedActivity extends AppCompatActivity implements ProFeedAdapter.ListItemClickListener,LoaderManager.LoaderCallbacks<String> {
    private static final String url = "http://192.168.43.19:5000/ProFeed";
    private ProFeedAdapter proFeedAdapter;
    private RecyclerView recyclerView;
    ArrayList<ProFeedData> extractedData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_feed);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_action_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProFeedActivity.this, CreateProjectActivity.class);
                startActivity(intent);
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        proFeedAdapter = new ProFeedAdapter(this,extractedData);
        recyclerView.setAdapter(proFeedAdapter);
        getLoaderManager().initLoader(0, null, this);

    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        ProFeedData curProFeedData = extractedData.get(clickedItemIndex);
        Bundle extras = new Bundle();
        extras.putString("pID",""+curProFeedData.getpID());
        extras.putString("pName",curProFeedData.getPname());
        extras.putString("pCreator",curProFeedData.getCreator());
        extras.putString("pDesc",curProFeedData.getProject_desc());
        extras.putString("pDateStarts",curProFeedData.getStart_date());
        extras.putString("pDateEnds",curProFeedData.getEnd_date());
        extras.putString("pDomain",curProFeedData.getDomain());
        extras.putString("pLink",curProFeedData.getpLink());
        Intent intent = new Intent(this,ProjectActivity.class).putExtras(extras);
        startActivity(intent);
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new DataAsyncLoader(this,url);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        extractedData  = extractProfeedData(data);
        proFeedAdapter = new ProFeedAdapter(this,extractedData);
        recyclerView.setAdapter(proFeedAdapter);

    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
        //TODO Add onLoaderReset
    }
    public static ArrayList<ProFeedData> extractProfeedData(String SAMPLE_JSON_RESPONSE) {
        if(TextUtils.isEmpty(SAMPLE_JSON_RESPONSE))
            return null;
        ArrayList<ProFeedData> dbdatas = new ArrayList<>();
       // Log.e("ProFeed",SAMPLE_JSON_RESPONSE);

        try {


            JSONArray jsonArray = new JSONArray(SAMPLE_JSON_RESPONSE);
            for(int i=0;i<jsonArray.length();i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                int pid = obj.getInt("pID");
                String pname = obj.getString("pName");
                String created_by = obj.getString("pCreated_By");
                String  domain = obj.getString("pDomain");
                SimpleDateFormat date = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd,yyyy");
                String start_date = null;
                String end_date = null;
                try {
                     Date st_date = date.parse(obj.getString("pDateStarts"));
                     Date ed_date = date.parse(obj.getString("pDateEnds"));
                    // Log.e("ProFeed","start date:"+st_date);
                    // Log.e("ProFeed","end date:"+ed_date);
                    start_date = simpleDateFormat.format(st_date);
                    end_date = simpleDateFormat.format(ed_date);
                } catch (Exception e) {
                    Log.e("Pro Feed","Parsing failed miserably "+e);
                }
                String pDesc = obj.getString("pDesc");
                String pLink = obj.getString("pLink");
                dbdatas.add(new ProFeedData(pid,pname,created_by,domain,start_date,end_date,pDesc,pLink));
            }

        } catch (JSONException e) {

            Log.e("ProFeedAct", "Problem parsing the earthquake JSON results", e);
        }


        return dbdatas;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profeed_activity_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id  = item.getItemId();
        if(id == R.id.user_profile){
            Intent intent = new Intent(this,ProfileActivity.class);
            SharedPreferences sp = getSharedPreferences("User", Context.MODE_PRIVATE);

            Bundle myBundle = new Bundle();
            //TODO Change this to get Actual Values
            myBundle.putString("identifier","loggedInUser");
            myBundle.putString("uName", sp.getString("uName"," "));
            myBundle.putString("uWho", ""+sp.getInt("uWho",0));
            myBundle.putString("uNumber", "" + sp.getLong("uNumber",0));
            myBundle.putString("uUsername",sp.getString("uUsername",""));
            myBundle.putString("uEmail",sp.getString("uEmail",""));
            myBundle.putString("uDesc",sp.getString("uDesc",""));
            myBundle.putString("uPassword",sp.getString("uPassword",""));
            intent.putExtras(myBundle);
            startActivity(intent);

        }
        else if(id == R.id.user_logout){
            SharedPreferences.Editor editor = getSharedPreferences("User",MODE_PRIVATE).edit();
            editor.clear(); //clear all stored data
            editor.apply();
            Intent intent = new Intent(ProFeedActivity.this,SignInActivity.class);
            NavUtils.navigateUpTo(ProFeedActivity.this,intent);
        }
        return true;
    }
}