package com.example.amd.testapp;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.ArrayAdapter;
import android.widget.*;
import android.view.View;
import android.content.Intent;

import java.util.Locale;

/**
 * Created by buscis-c2-l3 on 9/18/2017.
 */

public class GatherMoreInfo extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    private ArrayAdapter<CharSequence> adapter;

    private Spinner sp;

    private TextView    date,
                        username;

    private RadioButton rbEng,
                        rbSpan;

    private static final String ENGLISH = Locale.ENGLISH.toString(); //English language locale.
    private static final String SPANISH = ""; //Spanish language locale.

    private String selectedLanguage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_info);

        date     = (TextView)findViewById(R.id.dateLabelId);
        username = (TextView)findViewById(R.id.textViewUsernameId);
        sp       = (Spinner)findViewById(R.id.spinner);
        rbEng    = (RadioButton)findViewById(R.id.rbEng);
        rbSpan   = (RadioButton)findViewById(R.id.rbSpan);


        adapter = ArrayAdapter.createFromResource(this,R.array.dates,R.layout.support_simple_spinner_dropdown_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp.setAdapter(adapter);

        sp.setOnItemSelectedListener(this);

        username.setText(getIntent().getStringExtra("username"));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
    {
        date.setText(parent.getItemAtPosition(pos).toString());
    }

    public void onNothingSelected(AdapterView<?> parent)
    {
        //Do nothing.
    }

    public void doSomething()
    {
        if(rbEng.isChecked())
        {
            //Set language to English.
            selectedLanguage = ENGLISH;
        }
        else if(rbSpan.isChecked())
        {
            //Set language to Spanish.
            selectedLanguage = SPANISH;
        }
        else
        {
           //Default: leave language setting as-is on device.
        }

        changeLanguage(selectedLanguage);
    }

    private void changeLanguage(String lang)
    {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        onConfigurationChanged(conf);
    /*Intent refresh = new Intent(this, AndroidLocalize.class);
    startActivity(refresh);*/
    }
        @Override
        public void onConfigurationChanged(Configuration newConfig) {
        // refresh your views here
        //lblLang.setText(R.string.langselection);
        super.onConfigurationChanged(newConfig);
    }

}
