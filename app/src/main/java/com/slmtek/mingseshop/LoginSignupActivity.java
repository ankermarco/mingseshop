package com.slmtek.mingseshop;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

/**
 * Created by ke on 01/09/15.
 */
public class LoginSignupActivity extends AppCompatActivity {

    // Declare Variables
    Button loginButton;
    Button signup;
    String usernameTxt;
    String passwdTxt;
    EditText passwd;
    EditText username;
    TextView networkNotice;

    private BroadcastReceiver myBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService( Context.CONNECTIVITY_SERVICE );

                NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();

                if (activeNetInfo != null &&  activeNetInfo.isConnected()){
                    networkNotice.setText("");
                    loginButton.setEnabled(true);
                } else {
                    networkNotice.setText("Check your network");
                    loginButton.setEnabled(false);
                }
            }

        };

    public void onResume() {
        super.onResume();
        registerReceiver(myBroadcastReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }

    public void onPause() {
        super.onPause();
        unregisterReceiver(myBroadcastReceiver);
    }



    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the view from loginsignup.xml
        setContentView(R.layout.loginsignup);

        networkNotice = (TextView) findViewById(R.id.networknotice);

        // Locate EditTexts in loginsignup.xml
        username = (EditText) findViewById(R.id.username);
        passwd = (EditText) findViewById(R.id.password);

        // Locate Buttons in loginsignup.xml
        loginButton = (Button) findViewById(R.id.login);
        //signup = (Button) findViewById(R.id.signup);

        // Login Button Click Listener
        loginButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // Retrieve the text entered from the EditText
                usernameTxt = username.getText().toString();
                passwdTxt = passwd.getText().toString();

                // Send data to Parse.com for verification
                ParseUser.logInInBackground(usernameTxt, passwdTxt,
                        new LogInCallback() {
                            public void done(ParseUser user, ParseException e) {
                                if (user != null) {
                                    // If user exist and authenticated, send user to Welcome.class
                                    Intent intent = new Intent(
                                            LoginSignupActivity.this,
                                            CategoriesList.class);
                                    startActivity(intent);
                                    Toast.makeText(getApplicationContext(),
                                            "Successfully Logged in",
                                            Toast.LENGTH_LONG).show();
                                    finish();
                                } else {
                                    Toast.makeText(
                                            getApplicationContext(),
                                            getString(R.string.UserNotExist),
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });

    }
}
