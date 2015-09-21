package com.slmtek.mingseshop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class ProductsList extends AppCompatActivity {

    ListView listView;
    ProgressDialog mProgressDialog;
    ArrayAdapter<String> adapter;
    TextView catName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_list);

        // Allow Up navigation with the app icon in the action bar
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        catName = (TextView) findViewById(R.id.catName);
        catName.setText(getIntent().getStringExtra("name"));

        // Add Progress Dialog
        addProgressDialog();

        // Load Products under current category
        allProductsFromCategory(getIntent().getStringExtra("name"));

    }

    // Add and show progress dialog
    private void addProgressDialog () {

        // Create a progress dialog
        mProgressDialog = new ProgressDialog(ProductsList.this);

        // Set progress dialog title
        mProgressDialog.setTitle(getString(R.string.app_name));

        // Set progress dialog message
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setIndeterminate(false);

        // Show progress dialog
        mProgressDialog.show();

    }

    private void allProductsFromCategory(String catId) {

        // Locate the class table named "Product" in Parse.com and cache them
        ParseQuery<ParseObject> query = new ParseQuery<>(
                "Product");

        //query.whereEqualTo("order", 1);
        //query.orderByAscending("order");
        //query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(final List<ParseObject> list, ParseException e) {
                if (e == null) {
                    Log.e("Error"," " + list.size());
                    // Locate the list view in categories_list.xml
                    listView = (ListView) findViewById(R.id.productList);

                    // Pass the results into a ArrayAdapter
                    adapter = new ArrayAdapter<>(ProductsList.this, R.layout.product_item);

                    // Retrieve object "Product" from parse.com database
                    for (ParseObject product : list) {
                        adapter.add((String) product.get("title"));
                    }

                    // Binds the Adapter to the ListView
                    listView.setAdapter(adapter);

                    // Close the progress dialog
                    mProgressDialog.dismiss();

                    /*
                    // Capture clicks on listView items
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            // Send category id to ProductListActivity Class
                            Intent i = new Intent(CategoriesList.this, ProductsList.class);
                            i.putExtra("name", list.get(position).getString("categoryIdentifier"));
                            startActivity(i);

                        }
                    });*/

                } else {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
            }

        });
    }


}
