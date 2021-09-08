package com.example.android.glass.cardsample.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.android.glass.cardsample.BaseActivity;
import com.example.android.glass.cardsample.ConnectLooker;
import com.example.android.glass.cardsample.MainActivity;
import com.example.android.glass.cardsample.R;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarEntry;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DashboardContent extends BaseActivity {
    public JSONArray j = new JSONArray();
    public View view;

    private List<BaseFragment> fragments = new ArrayList<>();
    private ViewPager viewPager;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String value = intent.getStringExtra("dash");
        int dash_id = intent.getIntExtra("id", 72);
        String token = intent.getStringExtra("token");
        setContentView(R.layout.view_pager_layout);
        final ScreenSlidePagerAdapter screenSlidePagerAdapter = new ScreenSlidePagerAdapter(
            getSupportFragmentManager());
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(screenSlidePagerAdapter);

        ConnectLooker c = new ConnectLooker();
        ArrayList<BarEntry> data = null;
        try {
            //data = c.getBarData(token);
            data = c.getBarDataStub(this, token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        VerticalBarChartFragment x = new VerticalBarChartFragment(data);

        LineChartFragment y = new LineChartFragment(data);

        fragments.add(x);
        fragments.add(y);
        screenSlidePagerAdapter.notifyDataSetChanged();

        final TabLayout tabLayout = findViewById(R.id.page_indicator);
        tabLayout.setupWithViewPager(viewPager, true);
    }


    public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
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
