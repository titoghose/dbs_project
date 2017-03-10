package com.example.android.requiry;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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


        try {


            JSONArray jsonArray = new JSONArray(SAMPLE_JSON_RESPONSE);
            for(int i=0;i<jsonArray.length();i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                int pid = obj.getInt("pID");
                String pname = obj.getString("pName");
                String created_by = obj.getString("pCreated_By");
                String  domain = obj.getString("pDomain");
                SimpleDateFormat date = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd,YYYY");
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
                dbdatas.add(new ProFeedData(pid,pname,created_by,domain,start_date,end_date,pDesc));
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
            Bundle myBundle = new Bundle();
           //TODO Change this to get Actual Values
            myBundle.putString("uName","Aayush Bhala");
            myBundle.putString("uWho","0");
            myBundle.putString("uNumber","9988776655");
            myBundle.putString("uUsername","aayushbhala");
            myBundle.putString("uEmail","aayushbhala@gmail.com");
            myBundle.putString("uDesc","Student at MIT");
            myBundle.putString("uPassword","iamaayush");
            intent.putExtras(myBundle);
            startActivity(intent);
        }
        return true;
    }
}
