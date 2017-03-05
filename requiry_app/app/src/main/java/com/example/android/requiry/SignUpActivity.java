package com.example.android.requiry;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import org.json.JSONObject;

public class SignUpActivity extends AppCompatActivity{
    private EditText mNameEditText;
    private EditText mNumberEditText;
    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private EditText mDescEditText;
    private final String url = "http://192.168.43.19:5000/SignUp";//Add your Sign Up url here
    private RadioGroup radioGroup;
    JSONObject jsonObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);



        mNameEditText = (EditText) findViewById(R.id.input_nameEditText);
        mNumberEditText = (EditText) findViewById(R.id.input_numberEditText);
        mUsernameEditText = (EditText) findViewById(R.id.input_usernameEditText);
        mPasswordEditText = (EditText) findViewById(R.id.input_passwordEditText);
        mDescEditText = (EditText) findViewById(R.id.input_userdesc);
        radioGroup = (RadioGroup) findViewById(R.id.whoradiogroup);
    }
    public void registerUser(View view){
        Integer number = null;
        String name = mNameEditText.getText().toString().trim();
        if(!TextUtils.isEmpty(mNumberEditText.getText().toString().trim()))
            number  = Integer.parseInt(mNumberEditText.getText().toString());

        String username = mUsernameEditText.getText().toString().trim();
        String password = mPasswordEditText.getText().toString().trim();
        String desc = mDescEditText.getText().toString().trim();
        if(TextUtils.isEmpty(name)){
            mNameEditText.requestFocus();
            mNameEditText.setError("This field is required");
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
        int who;
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
            jsonObject.put("uname", name);
            jsonObject.put("unumber", number);
            jsonObject.put("uusername", username);
            jsonObject.put("upassword", password);
            jsonObject.put("uwho", who);
            jsonObject.put("udesc", desc);
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
                //  Toast.makeText(getMyActivityContext(),"postDataWorks",Toast.LENGTH_SHORT).show();
                if(result.equals("success"))
                    NavUtils.navigateUpFromSameTask(getMyActivityContext());
            }
        };
        new SubmitAsyncTask(this,url,jsonObject,mycallback).execute();

    }
    private Activity getMyActivityContext(){
        return this;
    }
}
