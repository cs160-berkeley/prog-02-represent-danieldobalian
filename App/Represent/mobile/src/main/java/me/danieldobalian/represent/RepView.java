package me.danieldobalian.represent;

import java.util.ArrayList;
import java.net.*;
import java.io.*;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.content.*;
import android.widget.ListView;
import android.widget.TextView;

import com.twitter.sdk.android.core.AppSession;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class RepView extends AppCompatActivity {

    ListView lv;
    Context context;
    RepView tempThis;

    ArrayList prgmName;
    public static String [] names={"Senator Dianne Feinstein (D)", "Senator Dianne Feinstein (D) ",
            "Senator Dianne Feinstein (D)","Senator Dianne Feinstein (D)"};
    public static String [] party = {"D", "D", "D", "D"};
    public static String [] website={"http://feinstein.senate.gov", "http://feinstein.senate.gov",
            "http://feinstein.senate.gov","http://feinstein.senate.gov"};
    public static String [] email={"dianne@feinstein.senate.gov", "dianne@feinstein.senate.gov",
            "dianne@feinstein.senate.gov","dianne@feinstein.senate.gov"};
    public static String [] tweets={"Ill", "Ill be on tv...", "Ill be on tv...","Ill be on tv..."};
    public static String [] bioID={"L000551", "L000551", "L000551","L000551"};
    public static String [] term={"Jan 2017", "Jan 2017", "Jan 2017","Jan 2017"};
    public static String county = "Temp";
    public static String state = "Temp";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rep_view);

        // Dont do below if coming from watch
        context=this;
        tempThis = this;
        lv=(ListView) findViewById(R.id.listView);

        /* County Locator */
        CountyGet obj = new CountyGet();
        obj.execute();
    }

    public class CountyGet extends AsyncTask<Void, Void, Void> {

        StringBuilder sb;
        BufferedReader br;

        @Override
        protected Void doInBackground(Void... params) {
        /* Sunlight API Call */
            StringBuilder urlString =
                    new StringBuilder("http://maps.google.com/maps/api/geocode/json?");

            String inputCode = getIntent().getStringExtra("inputCode");

                /* Check if we need to query lat/long or zip */
            try {
                if (inputCode.equals("-1")) {
                    String lat = getIntent().getStringExtra("lat");
                    String mLong = getIntent().getStringExtra("mLong");
                    urlString.append("latlng=" + lat + "," + mLong);
                } else {
                    urlString.append("address=" +
                        URLEncoder.encode(getIntent().getStringExtra("inputCode"), "utf8"));
                }
                urlString.append("&sensor=false");

                java.net.URL url = new URL(urlString.toString());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();
                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb = sb.append(line + "\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            JSONObject data = null;
            String sBills = "";

            try {
                data = new JSONObject(sb.toString());
                Log.v("v", "Data: " + data);

                JSONArray arr = null;
                JSONArray Results = data.getJSONArray("results");
                JSONObject zero = Results.getJSONObject(0);
                JSONArray address_components = zero.getJSONArray("address_components");

                for (int i = 0; i < address_components.length(); i++) {
                    JSONObject zero2 = address_components.getJSONObject(i);
                    String long_name = zero2.getString("long_name");
                    String short_name = zero2.getString("short_name");
                    JSONArray mtypes = zero2.getJSONArray("types");
                    String Type = mtypes.getString(0);

                    if (!TextUtils.isEmpty(long_name) || !long_name.equals(null)
                            || long_name.length() > 0 || !long_name.equals("")) {
                        if (Type.equalsIgnoreCase("administrative_area_level_2")) {
                            tempThis.county = long_name;
                            Log.v("v", "County: " + tempThis.county);
                        }
                    }
                    if (!TextUtils.isEmpty(short_name) || !short_name.equals(null)
                            || short_name.length() > 0 || !short_name.equals("")) {
                        if (Type.equalsIgnoreCase("administrative_area_level_1")) {
                            tempThis.state = short_name;
                            Log.v("v", "State: " + tempThis.state);
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            /* Sunlight Dataset */
            SunlightData obj = new SunlightData();
            obj.execute();

            super.onPostExecute(aVoid);
        }
    }


    public class SunlightData extends AsyncTask<Void, Void, Void> {

        StringBuilder sb;
        BufferedReader br;

        @Override
        protected Void doInBackground(Void... params) {
        /* Sunlight API Call */
            String urlQuery = "http://congress.api.sunlightfoundation.com/legislators/locate?"
                    + "apikey=0b8c7293dcdd4cf6bded1a982fbac946";
            StringBuilder urlStringBuilder = new StringBuilder(urlQuery);

            try {
                String inputCode = getIntent().getStringExtra("inputCode");

                /* Check if we need to query lat/long or zip */
                if (inputCode.equals("-1")) {
                    String lat = getIntent().getStringExtra("lat");
                    String mLong = getIntent().getStringExtra("mLong");
                    urlStringBuilder.append("&latitude=" + lat + "&longitude=" + mLong);
                } else {
                    urlStringBuilder.append("&zip=" +
                            URLEncoder.encode(getIntent().getStringExtra("inputCode"), "utf8"));
                }

                String URL = urlStringBuilder.toString();
                Log.v("v", "URL: " + URL);

                java.net.URL url = new URL(URL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();
                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb = sb.append(line + "\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            JSONObject data = null;

            try {
                data = new JSONObject(sb.toString());
                String inputCode = getIntent().getStringExtra("inputCode");
                Log.v("v", "Data: " + data);

                JSONArray arr = null;
                arr = new JSONArray(data.getString("results"));
                JSONObject tempJSON = null;

                tempThis.names = new String[arr.length()];
                tempThis.website = new String[arr.length()];
                tempThis.email = new String[arr.length()];
                tempThis.bioID = new String[arr.length()];
                tempThis.party = new String[arr.length()];
                tempThis.tweets = new String[arr.length()];
                tempThis.term = new String[arr.length()];

                for(int i = 0; i < arr.length(); i++) {
                    tempThis.tweets[i] = "loading...";
                }

                for(int i = 0; i < arr.length(); i++) {
                    tempJSON = arr.getJSONObject(i);

                    tempThis.names[i] = tempJSON.getString("title") + ". "
                            + tempJSON.getString("first_name") + " "
                            + tempJSON.getString("last_name") + " ("
                            + tempJSON.getString("party") + ")";
                    tempThis.website[i] = tempJSON.getString("website");
                    tempThis.email[i] = tempJSON.getString("oc_email");
                    tempThis.party[i] = tempJSON.getString("party");
                    tempThis.bioID[i] = tempJSON.getString("bioguide_id");
                    tempThis.term[i] = tempJSON.getString("term_end");

                    getRecentTweet(tempJSON.getString("twitter_id"), i, arr.length()-1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            /* Get tweets */

//            getRecentTweet("senatorboxer", 0, 3);
//            getRecentTweet("senfeinstein", 1, 3);
//            getRecentTweet("senatorboxer", 2, 3);
//            getRecentTweet("RepBarbaraLee", 3, 3);

            /* Get voting data from api */
            String temp;
            JSONObject tempJSON;
            String elData = "";
            try {
                InputStream is = context.getAssets().open("eldata.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                temp = new String(buffer, "UTF-8");
                JSONArray jsonArray = new JSONArray(temp);
                for(int i=0 ; i< jsonArray.length(); i++) {
                    tempJSON = jsonArray.getJSONObject(i);  // get jsonObject @ i position
                    String name = tempJSON.getString("county-name");
                    String state = tempJSON.getString("state-postal");

                    if (state.equalsIgnoreCase(tempThis.state)
                            && name.equalsIgnoreCase(tempThis.county
                            .substring(0,tempThis.county.length() - 7))) {
                        elData += tempJSON.getString("obama-percentage") + "|"
                                + tempJSON.getString("romney-percentage");
                    }
                }
            } catch(Exception e) {
                e.printStackTrace();
            }

            Log.v("v", "Election Data: " + elData);

            /* load string as following (county|name...|obama%|romney%): "county|name|33.6|55.3" */
            /* name = "|display name, party, term date, bioID|" */
            // Watch bullshit
            String toWatch = tempThis.county + "|";
            for (int i = 0; i < tempThis.names.length; i++) {
                toWatch += tempThis.names[i] + ",";
                toWatch += tempThis.party[i] + ",";
                toWatch += tempThis.term[i] + ",";
                toWatch += tempThis.bioID[i] + ",";
                toWatch += "|";
            }
            toWatch += elData;

            Log.v("v", "Data To Phone: " + toWatch);

            /* Send to phonetowatch */
            Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
            sendIntent.putExtra("data", toWatch);
            sendIntent.putExtra("inputCode", getIntent().getStringExtra("inputCode"));
            startService(sendIntent);

            super.onPostExecute(aVoid);
        }
    }

    /* Get most recent tweet */
    private void getRecentTweet(final String handle, final int tweetIndex, final int max) {
        TwitterCore.getInstance().logInGuest(new Callback<AppSession>() {
            @Override
            public void success(Result<AppSession> appSessionResult) {
                AppSession session = appSessionResult.data;
                TwitterApiClient twitterApiClient =
                        TwitterCore.getInstance().getApiClient(session);
                twitterApiClient.getStatusesService().userTimeline(null, handle, 1,
                        null, null, false, false, false, true, new Callback<List<Tweet>>() {
                            @Override
                            public void success(Result<List<Tweet>> listResult) {
                                for (Tweet tweet : listResult.data) {
                                    Log.d("v", "Handle: " + handle);
                                    Log.d("v", "result: " + tweet.text + "  " + tweet.createdAt);
                                    tempThis.tweets[tweetIndex] = "Latest Tweet: " + tweet.text;
                                }

                                /* Adapter stuff */
                                if (tweetIndex == max) {
                                    String inputCode = getIntent().getStringExtra("inputCode");

                                    /* Writes data into the list layout */
                                    RepsAdapter adapter = new RepsAdapter(tempThis, names,
                                            website, email, tweets, bioID, party, term);
                                    lv.setAdapter(adapter);

                                    /* Prepares detailed view intent */
                                    Intent nextIntent = new Intent(RepView.this,
                                            Detailed.class);
                                    nextIntent.putExtra("inputCode", inputCode);
                                    adapter.nextIntent = nextIntent;
                                }
                            }

                            @Override
                            public void failure(TwitterException e) {
                                e.printStackTrace();
                            }
                        });
            }

            @Override
            public void failure(TwitterException e) {
                e.printStackTrace();
            }
        });
    }
}