package com.slmtek.mingseshop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class CategoriesList extends AppCompatActivity {

    // Declare Variable
    Button logout;
    ListView listView;
    ProgressDialog mProgressDialog;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from categories_list.xml
        setContentView(R.layout.categories_list);

        // Retrieve current user from Parse.com
        ParseUser currentUser = ParseUser.getCurrentUser();

        // Convert currentUser into String
        String struser = currentUser.getUsername();

        // Locate TextView in categories_list.xml
        TextView txtuser = (TextView) findViewById(R.id.txtuser);

        // Set the currentUser String into TextView
        txtuser.setText(struser);

        // Add progress dialog
        addProgressDialog();

        fetchLatestNews();

        // Fetch categories list from Parse.com with Async task (ParseQuery.findInBackground)
        // and the callback will run on the main thread
        fetchCategories();

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

    // Add and show progress dialog
    private void addProgressDialog () {

        // Create a progress dialog
        mProgressDialog = new ProgressDialog(CategoriesList.this);

        // Set progress dialog title
        mProgressDialog.setTitle(getString(R.string.app_name));

        // Set progress dialog message
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setIndeterminate(false);

        // Show progress dialog
        mProgressDialog.show();

    }

    private void fetchLatestNews() {
        ParseQuery<ParseObject> query = new ParseQuery<>("Notice");
        query.whereEqualTo("order",1);
        query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {

                    TextView latestNews = (TextView) findViewById(R.id.txtlatestnewscontent);
                    latestNews.setText((String) list.get(0).get("content"));
                } else {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

    private void fetchCategories() {
        // Load all categories
        // Locate the class table named "Country" in Parse.com and cache them
        ParseQuery<ParseObject> query = new ParseQuery<>(
                "Category");
        query.orderByAscending("order");
        query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(final List<ParseObject> list, ParseException e) {
                if (e == null) {

                    // Locate the list view in categories_list.xml
                    listView = (ListView) findViewById(R.id.listViewCategories);

                    // Pass the results into a ArrayAdapter
                    adapter = new ArrayAdapter<>(CategoriesList.this,R.layout.category_item);

                    // Retrieve object "Category" from parse.com database
                    for (ParseObject cat : list) {
                        adapter.add((String) cat.get("title"));
                    }

                    // Binds the Adapter to the ListView
                    listView.setAdapter(adapter);

                    // Close the progress dialog
                    mProgressDialog.dismiss();

                    // Capture clicks on listView items
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            // Send category id to ProductListActivity Class
                            Intent i = new Intent(CategoriesList.this, ProductsList.class);
                            i.putExtra("name", list.get(position).getString("categoryIdentifier"));
                            startActivity(i);

                        }
                    });

                } else {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
            }

        });
    }

}
