package com.example.android.requiry;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import org.json.JSONObject;

public class SignUpActivity extends AppCompatActivity{
    private EditText mNameEditText;
    private EditText mNumberEditText;
    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private EditText mDescEditText;
    private EditText mEmailEditText;
    private Bundle myBundle;
    private Button mRegisterButton;
    private final String urlSignUp = "http:///SignUp";//TODO Add your Sign Up url here
    private final String urlEditProfile = "http:///EditProfile";//TODO Add your Edit Profile url here
    private RadioGroup radioGroup;
    private boolean flag;

    private long number;
    private String name;
    private  String email;
    private String username;
    private String password;
    private String desc;
    private int who;
    JSONObject jsonObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        flag = false;
        mRegisterButton = (Button) findViewById(R.id.registerButton);
        mNameEditText = (EditText) findViewById(R.id.input_nameEditText);
        mNumberEditText = (EditText) findViewById(R.id.input_numberEditText);
        mUsernameEditText = (EditText) findViewById(R.id.input_usernameEditText);
        mPasswordEditText = (EditText) findViewById(R.id.input_passwordEditText);
        mDescEditText = (EditText) findViewById(R.id.input_userdesc);
        radioGroup = (RadioGroup) findViewById(R.id.whoradiogroup);
        mEmailEditText = (EditText) findViewById(R.id.input_emailEditText);
        Intent intent = getIntent();
        myBundle = intent.getExtras();
        if(myBundle!=null){
            mRegisterButton.setText("Save");
            this.setTitle("Edit Profile");
            mNameEditText.setText(myBundle.getString("uName"));
            mNumberEditText.setText(myBundle.getString("uNumber"));
            mUsernameEditText.setText(myBundle.getString("uUsername"));
            mPasswordEditText.setText(myBundle.getString("uPassword"));
            mDescEditText.setText(myBundle.getString("uDesc"));
            int who = Integer.parseInt(myBundle.getString("uWho"));
            if(who==1){
                radioGroup.check(R.id.faculty_radiobutton);
            }
            else
                radioGroup.check(R.id.student_radiobutton);
            mEmailEditText.setText(myBundle.getString("uEmail"));
            flag = true;
        }
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag)
                    registerUser(urlEditProfile);
                else
                    registerUser(urlSignUp);
            }
        });

    }
    public void registerUser(String url){
        name = mNameEditText.getText().toString().trim();
        if(!TextUtils.isEmpty(mNumberEditText.getText().toString().trim()))
            number  = Long.parseLong(mNumberEditText.getText().toString());
        email = mEmailEditText.getText().toString().trim();
        username = mUsernameEditText.getText().toString().trim();
        password = mPasswordEditText.getText().toString().trim();
        desc = mDescEditText.getText().toString().trim();
        if(TextUtils.isEmpty(name)){
            mNameEditText.requestFocus();
            mNameEditText.setError("This field is required");
            return;
        }
        if(TextUtils.isEmpty(email)){
            mEmailEditText.requestFocus();
            mEmailEditText.setError("This field is required");
            return;
        }
        if(TextUtils.isEmpty(username)){
            mUsernameEditText.requestFocus();
            mUsernameEditText.setError("This field is required");
            return;
        }
        if(TextUtils.isEmpty(password)){
            mPasswordEditText.requestFocus();
            mPasswordEditText.setError("This field is required");
            return;
        }
        /**
         * Radio Group
         * if the user is Student then in database it is stored as 0
         * if the user is Faculty then in database it is stored as 1
         */

        int selectedId = radioGroup.getCheckedRadioButtonId();
        if(selectedId==R.id.student_radiobutton)
            who = 0;
        else
            who=1;
        try {
            jsonObject = new JSONObject();
            jsonObject.put("uName", name);
            jsonObject.put("uNumber", number);
            jsonObject.put("uEmail",email);
            jsonObject.put("uUsername", username);
            jsonObject.put("uPassword", password);
            jsonObject.put("uWho", who);
            jsonObject.put("uDesc", desc);
        }
        catch (Exception e){
            Log.e("SignUpActivity",""+e);
        }
        SubmitAsyncTask.InformComplete mycallback = new SubmitAsyncTask.InformComplete() {
            @Override
            public void postData(String result) {
                mNameEditText.setText("");
                mNumberEditText.setText("");
                mUsernameEditText.setText("");
                mPasswordEditText.setText("");
                mDescEditText.setText("");
                mEmailEditText.setText("");
                if(result.equals("success")) {
                    if(myBundle!=null){
                        SharedPreferences sp = getSharedPreferences("User", MODE_PRIVATE);
                        if(sp!=null){
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("uName", name);
                            editor.putLong("uNumber", number);
                            editor.putString("uEmail", email);
                            editor.putString("uUsername", username);
                            editor.putString("uPassword", password);
                            editor.putInt("uWho", who);
                            editor.putString("uDesc", desc);
                            editor.apply();
                        }
                        finish();
                    }
                    else
                        NavUtils.navigateUpFromSameTask(getMyActivityContext());
                }
            }
        };
        new SubmitAsyncTask(this,url,jsonObject,mycallback).execute();

    }
    private Activity getMyActivityContext(){
        return this;
    }
}
