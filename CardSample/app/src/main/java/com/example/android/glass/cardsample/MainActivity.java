/*
 * Copyright 2019 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.glass.cardsample;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.example.android.glass.cardsample.fragments.BaseFragment;
import com.example.android.glass.cardsample.fragments.ColumnLayoutFragment;
import com.example.android.glass.cardsample.fragments.DashboardTiles;
import com.example.android.glass.cardsample.fragments.MainLayoutFragment;
import com.example.android.glass.cardsample.fragments.VerticalBarChartFragment;
import com.example.glass.ui.GlassGestureDetector.Gesture;
import com.github.mikephil.charting.data.BarEntry;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Main activity of the application. It provides viewPager to move between fragments.
 */
public class MainActivity extends BaseActivity {

    private List<BaseFragment> fragments = new ArrayList<>();
    private ViewPager viewPager;
    private boolean runnable = false;
    private int start = 0;
    private int end = 99;
    public ArrayList<BarEntry> data = new ArrayList<BarEntry>();
    public JSONArray dashData = new JSONArray();
    String token = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.view_pager_layout);
        setContentView(R.layout.main_layout);


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        ConnectLooker con = new ConnectLooker();
        token = con.connectLooker();
        //ArrayList<BarEntry> data = con.getLookerData();
        dashData = con.getMyDashboards();


//        final ScreenSlidePagerAdapter screenSlidePagerAdapter = new ScreenSlidePagerAdapter(
//            getSupportFragmentManager());
//        viewPager = findViewById(R.id.viewPager);
//        viewPager.setAdapter(screenSlidePagerAdapter);

//        fragments.add(MainLayoutFragment
//            .newInstance("Saloni's Looker folder", "Looker",
//                getString(R.string.timestamp_sample), null));
//        fragments.add(MainLayoutFragment
//            .newInstance(getString(R.string.different_options), getString(R.string.empty_string),
//                getString(R.string.empty_string), R.menu.main_menu));
//        fragments.add(ColumnLayoutFragment
//            .newInstance(R.drawable.ic_style, getString(R.string.columns_sample),
//                getString(R.string.footnote_sample), getString(R.string.timestamp_sample)));
//        fragments.add(MainLayoutFragment
//            .newInstance(getString(R.string.like_this_sample), getString(R.string.empty_string),
//                getString(R.string.empty_string), null));

        //VerticalBarChartFragment x = new VerticalBarChartFragment(data);
        //DashboardTiles d = new DashboardTiles(dash, fragments, x, screenSlidePagerAdapter);
        //fragments.add(d);
//        VerticalBarChartFragment x = new VerticalBarChartFragment(data);
//        fragments.add(x);

        //screenSlidePagerAdapter.notifyDataSetChanged();

//        final TabLayout tabLayout = findViewById(R.id.page_indicator);
//        tabLayout.setupWithViewPager(viewPager, true);
//        runnable = true;
//        startDraw.start();

        Button button = (Button) findViewById(R.id.mbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity();
            }
        });
    }

    public void openNewActivity(){
        Intent intent = new Intent(MainActivity.this, DashboardTiles.class);
        intent.putExtra("dash", dashData.toString());
        intent.putExtra("token", token);
        startActivity(intent);
    }

    protected void onStart()
    {
        super.onStart();

        final TextView textView = new TextView(this);
        textView.setText("Saloni's Looker Folder");
        textView.setTextSize(40);
        textView.setTypeface(Typeface.create(getString(R.string.thin_font), Typeface.NORMAL));

        final FrameLayout bodyLayout = findViewById(R.id.body_layout);
        //bodyLayout.addView(textView);
//      Button b = new Button(getContext());
//      bodyLayout.addView(b);
        final TextView footer = findViewById(R.id.footer);
        footer.setText("Saloni's Looker folder");

        final TextView timestamp = findViewById(R.id.timestamp);
        timestamp.setText("just now");

    }

//    @Override
//    public boolean onGesture(Gesture gesture) {
//        switch (gesture) {
//            case TAP:
//                //fragments.get(viewPager.getCurrentItem()).onSingleTapUp();
//                return true;
//            default:
//                return super.onGesture(gesture);
//        }
//    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        runnable = false;
    }
}
