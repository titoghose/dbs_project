package com.example.android.requiry;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by MAHE on 02-Mar-17.
 */
public class SubmitAsyncTask extends AsyncTask<String, Void, String> {
    String urlString;
    JSONObject jsonObject;
    Context context;
    InformComplete myCallback;

    public SubmitAsyncTask(Context context,String url, JSONObject jsonObject,InformComplete mycallback) {
        this.context = context;
        urlString = url;
        this.jsonObject = jsonObject;
        myCallback = mycallback;
    }


    private URL createUrl(String stringurl) {
        URL url = null;
        try {
            url = new URL(stringurl);
        } catch (MalformedURLException e) {
            Log.e("SubmitAsyncTask", "error ocurred during url formation");
        }
        return url;
    }

    @Override
    protected String doInBackground(String... params) {

        try {

            URL url = createUrl(urlString); // here is your URL path
            Log.e("params", jsonObject.toString());

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(jsonObject.toString());

            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(new
                        InputStreamReader(
                        conn.getInputStream()));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {

                    sb.append(line);
                }

                in.close();
                return sb.toString();

            } else {
                return "false : " + responseCode;
            }
        } catch (Exception e) {
            return "Exception: " + e.getMessage();
        }

    }
    @Override
    protected void onPostExecute(String result) {
        myCallback.postData(result);
    }
    public interface InformComplete{
        void postData(String result);
    }
}
