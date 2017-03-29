package com.example.android.requiry;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {
    TextView mUserPhotoTextView;
    TextView mUserNameTextView;
    TextView mUserWhoTextView;
    TextView mUserDescTextView;
    TextView mUserNumber;
    TextView mUserUsernameTextView;
    TextView mUserEmailTextView;
    private String user_Identity;
    ImageButton mEditProfileImageButton;
    private Button mDeleteButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mUserPhotoTextView = (TextView) findViewById(R.id.user_profile_photo);
        mUserNameTextView = (TextView) findViewById(R.id.user_profile_name);
        mUserWhoTextView = (TextView) findViewById(R.id.user_who);
        mUserDescTextView = (TextView) findViewById(R.id.user_profile_desc);
        mUserNumber = (TextView) findViewById(R.id.user_profile_number);
        mUserUsernameTextView = (TextView) findViewById(R.id.user_profile_username);
        mUserEmailTextView = (TextView) findViewById(R.id.user_profile_email);
        mEditProfileImageButton = (ImageButton) findViewById(R.id.edit_profile);
        mDeleteButton = (Button) findViewById(R.id.deleteButton);
        final Bundle myBundle = getIntent().getExtras();
        user_Identity = myBundle.getString("identifier");
        Log.e("ProfileActivity",""+user_Identity);
        if(user_Identity.equals("notloggedInUser")) {
            mEditProfileImageButton.setVisibility(View.INVISIBLE);
            mDeleteButton.setVisibility(View.INVISIBLE);
            invalidateOptionsMenu();
        }
        else {
            mEditProfileImageButton.setVisibility(View.VISIBLE);
            mDeleteButton.setVisibility(View.VISIBLE);
        }
        mUserNameTextView.setText(myBundle.getString("uName"));
        if(myBundle.get("uName")!=null) {
            Log.e("ProfileActivity",myBundle.getString("uName"));
            mUserPhotoTextView.setText("" + myBundle.getString("uName").charAt(0));
        }
        if(myBundle.get("uWho").equals("1"))
            mUserWhoTextView.setText("Faculty");
        else
            mUserWhoTextView.setText("Student");
        mUserNumber.setText(myBundle.getString("uNumber"));
        mUserUsernameTextView.setText(myBundle.getString("uUsername"));
        mUserEmailTextView.setText(myBundle.getString("uEmail"));
        mUserDescTextView.setText(myBundle.getString("uDesc"));
        mEditProfileImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myBundle.putString("Title", "EditProfile");
                Intent intent = new Intent(ProfileActivity.this, SignUpActivity.class).putExtras(myBundle);
                startActivity(intent);
            }
        });
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("uUsername",myBundle.getString("uUsername"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                SubmitAsyncTask.InformComplete myCallback = new SubmitAsyncTask.InformComplete() {
                    @Override
                    public void postData(String result) {
                        if(result.equals("success")) {
                            Toast.makeText(ProfileActivity.this, "User Deleted", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ProfileActivity.this,SignInActivity.class);
                            NavUtils.navigateUpTo(ProfileActivity.this,intent);
                        }
                        else{
                            Toast.makeText(ProfileActivity.this,"Error Occurred",Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                String url = "http://192.168.43.19:5000/DeleteUser";
                new SubmitAsyncTask(ProfileActivity.this,url,jsonObject,myCallback).execute();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_activity_menu,menu);
        MenuItem item;
        if(user_Identity.equals("notloggedInUser")){
            item = menu.findItem(R.id.user_logout).setVisible(false);
        }
        else{
            item = menu.findItem(R.id.user_logout).setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.user_logout){
            SharedPreferences.Editor editor = getSharedPreferences("User",MODE_PRIVATE).edit();
            editor.clear(); //clear all stored data
            editor.apply();
            Intent intent = new Intent(ProfileActivity.this,SignInActivity.class);
            NavUtils.navigateUpTo(ProfileActivity.this,intent);
        }
        return true;
    }
}