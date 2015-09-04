package com.slmtek.mingseshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.ParseUser;

/**
 * Created by ke on 01/09/15.
 */
public class CategoriesList extends AppCompatActivity {

    // Declare Variable
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from categories_list.xml
        setContentView(R.layout.categories_list);

        // Retrieve current user from Parse.com
        ParseUser currentUser = ParseUser.getCurrentUser();

        // Convert currentUser into String
        String struser = currentUser.getUsername().toString();

        // Locate TextView in categories_list.xml
        TextView txtuser = (TextView) findViewById(R.id.txtuser);

        // Set the currentUser String into TextView
        txtuser.setText(struser);

        // Locate Button in categories_list.xml
        logout = (Button) findViewById(R.id.logout);

        // Logout button click listener
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Logout current user
                ParseUser.logOut();
                startActivity(new Intent(v.getContext(),MainActivity.class));
            }
        });

    }
}
