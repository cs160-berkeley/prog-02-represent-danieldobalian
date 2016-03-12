package me.danieldobalian.represent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;
import java.util.Random;
import android.support.wearable.view.WearableListView;
import android.widget.Toast;
import android.hardware.*;
import android.content.Context;

/**
 * Created by danieldobalian on 3/3/16.
 */
public class MainActivity extends Activity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "bZ6DqFrbyDodvUTQGAHHWKZ3d";
    private static final String TWITTER_SECRET = "bvEZQMx4Y3hbe3ACxv1k2RdRDvtBJaavmRmVLp6LyQNzblYbDK";


    private TextView mTextView;
    // private Button mSelect;
    private WearableListView varList;
    private String[] names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_start_wear_page);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            Log.v("v", "Inside MainAct: " + extras.getString("data"));
            String data = extras.getString("data");
            Intent myIntent = new Intent(MainActivity.this,
                    rep.class);
            myIntent.putExtra("data", data);
            startActivity(myIntent);
        }

    }
}