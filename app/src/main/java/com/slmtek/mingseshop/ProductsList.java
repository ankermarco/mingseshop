package com.slmtek.mingseshop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ProductsList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_list);

        // Allow Up navigation with the app icon in the action bar
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView txtCatName = (TextView) findViewById(R.id.catName);
        txtCatName.setText(getIntent().getStringExtra("name"));
    }

}
