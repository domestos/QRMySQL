package com.example.varenik.qrmysql;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

/**
 * Created by varenik on 15.10.17.
 * <p>
 * щоб клав вернув String  в форматі JSON необхіно
 * викликати його метод startGetJSON()
 * та отримати змінну за допомогою getURLRequest()
 */

public class ConnectToURLFragment extends android.app.Fragment {

    private boolean isWork = false; //інформує актівіті про стан AsyncTask
    public ConnectToURLAsyncTask connectToURLAsyncTask;
    private MainActivity activity;
    public String URLRequest = "waiting";
    public Const c = new Const();
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

    public void startGetJSON(String url) {
        connectToURLAsyncTask = new ConnectToURLAsyncTask();
        connectToURLAsyncTask.execute(url);
    }


    public void stop() {
        if (connectToURLAsyncTask != null) {
            connectToURLAsyncTask.cancel(true);
        }
    }

    public boolean getIsWork() {
        return isWork;
    }


    // ==================== INTO CLASS =======================
    class ConnectToURLAsyncTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            isWork = true;
            activity.showProgress(isWork);
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                Thread.sleep(100);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return  DoConnect.doConnect(params[0].toString());
        }



        @Override
        protected void onPostExecute(String s) {
            Const.saveInfoItem = s;
            activity.showProgress(false);
            isWork = false;
            s =parsarData.dataParsed(s);
            activity.tvInfoItem.setText(s);
            connectToURLAsyncTask = null;
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
