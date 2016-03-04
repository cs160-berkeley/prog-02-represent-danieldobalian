package me.danieldobalian.represent;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.ImageView;
import android.app.Activity;
import android.view.LayoutInflater;
import android.widget.Toast;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.graphics.Color;
import android.widget.Button;
import android.content.Intent;
import java.util.Arrays;

/**
 * Created by danieldobalian on 3/1/16.
 */
public class RepsAdapter extends BaseAdapter {
        String [] names;
        String [] website;
        String [] email;
        String [] tweets;

        Context context;
        int [] images;

        public Intent nextIntent;
        private static LayoutInflater inflater=null;
        final Animation animAlpha;

        public RepsAdapter(RepView mainActivity, int[] picture, String[] names,
                           String[] website, String[] email, String[] tweets) {
            // TODO Auto-generated constructor stub

            this.names=names;
            this.website = website;
            this.email = email;
            this.tweets = tweets;
            this.images=picture;
            this.nextIntent = null;

            context=mainActivity;
            this.animAlpha = AnimationUtils.loadAnimation(context, R.anim.anim_alpha);
            inflater = ( LayoutInflater )context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return names.length;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        public class Holder
        {
            TextView names;
            Button details;
            ImageView img;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            Holder holder=new Holder();
            View row = inflater.inflate(R.layout.listview_item_row, null);

            // Text view formatting
            holder.names=(TextView) row.findViewById(R.id.name);
            String output = "\n\n\t\t\t" + names[position] +
                    "\n\n\n\t\t\t" +
                    website[position] + "\n\n\t\t\t" +
                    email[position] + "\n\n\t\t\t" + tweets[position] +
                    "\n\n\n\n\n";
            SpannableString text = new SpannableString(output);
            text.setSpan(new ForegroundColorSpan(Color.parseColor("#2B98EC")), 0,
                    5 + names[position].length(), 0);
            text.setSpan(new ForegroundColorSpan(Color.parseColor("#2B98EC")),
                    output.length() - tweets[position].length() - 5, output.length(), 0);
            holder.names.setText(text, TextView.BufferType.SPANNABLE);

            // Button Setup
            holder.details = (Button) row.findViewById(R.id.details);

            // Image Setup
            holder.img=(ImageView) row.findViewById(R.id.imageView1);
            holder.img.setImageResource(images[position]);
            holder.details.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    //Toast.makeText(context, "You Clicked "+names[position], Toast.LENGTH_LONG).show();

                    v.startAnimation(animAlpha);

                    nextIntent.putExtra("name", names[position]);
                    nextIntent.putExtra("picture", images[position]);

                    context.startActivity(nextIntent);
                }
            });

            holder.names.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    //Toast.makeText(context, "You Clicked "+names[position], Toast.LENGTH_LONG).show();

                    v.startAnimation(animAlpha);

                    nextIntent.putExtra("name", names[position]);
                    nextIntent.putExtra("picture", images[position]);

                    context.startActivity(nextIntent);
                }
            });


            return row;
        }

    }