package com.example.amd.portal;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.ArrayAdapter;
import android.widget.*;
import android.view.View;
import android.content.Intent;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.app.Dialog;
import android.widget.EditText;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by buscis-c2-l3 on 9/18/2017.
 */

public class GatherMoreInfo extends AppCompatActivity implements AdapterView.OnItemSelectedListener, OnCheckedChangeListener
{
    private ArrayAdapter<CharSequence> adapter;

    //private Spinner sp;

    private TextView    user;

    private EditText    name,
                        address,
                        email;

    private static EditText date,
                            time;

    private RadioButton rbEng,
                        rbSpan;

    private Button b;

    private Intent intent;

    private static final String ENGLISH = Locale.ENGLISH.toString(); //English language locale.
    private static final String SPANISH = ""; //Spanish language locale.

    private String selectedLanguage = "";

    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_info);

        date     = (EditText) findViewById(R.id.editTextDate);
        time     = (EditText) findViewById(R.id.editTextTime);

        //sp       = (Spinner)findViewById(R.id.spinner);
        rbEng    = (RadioButton)findViewById(R.id.rbEng);
        rbSpan   = (RadioButton)findViewById(R.id.rbSpan);
        user     = (TextView)findViewById(R.id.user);
        name     = (EditText)findViewById(R.id.editTextName);
        address  = (EditText)findViewById(R.id.editTextAddress);
        email    = (EditText)findViewById(R.id.editTextEmail);

        b = (Button)findViewById(R.id.button4);

        adapter = ArrayAdapter.createFromResource(this,R.array.dates,R.layout.support_simple_spinner_dropdown_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //sp.setAdapter(adapter);

        //sp.setOnItemSelectedListener(this);

        i = getIntent();
        user.setText(i.getStringExtra(MainActivity.PACKAGE_NAME));

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


    private void changeLanguage(String lang)
    {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
//        conf.locale = myLocale;
 //       res.updateConfiguration(conf, dm);
        onConfigurationChanged(conf);
    /*Intent refresh = new Intent(this, AndroidLocalize.class);
    startActivity(refresh);*/
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        // refresh your views here
        //lblLang.setText(R.string.langselection);
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {


        radioGroup.getCheckedRadioButtonId();

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
            selectedLanguage = Locale.getDefault().getDisplayLanguage();
        }

        changeLanguage(selectedLanguage);



    }

    public void showDatePickerDialog(View v)
    {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");

    }
    public void showTimePickerDialog(View v)
    {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");

    }

    public void sendMessage(View v)
    {

        if( v.getId() == b.getId() )
        {
            intent = new Intent(this, PersonalActivity.class);
            startActivity(intent);
        }
        else
        {
            //Do nothing.
        }

    }


    //Save to name, email, and address to file.  Time and date?  Append the data, not overwrite.
    private void saveFile()
    {
        String path= "";
        String filename = "";
        File f;
        BufferedWriter writer = null;

        String sName, sAddress, sEmail;

        sName       = name.getText().toString();
        sAddress    = address.getText().toString();
        sEmail      = email.getText().toString();

        try
        {
            f = new File(path);
            writer = new BufferedWriter(new FileWriter(f));


            writer.write(sName);
            writer.newLine();
            writer.write(sAddress);
            writer.newLine();
            writer.write(sEmail);
            writer.newLine();




        }
        catch(Exception e)
        {

        }
        finally
        {
            try{if(writer != null)writer.close();}catch(Exception x){}
        }
    }



    //DATE PICKER INNER CLASS.
    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);



            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            date.setText(month+"/"+day+"/"+year);

        }


    }

    //TIME PICKER INNER CLASS.
    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    false);
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user

            String ampm;

            if(hourOfDay > 12)
            {
                ampm = "pm";
                hourOfDay -= 12;
            }
            else
            {
                ampm = "am";
            }


            time.setText(hourOfDay+":"+minute+ampm);
        }
    }
}
