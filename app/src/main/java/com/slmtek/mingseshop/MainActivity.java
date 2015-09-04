package com.slmtek.mingseshop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;

import com.parse.ParseAnonymousUtils;
import com.parse.ParseUser;

/**
 * Created by ke on 01/09/15.
 *
 * In this Activity, we try to determine whether the current user is an anonymous user and
 * if the user has previously logged into the application, the user will be automatically
 * converted into a regular user.
 *
 * Every signup and login methods will cache the user data on disk. But atm no public signup yet!
 * Caching user's data on disk will prevent a regular user to login every time they open the application.
 *
 * To clear a regular user cached data will require calling "ParseUser.logOut()" and an anonymous user
 * will be sent to the LoginSignupActivity.java
 *
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Determine whether the current user is an anonymous user
        if (ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())) {
            // If the user is anonymous, send the user to LoginSignupActivity.class

            Intent intent = new Intent(MainActivity.this, LoginSignupActivity.class);
            startActivity(intent);
            finish();
        } else {
            // If current user is NOT anonymous user
            // Get current user data from Parse.com
            ParseUser currentUser = ParseUser.getCurrentUser();
            if (currentUser != null) {
                // Send logged in users to CategoriesList.class
                Intent intent = new Intent(MainActivity.this, CategoriesList.class);
                startActivity(intent);
                finish();
            } else {
                // Send user to LoginSignupActivity.class
                Intent intent = new Intent(MainActivity.this, LoginSignupActivity.class);
                startActivity(intent);
                finish();
            }
        }

    }

}
