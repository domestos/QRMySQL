package com.example.varenik.qrmysql;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.varenik.qrmysql.helper.BoxValues;
import com.example.varenik.qrmysql.helper.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class EditActivityFragment extends android.app.Fragment {

    private boolean isWork = false; //інформує актівіті про стан AsyncTask
    public EditActivityFragmentAsyncTask editActivityFragmentAsyncTask;
    private EditActivity activity;
    private ParsarData parsarData = new ParsarData();
    private JSONParser jsonParser = new JSONParser();


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

//    public void startGetJSON(String url) {
//        editActivityFragmentAsyncTask = new EditActivityFragmentAsyncTask();
//        editActivityFragmentAsyncTask.execute(url);
//    }


    public void stop() {
        if (editActivityFragmentAsyncTask != null) {
            editActivityFragmentAsyncTask.cancel(true);
        }
    }

    public boolean getIsWork() {
        return isWork;
    }

    public void saveChange(List<NameValuePair> params) {
        editActivityFragmentAsyncTask = new EditActivityFragmentAsyncTask();
        editActivityFragmentAsyncTask.execute(params);

    }


    // ==================== INTO CLASS =======================
    class EditActivityFragmentAsyncTask extends AsyncTask<List<NameValuePair> , String, String> {

        @Override
        protected void onPreExecute() {
            isWork = true;
            activity.showProgress(isWork);
        }

        @Override
        protected String doInBackground(List<NameValuePair> ... params) {
            Log.d(BoxValues.TAG_LOG, "URL_SELECT_UPDATE= "+ BoxValues.URL_SELECT_UPDATE);
            // получаем продукт по HTTP запросу
            JSONObject json = jsonParser.makeHttpRequest(BoxValues.URL_SELECT_UPDATE, "POST", params[0]);


            return json.toString();
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
            editActivityFragmentAsyncTask = null;
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
