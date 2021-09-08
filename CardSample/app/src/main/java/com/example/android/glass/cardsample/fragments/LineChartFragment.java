package com.example.android.glass.cardsample.fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android.glass.cardsample.ConnectLooker;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.example.android.glass.cardsample.R;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;

public class LineChartFragment extends BaseFragment {
    private static final int MAX_X_VALUE = 20;
    private static final int MAX_Y_VALUE = 50;
    private static final int MIN_Y_VALUE = 5;
    private static final String SET_LABEL = "GOOG - Line";
    private static final String[] DAYS = {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};
    public ArrayList<BarEntry> values = new ArrayList<>();
    private LineChart chart;
    private boolean runnable = false;

    private int start = 0;
    private int end = 580;
    public View view;

    public LineChartFragment(ArrayList<BarEntry> data)
    {
        values = data;
    }

    private LineData createLineData() {

        ArrayList<Entry> lineEntry = new ArrayList<Entry>();
        for(int i = start; i <= end; i++)
        {
            lineEntry.add(new Entry(values.get(i).getX(), values.get(i).getY()));
        }
        LineDataSet set1 = new LineDataSet(lineEntry, SET_LABEL);
        set1.setDrawValues(false);
        set1.setDrawCircles(false);
        set1.setColor(ColorTemplate.rgb("#3EB0D5"));
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        LineData data = new LineData(dataSets);
        data.notifyDataChanged();
        return data;
    }

    private void prepareChartData(LineData data) {
        data.setValueTextSize(12f);
        chart.setData(data);
        chart.notifyDataSetChanged();
        chart.invalidate();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        runnable = true;
        //startDraw.start();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_linechart, container, false);

        chart = view.findViewById(R.id.fragment_linechart_chart);

        //BarData data = createChartData();
        LineData data = createLineData();
        configureChartAppearance();
        prepareChartData(data);

        return view;
    }

    @Override
    public void onDestroy() {
        runnable = false;
        super.onDestroy();
    }

    private void configureChartAppearance() {
        YAxis axisLeft = chart.getAxisLeft();
        axisLeft.isDrawBottomYLabelEntryEnabled();
        axisLeft.setTextColor(Color.WHITE);
        chart.getXAxis().setDrawGridLines(false);
        chart.getAxisRight().setDrawAxisLine(false);
        chart.getXAxis().setDrawAxisLine(false);
        chart.getDescription().setEnabled(false);
        chart.getLegend().setTextColor(Color.WHITE);
    }


    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler(){
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(android.os.Message msg){
            try {
                switch(msg.what){

                    case 0x01:
                        if(start >= values.size()) {
                            start = 0;
                            end = 99;
//                            runnable = false;
//                            break;
                        }
                        LineData data = createLineData();
                        configureChartAppearance();
                        prepareChartData(data);
                        start = start + 100;
                        end = end + 100;
                        break;
                }
            }
            catch (Exception e)
            {
                throw e;
            }

        }
    };

    public Thread startDraw = new Thread(){
        @Override
        public void run(){
            while(runnable){
                handler.sendEmptyMessage(0x01);
                try{
                    Thread.sleep(1000);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    };

}

