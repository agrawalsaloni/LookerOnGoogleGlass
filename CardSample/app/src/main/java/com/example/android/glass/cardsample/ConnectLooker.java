package com.example.android.glass.cardsample;

import android.content.Context;
import android.util.Log;

import com.github.mikephil.charting.data.BarEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class ConnectLooker {
    private int start = 0;
    private int end = 300;
    private JSONArray json;
    public String t = "";
    public ConnectLooker()
    {

    }

    public String connectLooker()
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
            t = (String) token.get("access_token");
            http.disconnect();

//            URL url2 = new URL("https://lookerv218.dev.looker.com:19999/api/3.1/queries/3150/run/json");
//            HttpURLConnection http2 = (HttpURLConnection)url2.openConnection();
//            http2.setRequestMethod("GET");
//            http2.setRequestProperty("Authorization", "token "+access_token);
//            int responseCode = http2.getResponseCode();
//            if (responseCode == HttpURLConnection.HTTP_OK) { // success
//                BufferedReader in2 = new BufferedReader(new InputStreamReader(
//                        http2.getInputStream()));
//                String inputLine;
//                StringBuffer response = new StringBuffer();
//
//                while ((inputLine = in2.readLine()) != null) {
//                    response.append(inputLine);
//                }
//                in2.close();
//
//                // print result
//                responseJson = new JSONArray(response.toString());
//
//            }
//            http2.disconnect();
//            return responseJson;
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
        return t;
    }

    public ArrayList<BarEntry> getBarDataStub(Context context, String token)  {
        String jsonString;
        ArrayList<BarEntry> values = new ArrayList<>();
        try {
            InputStream is = context.getAssets().open("data.json");

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            jsonString = new String(buffer, "UTF-8");
            JSONArray responseJson = new JSONArray(jsonString);
            for (int i = 0; i < responseJson.length(); i++) {

                try {
                    float x = i;
                    Double y = (Double) responseJson.getJSONObject(i).get("astockdata.close");
                    values.add(new BarEntry(x, y.floatValue()));
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch(Exception e)
                {
                    Log.d("saloni 1", e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return values;
    }

    public ArrayList<BarEntry> getBarData(String token) throws IOException, JSONException {
        JSONArray responseJson = new JSONArray();
        ArrayList<BarEntry> values = new ArrayList<>();
        try {
            URL url2 = new URL("https://lookerv218.dev.looker.com:19999/api/3.1/queries/3150/run/json");
            HttpURLConnection http2 = (HttpURLConnection) url2.openConnection();
            http2.setRequestMethod("GET");
            http2.setRequestProperty("Authorization", "token " + token);
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


                for (int i = 0; i < responseJson.length(); i++) {

                    try {
                        float x = i;
                        Double y = (Double) responseJson.getJSONObject(i).get("astockdata.close");
                        values.add(new BarEntry(x, y.floatValue()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch(Exception e)
                    {
                        Log.d("saloni 1", e.getMessage());
                    }
                }

                return values;

            }
            http2.disconnect();
        }
        catch (Exception ex)
        {
            throw ex;
        }
        return values;
    }

    public JSONArray getMyDashboards()
    {
        URL url2 = null;
        JSONArray responseJson = new JSONArray();
        try {
            url2 = new URL("https://lookerv218.dev.looker.com:19999/api/3.1//folders/146/dashboards");
            HttpURLConnection http2 = (HttpURLConnection)url2.openConnection();
            http2.setRequestMethod("GET");
            http2.setRequestProperty("Authorization", "token "+t);
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        return responseJson;
    }

    public ArrayList<BarEntry> getLookerData()
    {
        //getMyDashboards();
        JSONArray responseJson = json;
        ArrayList<BarEntry> values = new ArrayList<>();

        for (int i = 0; i < responseJson.length(); i++) {

            try {
                float x = i;
                Double y = (Double) responseJson.getJSONObject(i).get("astockdata.close");
                values.add(new BarEntry(x, y.floatValue()));
            } catch (JSONException e) {
                e.printStackTrace();
            } catch(Exception e)
            {
                Log.d("saloni 1", e.getMessage());
            }
        }

        return values;
    }
}
