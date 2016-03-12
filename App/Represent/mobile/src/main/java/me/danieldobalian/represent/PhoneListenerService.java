package me.danieldobalian.represent;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;

/**
 * Created by danieldobalian on 3/3/16.
 */
public class PhoneListenerService extends WearableListenerService {

    //   WearableListenerServices don't need an iBinder or an onStartCommand: they just need an onMessageReceieved.
    private static final String DETAILED = "/detailed";
    private static final String NEW = "/new";

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.v("T", "in PhoneListenerService, got: " + messageEvent.getPath());
        if( messageEvent.getPath().equalsIgnoreCase(DETAILED) ) {

            // Value contains the String we sent over in WatchToPhoneService, "good job"
            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);

            // Make a toast with the String
            Context context = getApplicationContext();

            /* Use Value to launch the intent of detailed*/
            Intent intent = new Intent(this, Detailed.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //you need to add this flag since you're starting a new activity from a service

            String delims = "[|]";
            String [] splitData = value.split(delims);

            intent.putExtra("name", splitData[0]);
            intent.putExtra("party", splitData[1]);
            intent.putExtra("term", splitData[2]);
            intent.putExtra("bioID", splitData[3]);

            startActivity(intent);

        } else if (messageEvent.getPath().equalsIgnoreCase(NEW)){
            // Value contains the String we sent over in WatchToPhoneService, "good job"
            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);

            // Make a toast with the String
            Context context = getApplicationContext();

                        /* Use Value to launch the intent of rep*/
            Intent intent = new Intent(this, RepView.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //you need to add this flag since you're starting a new activity from a service

            /* Place holder so Java doesn't complain */

            String randomZip = "92867";
            try {
                InputStream is = context.getAssets().open("zip.txt");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                String temp = new String(buffer, "UTF-8");
                String[] zips = temp.split("\\s+");

                Random rand = new Random();
                randomZip = zips[rand.nextInt(zips.length)];
                Log.v("v", "Random Zip: " + randomZip);
            } catch (Exception e) {
                e.printStackTrace();
            }
            while(randomZip.length() != 5) {
                randomZip = "0" + randomZip;
            }
            intent.putExtra("inputCode", randomZip);

            startActivity(intent);

        } else
        {
            super.onMessageReceived( messageEvent );
        }

    }
}
