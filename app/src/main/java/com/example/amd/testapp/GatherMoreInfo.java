package com.example.amd.testapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.*;
import android.view.View;
import android.content.Intent;

/**
 * Created by buscis-c2-l3 on 9/18/2017.
 */

public class GatherMoreInfo extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    private ArrayAdapter<CharSequence> adapter;

    private Spinner sp;

    private TextView    date,
                        username;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_info);

        date = (TextView)findViewById(R.id.dateLabelId);
        username = (TextView)findViewById(R.id.textViewUsernameId);
        sp = (Spinner)findViewById(R.id.spinner);

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

}
