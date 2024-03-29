package com.campustalk.developer.campustalk;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by khushali on 02/04/2016.
 */
public class DownloadData extends AsyncTask<Void,Void,String> {

    Callback callback;
    String url;
    HashMap<String,String> parametersMap;

    public DownloadData(String url,HashMap<String,String> parametersMap,Callback callback){
        this.url = url;
        this.parametersMap = parametersMap;
        this.callback = callback;
    }

    @Override
    protected String doInBackground(Void... params) {
        HttpURLConnection con=null;

        try {
            StringBuilder builtURL = new StringBuilder(url+"?");
            Set<String> keySet = parametersMap.keySet();

            for(String key : keySet){
                builtURL.append(URLEncoder.encode(key, "UTF-8")+"="+URLEncoder.encode(parametersMap.get(key),"UTF-8")+"&");
            }

            String finalURL = builtURL.substring(0,builtURL.length()-1);
            System.out.println(finalURL);

            URL obj = new URL(finalURL);
             con = (HttpURLConnection) obj.openConnection();

            // optional default is GET
            con.setRequestMethod("GET");

            //int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            //System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            System.out.println(response.toString());
            return response.toString();
        }catch (Exception e){
            e.printStackTrace();

        }finally{
            con.disconnect();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        callback.getData(s);
    }
}
