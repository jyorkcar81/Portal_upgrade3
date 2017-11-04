package com.example.amd.portal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RatingBar;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuItem;



public class PersonalActivity extends AppCompatActivity {

    private RatingBar rb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        rb = (RatingBar)findViewById(R.id.ratingBar);

        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean b) {
                Toast.makeText(PersonalActivity.this,"Rated: "+rating,Toast.LENGTH_LONG).show();
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.activity_main,menu);
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
            case R.id.LinkToApp://About app.
                startActivity(new Intent(this,PersonalActivity.class));
                return true;
            case R.id.AboutYouApp://Link to BIO.
                startActivity(new Intent(this,AboutMeActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }
}
