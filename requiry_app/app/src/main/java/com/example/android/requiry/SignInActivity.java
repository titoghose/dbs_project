package com.example.android.requiry;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SignInActivity extends AppCompatActivity {

    private AutoCompleteTextView mEnterUsername;
    private EditText mEnterPassword;
    private Button mSignInButton;
    private Button mSignUpButton;
    private JSONArray jArray;
    private boolean valid;

    private int uId;
    private String uName;
    private long uNumber;
    private String uEmail;
    private String uUsername;
    private String uPassword;
    private int uWho;
    private String uDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.action_bar, null);
        TextView actionbar_title = (TextView) v.findViewById(R.id.action_bar_title);
        actionbar_title.setText("Sign In");
        assert actionBar != null;
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(v);


        mEnterUsername = (AutoCompleteTextView) findViewById(R.id.username);
        mEnterPassword = (EditText) findViewById(R.id.password);
        mSignInButton = (Button) findViewById(R.id.signIn_button);
        mSignUpButton = (Button) findViewById(R.id.signUp_button);

        SharedPreferences sp = getSharedPreferences("User", MODE_PRIVATE);
        if(sp.getString("uUsername","").length() != 0){
            Intent nextScreen = new Intent(SignInActivity.this, ProFeedActivity.class);
            startActivity(nextScreen);
        }

        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mEnterUsername.getText().toString().trim();
                String password = mEnterPassword.getText().toString().trim();


                JSONObject obj = new JSONObject();
                try {
                    obj.put("uUsername", username);
                    obj.put("uPassword", password);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                SubmitAsyncTask.InformComplete mycallback = new SubmitAsyncTask.InformComplete() {
                    @Override
                    public void postData(String result) {

                        if(result.equals("Failed"))
                            Toast.makeText(SignInActivity.this, "Incorrect Username or Password", Toast.LENGTH_SHORT).show();
                        else{
                            try {
                                JSONArray jsonArray = new JSONArray(result);
                                JSONObject obj = jsonArray.getJSONObject(0);
                                uId = obj.getInt("uID");
                                uName = obj.getString("uName");
                                uNumber = obj.getLong("uNumber");
                                uEmail = obj.getString("uEmail");
                                uUsername = obj.getString("uUsername");
                                uPassword = obj.getString("uPassword");
                                uWho = obj.getInt("uWho");
                                uDesc = obj.getString("uDesc");
                                Log.e("SignIn", uName);
                                Toast.makeText(SignInActivity.this, uName, Toast.LENGTH_SHORT).show();

                                SharedPreferences sharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putInt("uID", uId);
                                editor.putString("uName", uName);
                                editor.putLong("uNumber", uNumber);
                                editor.putString("uEmail", uEmail);
                                editor.putString("uUsername", uUsername);
                                editor.putString("uPassword", uPassword);
                                editor.putInt("uWho", uWho);
                                editor.putString("uDesc", uDesc);
                                editor.apply();
                                mEnterUsername.setText("");
                                mEnterPassword.setText("");

                                Intent nextScreen = new Intent(SignInActivity.this, ProFeedActivity.class);
                                startActivity(nextScreen);

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.e("fh", result);
                            }
                        }
                    }
                };
                String url = "http://192.168.43.19:5000/SignIn";
                new SubmitAsyncTask(SignInActivity.this, url, obj, mycallback).execute();

            }
        });

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEnterUsername.setText("");
                mEnterPassword.setText("");
                Intent moveToSignUp = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(moveToSignUp);
            }
        });
    }

}
