package com.whphy.enoticeboard.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.whphy.enoticeboard.R;

/**
 * Created by Jaykishan on 27/7/2017.
 */

public class FullNotif extends AppCompatActivity {

    String str_title,str_notif;

    TextView title,notif;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_notif_layout);

        getSupportActionBar().setTitle("Details");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        str_title = getIntent().getStringExtra("TITLE");
        str_notif = getIntent().getStringExtra("NOTIF");

        title = (TextView)findViewById(R.id.title_full);
        notif = (TextView)findViewById(R.id.notif_full);

        title.setText(str_title);
        notif.setText(str_notif);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
