package com.example.varenik.qrmysql;

import android.util.Log;

import com.example.varenik.qrmysql.helper.BoxValues;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Valera on 17.10.2017.
 */

public class ParsarData {
    String singleJO;
    JSONObject JO;

    public String dataParsed(String sb) {

        try {
            Log.d(BoxValues.TAG_LOG, "dataParsed   insert param =  "+sb);
            JO = new JSONObject(sb);
            int success = JO.getInt("success");

           if (success == 0) {
               return JO.get("message").toString();
           }
           JSONArray JA = new JSONArray(JO.get("product").toString());


                JSONObject singItem = (JSONObject) JA.get(0);

                singleJO = "id:  " + JO.getString("id") + "\n" +
                        "number:  " + JO.getString("number") + "\n" +
                        "item:   " + JO.getString("item") + "\n" +
                        "owner:  " + JO.getString("owner") + "\n" +
                        "location:  " + JO.getString("location") + "\n" +
                        "description:  " + JO.getString("description");

        } catch (JSONException e) {
            e.printStackTrace();
            return sb + e.getMessage();
        }

        return singleJO;
    }

    public String dataParsed(JSONObject JO) {
        Log.w(BoxValues.TAG_LOG, "get first product "+ JO.toString());
        try {
            int success = JO.getInt("success");
            if(success==0){
                return JO.get("message").toString();
            }else {
                Log.w(BoxValues.TAG_LOG, "get first product "+ JO.get("product").toString());
                JSONArray JA = new JSONArray(JO.get("product").toString());
                JSONObject jsonObject = JA.getJSONObject(0);
                singleJO = "id:  " + jsonObject.getString("id") + "\n" +
                        "number:  " + jsonObject.getString("number") + "\n" +
                        "item:   " + jsonObject.getString("item") + "\n" +
                        "owner:  " + jsonObject.getString("owner") + "\n" +
                        "location:  " + jsonObject.getString("location") + "\n" +
                        "description:  " + jsonObject.getString("description");
                return singleJO ;

            }


        } catch (JSONException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }



}
