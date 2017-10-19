package com.example.varenik.qrmysql;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private Button btnSearche;
    private Button btnEdit;
    public TextView tvUpdate = null;
    private ProgressBar progressBar;
    private ConnectToURLFragment connectToURLFragment;
    private String TAG_FRAGMENT = "TAG_URL_connect";
    private EditText etInventNumber;
    public static TextView tvInfoItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //==============Fragment =================
        tvUpdate = (TextView) findViewById(R.id.tvUpdate);
        btnSearche = (Button) findViewById(R.id.btnSearche);
        btnEdit = (Button) findViewById(R.id.btnEdit);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnEdit.setOnClickListener(this);
        btnSearche.setOnClickListener(this);
        //==============Fragment =================

        etInventNumber = (EditText) findViewById(R.id.etInventNumber);
        tvInfoItem = (TextView) findViewById(R.id.tvInfoItem);

        connectToURLFragment = getConnectToURLFragment();
        //Передаємо об"єкту MainFragment актівіті
        connectToURLFragment.link(this);
        // визначаємо в якому стані AsyncTask
        // mainFragment.getIsWork() - повертає true якщо виконується процес, false - якщо він в стані спокою
        showProgress(connectToURLFragment.getIsWork());



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                IntentIntegrator scanIntegrator = new IntentIntegrator(MainActivity.this);
                scanIntegrator.initiateScan();

            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Action", null).show();
            }
        });

    }


    // Fragment запускає цей метод (показує стан AsyncTasks)
    public void showProgress(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }


    public ConnectToURLFragment getConnectToURLFragment() {
        //перевіряємо чи існує фрагмент і присвоюємо результат перевірки змінній
        connectToURLFragment = (ConnectToURLFragment) getFragmentManager().findFragmentByTag(TAG_FRAGMENT);
        //якщо перевірка верне null, то означає,
        // що фрагмент раніше не був створений і його нобхідно створити
        if (connectToURLFragment == null) {
            connectToURLFragment = new ConnectToURLFragment();
            //записуємо створений екземпляр MainFragment у FragmentManager
            getFragmentManager().beginTransaction().add(connectToURLFragment, TAG_FRAGMENT).commit();
        }
        tvInfoItem.setText(Const.saveInfoItem);
        return connectToURLFragment;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        // завершує роботу AsyncTask якщо нажата кнопка Back
        if (isFinishing()) {
            connectToURLFragment.stop();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSearche:
                showProgress(true);
                connectToURLFragment.startGetJSON(Const.URL_ADDRESS + "'" + etInventNumber.getText().toString() + "'");


                break;

            // tvUpdate.setText(connectToURLFragment.getURLRequest());


            case R.id.btnEdit:
                Intent intent = new Intent(this,EditActivity.class);
                startActivity(intent);
                break;
        }
    }


    // Get the results:
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                etInventNumber.setText(result.getContents());
                connectToURLFragment.startGetJSON(Const.URL_ADDRESS + "'" + etInventNumber.getText().toString() + "'");

                //Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }




}
