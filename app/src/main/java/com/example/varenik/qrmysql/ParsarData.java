package com.example.varenik.qrmysql;

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
            JO = new JSONObject(sb);
//
//            if (JO.get("success").toString().equals("0")) {
//                return JO.get("message").toString();
//            }
           // JSONArray JA = new JSONArray(JO.get("product").toString());


              //  JSONObject singItem = (JSONObject) JA.get(0);

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


        /*
        try {
            JSONArray JA =  new JSONArray(sb);
            Log.d("JA  ",JA.toString());
            for (int i = 0; i < JA.length(); i++) {
                JSONObject JO = (JSONObject) JA.get(i);
                *//*singleJO = "number: " + JO.get("number") + "\n" +
                           "item: " + JO.get("item") + "\n" +
                           "owner: " + JO.get("owner") + "\n" +
                           "location: " + JO.get("location") + "\n";*//*
                singleJO = "success: " + JO.get("success") + "\n";
              Log.d("JA1  ",singleJO.toString());
                arrayJO = arrayJO + singleJO+"\n";
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        return singleJO;
    }

}
