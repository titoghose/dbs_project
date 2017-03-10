package com.example.android.requiry;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

public class CreateProjectActivity extends AppCompatActivity {

    private AutoCompleteTextView mName;
    private AutoCompleteTextView mDomain;
    private EditText mLinks;
    private AutoCompleteTextView mDesc;
    private EditText mETC;

    private final String url = "http://192.168.43.19:5000/CreateProject";
    JSONObject jsonObject;

    public CreateProjectActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_project);

        ActionBar actionBar = getSupportActionBar();
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.action_bar, null);
        TextView actionbar_title = (TextView)v.findViewById(R.id.action_bar_title);
        actionbar_title.setText("Create");
        assert actionBar != null;
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(v);

        mName = (AutoCompleteTextView) findViewById(R.id.pName_editText);
        mDomain = (AutoCompleteTextView) findViewById(R.id.pDomain_editText);
        mLinks = (EditText) findViewById(R.id.pLinks_editText);
        mDesc = (AutoCompleteTextView) findViewById(R.id.pDesc_editText);
        mETC = (EditText) findViewById(R.id.pETC_editText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.action_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.create_button) {
            createProject();
        }
        return super.onOptionsItemSelected(item);
    }

    private void createProject() {
        String name = mName.getText().toString().trim();
        String domain = mDomain.getText().toString().trim();
        String links = mLinks.getText().toString().trim();
        String desc = mDesc.getText().toString().trim();
        String etc = mETC.getText().toString().trim();

        if(name.length()==0){
            mName.setError("Cannot leave empty");
            return;
        }
        if(domain.length()==0){
            mDomain.setError("Cannot leave empty");
            return;
        }
        if(desc.length()==0){
            mDesc.setError("Cannot leave empty");
            return;
        }

        try {
            jsonObject = new JSONObject();
            jsonObject.put("pName", name);
            jsonObject.put("pDomain", domain);
            jsonObject.put("pLinks", links);
            jsonObject.put("pDesc", desc);
            jsonObject.put("pETC", etc);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        SubmitAsyncTask.InformComplete mycallback = new SubmitAsyncTask.InformComplete() {
            @Override
            public void postData(String result) {
                mName.setText("");
                mDomain.setText("");
                mLinks.setText("");
                mDesc.setText("");
                mETC.setText("");
                //  Toast.makeText(getMyActivityContext(),"postDataWorks",Toast.LENGTH_SHORT).show();
                if(result.equals("success"))
                    NavUtils.navigateUpFromSameTask(getMyActivityContext());
            }
        };
        new SubmitAsyncTask(this, url, jsonObject, mycallback).execute();

    }
    private Activity getMyActivityContext(){
        return this;
    }

}
