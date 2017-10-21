package com.example.varenik.qrmysql;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.varenik.qrmysql.helper.BoxValues;
import com.example.varenik.qrmysql.helper.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivityFragment extends android.app.Fragment {

    private boolean isWork = false; //інформує актівіті про стан AsyncTask
    public MainActivityFragmentAsyncTask mainActivityFragmentAsyncTask;
    private MainActivity activity;
    private String URLResponse;

    private JSONParser jsonParser = new JSONParser();
    private ParsarData  parsarData= new ParsarData();



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);//заборона перестворювати фрагмент при повороті екрану
    }

    /**
     * Даний метод викликається в методі onCreate() класу MainActivity,
     * тому ми завжди будемо мати актуальний activity, навіть при повороті екрану.
     */
    public MainActivity link(MainActivity activity) {
        return this.activity = activity;
    }

    /**
     * цей метод демонструє звязок між класом MainFragment та MainActivity
     * але замість ньоги ми будемо використовувати метод link(...).
     */
    public MainActivity getMainActvity() {
        activity = (MainActivity) this.getActivity();
        return activity;
    }

    public void startGetJSON(String searcheNumber) {
        mainActivityFragmentAsyncTask = new MainActivityFragmentAsyncTask();
        mainActivityFragmentAsyncTask.execute(searcheNumber);
    }


    public void stop() {
        if (mainActivityFragmentAsyncTask != null) {
            mainActivityFragmentAsyncTask.cancel(true);
        }
    }

    public boolean getIsWork() {
        return isWork;
    }


    // ==================== INTO CLASS =======================
    class MainActivityFragmentAsyncTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            isWork = true;
            activity.showProgress(isWork);
        }

        @Override
        protected String doInBackground(String... strings) {
            String number =  "'"+strings[0].toString()+"'";
            Log.d(BoxValues.TAG_LOG, "number= "+ number);
            int success ;
            JSONArray productObj = null;

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("number", number));
            // получаем продукт по HTTP запросу
            JSONObject json = jsonParser.makeHttpRequest(BoxValues.URL_SELECT_NUMEBER, "GET", params);


            try {
        //        Log.d(BoxValues.TAG_LOG, json.toString());
                success = json.getInt(BoxValues.TAG_SUCCESS);
                if (success ==1){
                    // Успешно получинна детальная информация о продукте
                    productObj = json.getJSONArray(BoxValues.TAG_PRODUCT);
                    JSONObject product = productObj.getJSONObject(0);
                    Log.d(BoxValues.TAG_LOG, "product= "+ product);
                    return   product.toString();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }



        @Override
        protected void onPostExecute(String s) {

            activity.showProgress(false);
            isWork = false;
            BoxValues.saveInfoItem = s;
            s =parsarData.dataParsed(s);
            activity.tvInfoItem.setText(s);
            mainActivityFragmentAsyncTask = null;
        }

        @Override
        protected void onCancelled() {
            Log.i("log", "cancelled");
            isWork = false;
            activity.showProgress(isWork);
            super.onCancelled();
        }



    } // end into class


}
