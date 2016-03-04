package me.danieldobalian.represent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Random;
import android.support.wearable.view.WearableListView;
import android.widget.Toast;
import android.hardware.*;
import android.content.Context;

/**
 * Created by danieldobalian on 3/3/16.
 */
public class MainActivity extends Activity {

    private TextView mTextView;
    // private Button mSelect;
    private WearableListView varList;
    private String[] names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_wear_page);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null) {
            String inputCode = extras.getString("inputCode");
            Intent myIntent = new Intent(MainActivity.this,
                    rep.class);
            myIntent.putExtra("inputCode", inputCode);
            startActivity(myIntent);
        }

    }
}