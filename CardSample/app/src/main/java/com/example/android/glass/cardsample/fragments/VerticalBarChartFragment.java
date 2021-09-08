package com.example.android.glass.cardsample.fragments;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android.glass.cardsample.ConnectLooker;
import com.github.mikephil.charting.charts.BarChart;
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
import com.google.android.material.transition.MaterialSharedAxis;

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

public class VerticalBarChartFragment extends BaseFragment {
    private static final int MAX_X_VALUE = 20;
    private static final int MAX_Y_VALUE = 50;
    private static final int MIN_Y_VALUE = 5;
    private static final String SET_LABEL = "GOOG";
    private static final String[] DAYS = {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};
    public ArrayList<BarEntry> values = new ArrayList<>();
    private BarChart chart;
    private boolean runnable = false;

    private int start = 0;
    private int end = 199;
    public View view;

    public VerticalBarChartFragment(ArrayList<BarEntry> data)
    {
        values = data;
    }
    private JSONArray connectLooker()
    {
        URL url = null;
        JSONArray responseJson = new JSONArray();
        try {
            url = new URL("https://lookerv218.dev.looker.com:19999/login");
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            //http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            String data = "<client id and secret string>";
            http.getOutputStream().write(data.getBytes("UTF-8"));
            InputStream in = http.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder result = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null) {
                result.append(line);
            }
            in.close();
            JSONObject token = new JSONObject(result.toString());
            String access_token = (String) token.get("access_token");

            http.disconnect();

            URL url2 = new URL("https://lookerv218.dev.looker.com:19999/api/3.1/queries/3116/run/json");
            HttpURLConnection http2 = (HttpURLConnection)url2.openConnection();
            http2.setRequestMethod("GET");
            http2.setRequestProperty("Authorization", "token "+access_token);
            int responseCode = http2.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                BufferedReader in2 = new BufferedReader(new InputStreamReader(
                        http2.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in2.readLine()) != null) {
                    response.append(inputLine);
                }
                in2.close();

                // print result
                responseJson = new JSONArray(response.toString());

            }
            http2.disconnect();
            return responseJson;
        } catch (MalformedURLException e) {
            Log.d("mal", e.getMessage());
            e.printStackTrace();
        } catch (ProtocolException e) {
            Log.d("proto", e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.d("io", e.getMessage());
            e.printStackTrace();
        } catch(Exception e)
        {
            Log.d("saloni", e.getMessage());
        }
        return responseJson;
    }

    private void createNotificationChannel() {
        CharSequence name = "Viz";
        String description = "Looker viz desc";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("looker charts", name, importance);
        channel.setDescription(description);
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(getActivity().NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
    }

    @SuppressLint("ResourceAsColor")
    private BarData createChartData() {

        int e = end;
        if(end >= values.size())
            e = values.size() - 1;

//        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), "looker charts")
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentTitle("My notification")
//                .setContentText("Hi")
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getActivity());

        for(int i = start; i <= e; i++)
        {
            if(values.get(i).getY() > 2400 && values.get(i).getY() < 2405)
            {
//
//// notificationId is a unique int for each notification that you must define
               //notificationManager.notify(0, builder.build());
                Toast toast = Toast.makeText(getActivity(), Html.fromHtml(
                        "<font color='green' ><b>" + "GOOG 2500" + "</b></font>"),
                        Toast.LENGTH_SHORT);
                //View view = toast.getView();
                //view.get
//Gets the actual oval background of the Toast then sets the colour filter
                //view.getBackground().setColorFilter(android.R.color.background_light, PorterDuff.Mode.SRC_IN);

                toast.setGravity(Gravity.TOP | Gravity.RIGHT, 35, 6);
                toast.show();
            }

            if(values.get(i).getY() > 1150 && values.get(i).getY() < 1160)
            {

                Toast toast = Toast.makeText(getActivity(), Html.fromHtml(
                        "<font color='red' ><b>" + "Dropped to 1200" + "</b></font>"),
                        Toast.LENGTH_SHORT);

                toast.setGravity(Gravity.TOP | Gravity.RIGHT, 35, 6);
                toast.show();
            }
        }
        BarDataSet set1 = new BarDataSet(values.subList(start, e), SET_LABEL);
        set1.setDrawValues(false);
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();

        set1.setColor(ColorTemplate.rgb("#3EB0D5"));
        dataSets.add(set1);
        BarData data = new BarData(dataSets);
        data.notifyDataChanged();
        return data;
    }

    private LineData createLineData() {
        //JSONArray responseJson = connectLooker();
//        ArrayList<BarEntry> values = new ArrayList<>();

//        for (int i = 0; i < responseJson.length(); i++) {
//
//            try {
//                float x = i;
//                Double y = (Double) responseJson.getJSONObject(i).get("astockdata.close");
//                values.add(new BarEntry(x, y.floatValue()));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            } catch(Exception e)
//            {
//                Log.d("saloni 1", e.getMessage());
//            }
//        }

        ArrayList<Entry> lineEntry = new ArrayList<Entry>();
        for(int i = start; i <= end; i++)
        {
            lineEntry.add(new Entry(values.get(i).getX(), values.get(i).getY()));
        }
        LineDataSet set1 = new LineDataSet(lineEntry, SET_LABEL);
        set1.setDrawValues(false);
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        LineData data = new LineData(dataSets);
        data.notifyDataChanged();
        return data;
    }

    private void prepareChartData(BarData data) {
        data.setValueTextSize(12f);
        chart.setData(data);
        chart.notifyDataSetChanged();
        chart.invalidate();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        runnable = true;
        startDraw.start();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_verticalbarchart, container, false);

        chart = view.findViewById(R.id.fragment_verticalbarchart_chart);
//        view = inflater.inflate(R.layout.fragment_linechart, container, false);
//
//        chart = view.findViewById(R.id.fragment_linechart_chart);

        createNotificationChannel();


        BarData data = createChartData();
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
//        chart.getDescription().setEnabled(false);
//        chart.setDrawValueAboveBar(false);
//
//        XAxis xAxis = chart.getXAxis();
////        xAxis.setValueFormatter(new ValueFormatter() {
////            @Override
////            public String getFormattedValue(float value) {
////                return DAYS[(int) value];
////            }
////        });
//
        YAxis axisLeft = chart.getAxisLeft();
        axisLeft.isDrawBottomYLabelEntryEnabled();
        axisLeft.setTextColor(Color.WHITE);
        axisLeft.setAxisMinimum(800);
        axisLeft.setAxisMaximum(3000);
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
                            end = 199;
                            runnable = false;
                            break;
                        }
                        BarData data = createChartData();
                        configureChartAppearance();
                        prepareChartData(data);
                        start = start + 50;
                        end = end + 50;
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

