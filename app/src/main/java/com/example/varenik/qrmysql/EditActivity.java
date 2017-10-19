package com.example.varenik.qrmysql;

import android.app.MediaRouteButton;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EditActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvId;
    private TextView tvNumber;
    private EditText etOwner;
    private EditText etDescription;
    private ProgressBar progressBar;
    private Button btnSave;
    // private Button btnCancel;
    private EditActivityFragment editActivityFragment;
    private String TAG_FRAGMENT = "TAG_Edit_URL_connect";
    private String urlEdit;
    private JSONObject JO;
    private Spinner spLocation;
    private String[] arrayLocation = {"", "QA Red", "Administration", "BB", "Meeting Room", "QA Black", "QA Green", "QA White", "SMU", "Server Room", "Test room", "Training Room", "WAA", "Warehouse"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        ArrayAdapter<String> locations = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayLocation);
        locations.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        tvId = (TextView) findViewById(R.id.tvId);
        tvNumber = (TextView) findViewById(R.id.tvNumber);

        etOwner = (EditText) findViewById(R.id.etOwner);
        etDescription = (EditText) findViewById(R.id.etDescription);

        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);
        //  btnCancel = (Button) findViewById(R.id.btnCancel);
        // btnCancel.setOnClickListener(this);

        spLocation = (Spinner) findViewById(R.id.spLocation);
        spLocation.setAdapter(locations);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        editActivityFragment = getEditActivityFragment();
        //Передаємо об"єкту MainFragment актівіті
        editActivityFragment.link(this);

        // визначаємо в якому стані AsyncTask
        // mainFragment.getIsWork() - повертає true якщо виконується процес, false - якщо він в стані спокою

        showProgress(editActivityFragment.getIsWork());

        setSelectItem(spLocation);
        spLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getBaseContext(), adapterView.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        getValues(Const.saveInfoItem);
        Toast.makeText(this, Const.saveInfoItem, Toast.LENGTH_LONG).show();
    }


    // Fragment запускає цей метод (показує стан AsyncTasks)
    public void showProgress(boolean show) {

        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }



    public EditActivityFragment getEditActivityFragment() {
        //перевіряємо чи існує фрагмент і присвоюємо результат перевірки змінній
        editActivityFragment = (EditActivityFragment) getFragmentManager().findFragmentByTag(TAG_FRAGMENT);
        //якщо перевірка верне null, то означає,
        // що фрагмент раніше не був створений і його нобхідно створити
        if (editActivityFragment == null) {
            editActivityFragment = new EditActivityFragment();
            //записуємо створений екземпляр MainFragment у FragmentManager
            getFragmentManager().beginTransaction().add(editActivityFragment, TAG_FRAGMENT).commit();
        }

        return editActivityFragment;
    }



    private void setSelectItem(Spinner spLocation) {

        Log.d("TAG_location", "select Item");
        try {
            JO = new JSONObject(Const.saveInfoItem);
            JSONArray JA = new JSONArray(JO.get("product").toString());
            if (JA.length() == 1) {
                JSONObject singItem = (JSONObject) JA.get(0);
                int i = 0;
                String localtion;
                String chek;
                while (arrayLocation.length > i) {
                    localtion = singItem.getString("location");
                    chek = arrayLocation[i];

                    if (localtion.equals(chek)) {
                        spLocation.setSelection(i);
                    }
                    Log.d("TAG_location", localtion + " = " + arrayLocation[i]);
                    i++;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void getValues(String sb) {

        if (Const.saveInfoItem != null) {
            try {
                JO = new JSONObject(sb);
                JSONArray JA = new JSONArray(JO.get("product").toString());
                if (JA.length() == 1) {
                    JSONObject singItem = (JSONObject) JA.get(0);
                    tvId.setText(singItem.getString("id"));
                    tvNumber.setText(singItem.getString("number"));
                    etOwner.setText(singItem.getString("owner"));
                    etDescription.setText(singItem.getString("description"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSave:

                Toast.makeText(getBaseContext(), createJSONObject(), Toast.LENGTH_LONG).show();
                editActivityFragment.startGetJSON( createJSONObject());
                break;
            //      case R.id.btnCancel:

            //  break;
        }
    }

    private String createJSONObject() {
      //  http://lvilwks0004/PHPScript/db_update.php?id=6&number=240415/6/1&owner=Valera.p&location=QA%20Black&descripton=Lol
        return urlEdit = "http://192.168.1.46/PHPScript/db_update.php?id=" + tvId.getText().toString() +
                "&number="+  tvNumber.getText().toString() +
                "&owner="+ etOwner.getText().toString() +
                "&location=" + spLocation.getSelectedItem().toString() +
                "26&description="+  etDescription.getText().toString() ;


    }

}
