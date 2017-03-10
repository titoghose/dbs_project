package com.example.android.requiry;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by Aayush on 06-Mar-17.
 */
public class DataAsyncLoader extends AsyncTaskLoader<String> {
    String urlstr;
    public static final String LOG_TAG = DataAsyncLoader.class.getSimpleName();
    public DataAsyncLoader(Context context,String url) {
        super(context);
        this.urlstr = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public String loadInBackground() {
        if(TextUtils.isEmpty(urlstr))
            return null;
        URL url = createUrl(urlstr);
        String jsonresponse = "";
        try {
            jsonresponse = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonresponse;
    }
    private URL createUrl(String stringurl){
        URL url=null;
        try {
            url = new URL(stringurl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "error ocurred during url formation");
        }
        return url;
    }
    private String makeHttpRequest(URL url) throws IOException{
        String jsonresponse="";
        if(url==null){
            return jsonresponse;
        }
        HttpURLConnection httpURLConnection=null;
        InputStream inputStream=null;
        try{
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(1000);
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.connect();

            if(httpURLConnection.getResponseCode()==200){
                inputStream = httpURLConnection.getInputStream();
                jsonresponse = parseInputStream(inputStream);
            }
            else{
                Log.e(LOG_TAG,"Error in response Code "+httpURLConnection.getResponseCode());
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            if(httpURLConnection!=null){
                httpURLConnection.disconnect();
            }
            if(inputStream!=null){
                inputStream.close();
            }
        }
        return jsonresponse;
    }
    private String parseInputStream(InputStream inputStream)throws IOException{
        if(inputStream==null){
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line = bufferedReader.readLine();
        while(line!=null){
            stringBuilder.append(line);
            line=bufferedReader.readLine();
        }
        return stringBuilder.toString();
    }
}
