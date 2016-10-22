package com.example.kanchicoder.movietalk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by kanchicoder on 10/22/2016.
 */

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.detail_container, new DetailActivityFragment())
                    .commit();
        }
    }
}
