package me.danieldobalian.represent;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.content.*;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class zipCode extends AppCompatActivity {

    Button inputButton;
    EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zip_code);

        // used http://android-er.blogspot.com/2012/02/apply-animation-on-button.html
        // to help perform button animations
        final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);

        inputButton = (Button) findViewById(R.id.zipInput);
        input = (EditText)findViewById(R.id.zipEnter);

        inputButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Gets data from the field
                String inputZip = input.getText().toString();

                Intent myIntent;

                // Make sure zip length is not less than 5
                if (inputZip.length() != 5) {
                    myIntent = new Intent(zipCode.this,
                            zipCode.class);

                } else {
                    // prepares screen to change
                    myIntent = new Intent(zipCode.this,
                            RepView.class);
                    myIntent.putExtra("inputCode", inputZip);
                }

                // button animation
                v.startAnimation(animAlpha);

                // transition screens
                startActivity(myIntent);
            }
        });
    }

}
