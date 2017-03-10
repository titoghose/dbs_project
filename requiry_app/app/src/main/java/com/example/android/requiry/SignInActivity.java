package com.example.android.requiry;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class SignInActivity extends AppCompatActivity {

    private AutoCompleteTextView mEnterUsername;
    private EditText mEnterPassword;
    private Button mSignInButton;
    private Button mSignUpButton;
    private JSONArray jArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.action_bar, null);
        TextView actionbar_title = (TextView)v.findViewById(R.id.action_bar_title);
        actionbar_title.setText("Sign In");
        assert actionBar != null;
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(v);


        mEnterUsername = (AutoCompleteTextView) findViewById(R.id.username);
        mEnterPassword = (EditText) findViewById(R.id.password);
        mSignInButton = (Button) findViewById(R.id.signIn_button);
        mSignUpButton = (Button) findViewById(R.id.signUp_button);

        mSignInButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(validUsername() && validPassword()){
                    Intent moveToProFeed = new Intent(SignInActivity.this, ProFeedActivity.class);
                    startActivity(moveToProFeed);
                }
                else{
                    Toast.makeText(SignInActivity.this, "Incorrect Username or Password", Toast.LENGTH_SHORT).show();
                }

            }
        });

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveToSignUp = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(moveToSignUp);
            }
        });
    }

    private boolean validUsername() {
        final String email = String.valueOf(mEnterUsername.getText());
        jArray = new JSONArray();
        return true;
    }
    private boolean validPassword() {
        final String password = String.valueOf(mEnterPassword.getText());

        return true;
    }
}
