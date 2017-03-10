package com.example.android.requiry;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {
    TextView mUserPhotoTextView;
    TextView mUserNameTextView;
    TextView mUserWhoTextView;
    TextView mUserDescTextView;
    TextView mUserNumber;
    TextView mUserUsernameTextView;
    TextView mUserEmailTextView;
    ImageButton mEditProfileImageButton;
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
        final Bundle myBundle = getIntent().getExtras();
        mUserNameTextView.setText(myBundle.getString("uName"));
        if(myBundle.get("uName")!=null)
            mUserPhotoTextView.setText(""+myBundle.getString("uName").charAt(0));
        if(myBundle.get("uWho").equals("1"))
            mUserWhoTextView.setText("Faculty");
        else
            mUserWhoTextView.setText("Student");
        mUserNumber.setText(myBundle.getString("uNumber"));
        mUserUsernameTextView.setText(myBundle.getString("uUsername"));
        mUserEmailTextView.setText(myBundle.getString("uEmail"));
        mUserDescTextView.setText(myBundle.getString("uDesc"));
        mEditProfileImageButton = (ImageButton) findViewById(R.id.edit_profile);
        mEditProfileImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myBundle.putString("Title","EditProfile");
                Intent intent = new Intent(ProfileActivity.this,SignUpActivity.class).putExtras(myBundle);
                startActivity(intent);
            }
        });
    }
}