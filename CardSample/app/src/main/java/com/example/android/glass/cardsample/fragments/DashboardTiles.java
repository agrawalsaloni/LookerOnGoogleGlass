package com.example.android.glass.cardsample.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.android.glass.cardsample.BaseActivity;
import com.example.android.glass.cardsample.MainActivity;
import com.example.android.glass.cardsample.R;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarEntry;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class DashboardTiles extends BaseActivity {
    public JSONArray jar = new JSONArray();
    public View view;
    public String token = "";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String value = intent.getStringExtra("dash");
        token = intent.getStringExtra("token");
        setContentView(R.layout.button);
        final LinearLayout bodyLayout = findViewById(R.id.dashboard);
        //final CardView bodyLayout = findViewById(R.id.dash_card);
        boolean flag = true;
        try {
            jar = new JSONArray(value);
            for(int i = 0; i < jar.length(); i++)
            {
                final Button b = new Button(this);
                b.setText((CharSequence) jar.getJSONObject(i).get("title"));
                //b.setTextSize(40);
                //b.setTypeface(Typeface.create(getString(R.string.thin_font), Typeface.NORMAL));

                b.setId((Integer) jar.getJSONObject(i).get("id"));
                b.setHeight(150);
                b.setTextSize(15);
                //if(flag == true) {
                    BitmapDrawable img = (BitmapDrawable) getDrawable(R.drawable.dash72small);

                    if(i == 1)
                    {
                         img = (BitmapDrawable) getDrawable(R.drawable.dash2);
                    }
                    else if(i == 2)
                    {
                         img = (BitmapDrawable) getDrawable(R.drawable.dash1);
                    }
//                ScaleDrawable si = new ScaleDrawable(
//                  img,
//                  0,
//                        ((float) 0.5),
//                        ((float) 0.5)
//                );

                    b.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
                    flag = false;
                //}
                bodyLayout.addView(b);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openContentView(v);
                    }
                });
            }
//            ImageButton myButton = view.findViewById(R.id.buttontest);
//            myButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //openNewActivity();
//                }
//            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void openContentView(View v){
        Intent intent = new Intent(this, DashboardContent.class);
        intent.putExtra("dash", jar.toString());
        intent.putExtra("token", token);
        intent.putExtra("id", (int)v.getId());
        startActivity(intent);
    }


//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
//                             @Nullable Bundle savedInstanceState) {
//        final View view = inflater.inflate(R.layout.button, container, false);
//        ImageButton myButton =  view.findViewById(R.id.buttontest);
//        ImageButton myButton2 =  view.findViewById(R.id.buttontest2);
//        myButton.setOnClickListener(this);
//        return view;
//    }


//    public void openNewActivity(){
//        Intent intent = new Intent(this, NewActivity.class);
//        startActivity(intent);
//    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//    }

//    @Override
//    public void onClick(View view) {
//        try {
//            TextView tv = view.findViewById(R.id.text1);
//            tv.setText("hi");
//            tv.setText("sanjdajksldaklsdjlkasjd");
////            f.add(barchart);
////            msc.notifyDataSetChanged();
////            ViewPager viewPager = view.findViewById(R.id.viewPager);
////            final TabLayout tabLayout = view.findViewById(R.id.page_indicator);
////            tabLayout.setupWithViewPager(viewPager, true);
//        }
//        catch (Exception e)
//        {
//            throw e;
//        }
//    }
}
