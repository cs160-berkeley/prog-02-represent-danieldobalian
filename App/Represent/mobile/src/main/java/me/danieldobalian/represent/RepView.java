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
import android.widget.ListView;
import android.widget.ImageView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.app.Activity;
import java.util.ArrayList;
import android.view.Menu;


public class RepView extends AppCompatActivity {

    ListView lv;
    Context context;

    ArrayList prgmName;
    public static int [] picture={R.drawable.rep, R.drawable.rep, R.drawable.rep, R.drawable.rep, R.drawable.rep, R.drawable.rep, R.drawable.rep, R.drawable.rep};
    public static String [] names={"Senator Dianne Feinstein (D)", "Senator Dianne Feinstein (D) ",
            "Senator Dianne Feinstein (D)"};
    public static String [] website={"http://feinstein.senate.gov", "http://feinstein.senate.gov",
            "http://feinstein.senate.gov"};
    public static String [] email={"dianne@feinstein.senate.gov", "dianne@feinstein.senate.gov",
            "dianne@feinstein.senate.gov"};
    public static String [] tweets={"Ill be on tv...", "Ill be on tv...", "Ill be on tv..."};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rep_view);

        // Dont do below if coming from watch
        context=this;

        lv=(ListView) findViewById(R.id.listView);
        names[0] = names[1] + getIntent().getStringExtra("inputCode");
        RepsAdapter adapter = new RepsAdapter(this, picture, names, website, email, tweets);
        lv.setAdapter(adapter);
        Intent nextIntent = new Intent(RepView.this,
                Detailed.class);
        nextIntent.putExtra("inputCode", getIntent().getStringExtra("inputCode"));
        adapter.nextIntent = nextIntent;

        // Watch bullshit
        Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
        sendIntent.putExtra("inputCode", getIntent().getStringExtra("inputCode"));
        startService(sendIntent);

    }

}