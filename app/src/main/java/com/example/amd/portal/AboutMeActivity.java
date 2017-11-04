package com.example.amd.portal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

//import com.example.amd.testapp.R;

public class AboutMeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.activity_main2,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.LinkToHome:
                startActivity(new Intent(this,MainActivity.class));
                return true;
            case R.id.LinkToApp://Link to appp.
                startActivity(new Intent(this,PersonalActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }
}
