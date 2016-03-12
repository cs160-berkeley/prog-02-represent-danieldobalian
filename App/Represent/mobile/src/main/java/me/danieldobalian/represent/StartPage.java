package me.danieldobalian.represent;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.*;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.util.Random;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.*;
import com.twitter.sdk.android.core.models.User;

import java.util.List;


public class StartPage extends AppCompatActivity implements KeyEvent.Callback {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "PYUNfx352ExFdF9CYMn6BedkU";
    private static final String TWITTER_SECRET = "rUQlX2rR0yxNdGX2USrwSEzJddvchVcdzfG05oXLR7HGxXd3MD";


    Button zipButton;
    Button locationButton;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_start_page);

        // used http://android-er.blogspot.com/2012/02/apply-animation-on-button.html
        // to help perform button animations
        final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);

        zipButton = (Button) findViewById(R.id.zipButton);
        locationButton = (Button) findViewById(R.id.locationButton);

        zipButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(StartPage.this,
                        zipCode.class);

                v.startAnimation(animAlpha);
                startActivity(myIntent);
            }
        });

        locationButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                /* Replace with API Call for Current Location */
                Intent myIntent = new Intent(StartPage.this, CurLoc.class);
                v.startAnimation(animAlpha);
                startActivity(myIntent);
            }

//                Random randomGenerator = new Random();
//                String zipCode = "9286" + Integer.toString(randomGenerator.nextInt(10));
//                myIntent.putExtra("inputCode", zipCode);
//
//                v.startAnimation(animAlpha);
//                startActivity(myIntent);
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
