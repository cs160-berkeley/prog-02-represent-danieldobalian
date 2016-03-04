package me.danieldobalian.represent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.Button;
import android.content.*;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import java.util.Random;

public class StartPage extends AppCompatActivity {


    Button zipButton;
    Button locationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                Intent myIntent = new Intent(StartPage.this,
                        RepView.class);

                /* Replace with API Call for Current Location */
                Random randomGenerator = new Random();
                String zipCode = "9286" + Integer.toString(randomGenerator.nextInt(10));
                myIntent.putExtra("inputCode", zipCode);

                v.startAnimation(animAlpha);
                startActivity(myIntent);
            }
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
