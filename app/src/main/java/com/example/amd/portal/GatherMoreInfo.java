package com.example.amd.portal;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Calendar;


/**
 * Created by buscis-c2-l3 on 9/18/2017.
 */

public class GatherMoreInfo extends AppCompatActivity implements AdapterView.OnItemSelectedListener, OnCheckedChangeListener
{
    private ArrayAdapter<CharSequence> adapter;

    private static final String FILE_NAME="config.txt";

    private TextView    user;

    private EditText    name,
                        address,
                        email;

    private String  language,
                    memberType;

    private Spinner spinner;

    private static EditText date,
                            time;

    private RadioButton rbEng,
                        rbSpan;

    private Button b;

    private Intent intent;

    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_info);

        date     = (EditText) findViewById(R.id.editTextDate);
        time     = (EditText) findViewById(R.id.editTextTime);

        rbEng    = (RadioButton)findViewById(R.id.radioButtonEnglish);
        rbSpan   = (RadioButton)findViewById(R.id.radioButtonSpanish);
        user     = (TextView)findViewById(R.id.user);
        name     = (EditText)findViewById(R.id.editTextName);
        address  = (EditText)findViewById(R.id.editTextAddress);
        email    = (EditText)findViewById(R.id.editTextEmail);

        spinner = (Spinner)findViewById(R.id.spinner);

        b = (Button)findViewById(R.id.button4);

        spinner.setOnItemSelectedListener(this);

        i = getIntent();
        user.setText(i.getStringExtra(MainActivity.PACKAGE_NAME));


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
    {
        memberType = spinner.getSelectedItem().toString();
    }

    public void onNothingSelected(AdapterView<?> parent)
    {
        //Do nothing.
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
            //Determine chosen language.
            if(rbSpan.isChecked())
            {
                language = "Spanish";
            }
            else
            {
                //Default to English.
                language = "English";
            }

            Log.d("member_type",memberType);

            //Save data to file first, then go to next Activity.
            writeToFile(memberType+","+language+","+name.getText().toString()+","+address.getText().toString()+","+email.getText().toString()+","+time.getText().toString()+","+date.getText().toString()+",",this);

            showInfo();
            
            intent = new Intent(this, PersonalActivity.class);
            startActivity(intent);
        }
        else
        {
            //Do nothing.
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


    //************************************************
    //*********** FILE I/O ***************************
    //************************************************

    private void writeToFile(String data,Context context)
    {
        OutputStreamWriter outputStreamWriter = null;

        try
        {
            outputStreamWriter = new OutputStreamWriter(context.openFileOutput(FILE_NAME, Context.MODE_APPEND));
            outputStreamWriter.write(data);

            Log.d("write_status","Wrote successfully");
        }
        catch (IOException e)
        {
            Log.e("Exception", "File write failed: " + e.toString());

        }
        finally
        {
            try
            {
                if(outputStreamWriter != null)
                {
                    outputStreamWriter.close();
                }
            }
            catch(IOException io)
            {

            }
        }
    }

    private String readFromFile(Context context)
    {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput(FILE_NAME);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e)
        {
            Log.e("login activity", "File not found: " + e.toString());
        }
        catch (IOException e)
        {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }


    private void showInfo()
    {
        StringBuilder sb = new StringBuilder();
        String[] temp = readFromFile(this).split(",");

        sb.append("Data Collected:"+"\r\n");

        for(int i=0;i<temp.length;i++)
        {
            sb.append(temp[i]+"\r\n");
        }

        Toast.makeText(this,sb.toString(),Toast.LENGTH_LONG).show();
    }

}
