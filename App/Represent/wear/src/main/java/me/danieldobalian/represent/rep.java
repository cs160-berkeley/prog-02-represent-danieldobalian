package me.danieldobalian.represent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.support.wearable.view.WearableListView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;
import android.content.res.Resources;
import android.support.wearable.view.DotsPageIndicator;
import android.support.wearable.view.GridViewPager;
import android.view.View.OnApplyWindowInsetsListener;
import android.view.WindowInsets;

public class rep extends Activity {

    private TextView mTextView;
    // private Button mSelect;
    private Button mElectionData;
    private Button mDetailedButton;
    private WearableListView varList;
    private String[] names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rep);


        // Shake Code
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;

        mDetailedButton = (Button) findViewById(R.id.detailedButton);
        mElectionData = (Button) findViewById(R.id.electionData);
        final Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        final String inputCode;

        if (extras != null) {
            inputCode = intent.getStringExtra("inputCode");
        }

        /* API CALL */
        /* Use Zip Code to Query API for Names, 2012 Data */

        // Setting up the listview
        // Using temporary data
        names = new String[]{"Senator Dianne Feinstein (D)", "Senator Dianne Feinstein (D)",
                "Senator Dianne Feinstein (D)"};

        final Resources res = getResources();
        final GridViewPager pager = (GridViewPager) findViewById(R.id.pager);
        pager.setOnApplyWindowInsetsListener(new OnApplyWindowInsetsListener() {
            @Override
            public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
                final boolean round = insets.isRound();
                int rowMargin = res.getDimensionPixelOffset(R.dimen.page_row_margin);
                int colMargin = res.getDimensionPixelOffset(round ?
                        R.dimen.page_column_margin_round : R.dimen.page_column_margin);
                pager.setPageMargins(rowMargin, colMargin);
                pager.onApplyWindowInsets(insets);

                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub

                    Log.v("T", "Detailed button clicked");
                    Intent sendIntent = new Intent(getBaseContext(), WatchToPhoneService.class);
                    sendIntent.putExtra("command", "detailed");
                    sendIntent.putExtra("data", "Senator Dianne Feinstein (D)");
                    startService(sendIntent);
                    Log.v("T", "called start service");
                    }
                });

                return insets;
            }
        });
        pager.setAdapter(new SampleGridPagerAdapter(this, getFragmentManager()));
        DotsPageIndicator dotsPageIndicator = (DotsPageIndicator) findViewById(R.id.page_indicator);
        dotsPageIndicator.setPager(pager);


        mElectionData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.v("T", "Clicked on election 2012 data button");
                Intent election = new Intent(rep.this,
                        electionData.class);
                election.putExtra("inputCode", intent.getStringExtra("inputCode"));
                Log.v("T", "Created Intent");
                startActivity(election);
            }

        });

        mDetailedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.v("T", "Detailed button clicked");
                Intent sendIntent = new Intent(getBaseContext(), WatchToPhoneService.class);
                sendIntent.putExtra("command", "detailed");

                /* API: Will send index of one picked */
                sendIntent.putExtra("data", "Senator Dianne Feinstein (D)");
                startService(sendIntent);
                Log.v("T", "called start service");
            }

        });
    }

    SensorManager mSensorManager;
    float mAccel;
    float mAccelCurrent;
    float mAccelLast;

    final SensorEventListener mSensorListener = new SensorEventListener() {

        public void onSensorChanged(SensorEvent se) {
            float x = se.values[0];
            float y = se.values[1];
            float z = se.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt((double) (x*x + y*y + z*z));
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta;

            if (mAccel > 12) {
                Toast toast = Toast.makeText(getApplicationContext(), "Device has shaken.", Toast.LENGTH_LONG);
                toast.show();

                Log.v("T", "Shaking");
                Intent election = new Intent(rep.this,
                        rep.class);

                /* API Generate random zip code */

                Random gen = new Random();
                String newInput = "928" + Integer.toString(gen.nextInt(10))
                        + Integer.toString(gen.nextInt(10));

                /* Tell phone to do a new search */
                Intent sendIntent = new Intent(getBaseContext(), WatchToPhoneService.class);
                sendIntent.putExtra("command", "new");
                sendIntent.putExtra("data", newInput);
                startService(sendIntent);

                // Change watch screens
                election.putExtra("inputCode", newInput);
                Log.v("T", "Created shaking Intent");
                startActivity(election);

            }
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mSensorListener);
        super.onPause();
    }
}
