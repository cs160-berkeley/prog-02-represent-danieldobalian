package me.danieldobalian.represent;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Button;

public class Detailed extends AppCompatActivity {

    Button lessButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);

        final Intent prevIntent = getIntent();

        // Sets Image
        int pic = prevIntent.getIntExtra("picture", -1);

        // Checks if image came through, otherwise "query" for it in API
        if (pic != -1) {
            ImageView img = (ImageView) findViewById(R.id.detailedImage);
            img.setImageResource(pic);
        } else {
            ImageView img = (ImageView) findViewById(R.id.detailedImage);
            img.setImageResource(R.drawable.rep);
        }

        String name = prevIntent.getStringExtra("name");

        // Text view formatting

        // Sets name of person
        TextView names=(TextView) findViewById(R.id.nameView);
        names.setText(name, TextView.BufferType.SPANNABLE);

        /* Perform API call here w/ zip or do it in previous screen */

        // Background
        TextView background=(TextView) findViewById(R.id.background);
        background.setText("Background", TextView.BufferType.SPANNABLE);

        TextView backgroundBody=(TextView) findViewById(R.id.backgroundBody);
        backgroundBody.setText("Democratic Party\nTerm Conclusion 2016",
                TextView.BufferType.SPANNABLE);

        // Com's
        TextView coms=(TextView) findViewById(R.id.com);
        coms.setText("Committees", TextView.BufferType.SPANNABLE);

        TextView comsBody=(TextView) findViewById(R.id.comBody);
        comsBody.setText("Appropiations\nJudiciary\nRules and Admin", TextView.BufferType.SPANNABLE);

        // Bills
        TextView bills=(TextView) findViewById(R.id.bills);
        bills.setText("Bills", TextView.BufferType.SPANNABLE);

        TextView billsBody=(TextView) findViewById(R.id.billsBody);
        billsBody.setText("S. 2552: a bill to amend section 875(c) of title\n 18, US Code to ...",
                TextView.BufferType.SPANNABLE);


        lessButton = (Button) findViewById(R.id.lessButton);

        lessButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Detailed.this,
                        RepView.class);

                myIntent.putExtra("inputCode", prevIntent.getStringExtra("inputCode"));

                v.startAnimation(animAlpha);
                startActivity(myIntent);
            }
        });
    }

}
