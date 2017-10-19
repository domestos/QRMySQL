package com.example.varenik.qrmysql;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Valera on 19.10.2017.
 */

public class DoConnect {


    public static String doConnect(String s) {
        URL url = null;
        try {
            url = new URL(s);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "ERROR " + e.getMessage();
        }

        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(2500);
            httpURLConnection.setReadTimeout(25000);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setDoInput(true);
            httpURLConnection.connect();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error" + e.getMessage();
        }
        try {
            if (HttpURLConnection.HTTP_OK == httpURLConnection.getResponseCode()) {
                StringBuilder sb = new StringBuilder();
                BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
                String line;

                while ((line = in.readLine()) != null) {

                    sb.append(line);

                    sb.append("\n");
                }

                return sb.toString();
            } else {
                return "fail " + httpURLConnection.getResponseCode() + " " + httpURLConnection.getResponseMessage();
            }

        } catch (IOException e) {
            e.printStackTrace();
            return "Error" + e.getMessage();
        }
    } //end do Connect
}
