package com.slmtek.mingseshop;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

/**
 * Created by ke on 01/09/15.
 *
 * This is the authentication class to identify the app using Parse.com
 */
public class ParseApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        // Add your initialisation code here
        Parse.initialize(this, "", "");

        ParseUser.enableAutomaticUser();


    }
}
