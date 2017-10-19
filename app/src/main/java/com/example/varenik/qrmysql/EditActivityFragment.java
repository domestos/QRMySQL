package com.example.varenik.qrmysql;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class EditActivityFragment extends android.app.Fragment {

    private boolean isWork = false; //інформує актівіті про стан AsyncTask
    public ConnectToURLAsyncTask connectToURLAsyncTask;
    private EditActivity activity;
    private ParsarData parsarData = new ParsarData();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);//заборона перестворювати фрагмент при повороті екрану
    }

    /**
     * Даний метод викликається в методі onCreate() класу MainActivity,
     * тому ми завжди будемо мати актуальний activity, навіть при повороті екрану.
     */
    public EditActivity link(EditActivity activity) {
        return this.activity = activity;
    }

    /**
     * цей метод демонструє звязок між класом MainFragment та MainActivity
     * але замість ньоги ми будемо використовувати метод link(...).
     */
    public EditActivity getEditActivity() {
        activity = (EditActivity) this.getActivity();
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
            return DoConnect.doConnect(params[0].toString());
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {

          Toast.makeText(activity, s, Toast.LENGTH_LONG).show();

            isWork = false;
            activity.showProgress(isWork);
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
