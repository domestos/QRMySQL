package com.example.varenik.qrmysql.helper;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class JSONParser {

    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";
    String TAG_LOG ="TAG_LOG";

    // constructor
    public JSONParser() {

    }

    // метод получение json объекта по url
    // используя HTTP запрос и методы POST или GET
    public JSONObject makeHttpRequest(String url, String method, List<NameValuePair> params) {

        // Создаем HTTP запрос
        try {
            Log.d(TAG_LOG, "url = "+url);
            // проверяем метод HTTP запроса
            if(method == "POST"){
                Log.d(TAG_LOG, "start POST request");
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(params));

                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();

            }else if(method == "GET"){
                Log.d(TAG_LOG, "start GET request");
                DefaultHttpClient httpClient = new DefaultHttpClient();
                String paramString = URLEncodedUtils.format(params, "UTF-8");
                url += "?" + paramString;
                HttpGet httpGet = new HttpGet(url);

                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
            }


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.d(TAG_LOG, "1"+ e.getMessage());
        } catch (ClientProtocolException e) {
            Log.d(TAG_LOG, "2"+e.getMessage());
        } catch (IOException e) {
            Log.d(TAG_LOG, "3"+e.getMessage());
        }

        //iso-8859-1

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8" ), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
        } catch (Exception e) {
            Log.e(BoxValues.TAG_LOG, " Buffer ErrorError converting result " + e.toString());
        }

        // пытаемся распарсить строку в JSON объект
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e(BoxValues.TAG_LOG, "JSON Parser Error parsing data " + e.toString());
        }

        // возвращаем JSON строку
        return jObj;

    }

}
