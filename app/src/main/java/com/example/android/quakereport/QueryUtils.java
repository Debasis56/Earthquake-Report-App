package com.example.android.quakereport;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static com.example.android.quakereport.EarthquakeActivity.LOG_TAG;


public final class QueryUtils {

    private QueryUtils() {
    }


    public static List<Earthquake> fetchEarthquakeData(String requestUrl) {
                URL url = createUrl(requestUrl);

                String jsonResponse = null;
        try {
                        jsonResponse = makeHttpRequest(url);
        }
        catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

                List<Earthquake> earthquake = extractFeatureFromJson(jsonResponse);

                return earthquake;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

                if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

                                    if (urlConnection.getResponseCode() == 200 ) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }


    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public static List<Earthquake> extractFeatureFromJson(String earthquakeJSON) {
                List<Earthquake> earthquakes = new ArrayList<>();

                if (TextUtils.isEmpty(earthquakeJSON)) {
            return null;
        }

                                try {
                                    JSONObject reader = new JSONObject(earthquakeJSON);
            JSONArray features = reader.getJSONArray("features");
            for (int i = 0; i < features.length(); i++) {
                JSONObject c = features.getJSONObject(i);
                JSONObject properties = c.getJSONObject("properties");

                                double magnitude = properties.getDouble("mag");
                String place = properties.getString("place");
                long time = properties.getLong("time");
                String url = properties.getString("url");


                earthquakes.add(new Earthquake(magnitude, place, time, url));
            }

        } catch (JSONException e) {
        Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

                return earthquakes;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

}