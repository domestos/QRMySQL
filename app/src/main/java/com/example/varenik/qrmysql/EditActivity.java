package com.example.varenik.qrmysql;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EditActivity extends AppCompatActivity {
    private TextView tvId;
    private TextView tvNumber;
    private EditText etOwner;
    private JSONObject JO;
    private Spinner spLocation;
    private String[] arrayLocation = {"","QA Red", "Administration", "BB", "Meeting Room", "QA Black", "QA Green", "QA White", "SMU", "Server Room", "Test room", "Training Room", "WAA", "Warehouse"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        ArrayAdapter<String> locations = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayLocation);
        locations.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        tvId = (TextView) findViewById(R.id.tvId);
        tvNumber = (TextView) findViewById(R.id.tvNumber);
        etOwner = (EditText) findViewById(R.id.etOwner);

        spLocation = (Spinner) findViewById(R.id.spLocation);
        spLocation.setAdapter(locations);

       //  spLocation.setSelection(2);
        Log.e("TAG_location", "create");

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
                    while (arrayLocation.length > i){
                        localtion=singItem.getString("location");
                        chek = arrayLocation[i];

                        if(localtion.equals(chek)){
                            spLocation.setSelection(i);
                                                 }
                        Log.d("TAG_location", localtion +" = "+arrayLocation[i]);
                        i++;
                    }

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }



    public void getValues(String sb){

        if(Const.saveInfoItem != null) {


            try {
                JO = new JSONObject(sb);
                JSONArray JA = new JSONArray(JO.get("product").toString());
                if (JA.length() == 1) {
                    JSONObject singItem = (JSONObject) JA.get(0);
                    tvId.setText(singItem.getString("id"));
                    tvNumber.setText(singItem.getString("number"));
                    etOwner.setText(singItem.getString("owner"));

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

}
