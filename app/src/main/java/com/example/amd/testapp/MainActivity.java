package com.example.amd.testapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.graphics.Color;
import java.lang.*;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private int MIN_LENGTH;
    private int MAX_LENGTH;

    private String  username,
                    password,
                    defaultUsername,        //used to show password EditText is working.
                    defaultPassword;        //used to show username EditText is working.

    private EditText    e1,
                        e2,
                        e3,
                        e4;

    private TextView    tv2,
                        tv4;

    private Button  b1,
                    b2,
                    submitButton;

    private int  red     = Color.RED;
    private int  green   = Color.GREEN;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MIN_LENGTH = getResources().getInteger(R.integer.minLength);
        MAX_LENGTH = getResources().getInteger(R.integer.maxLength);

        initRefs();
        setListeners();

        e2.setFocusable(false);
        e4.setFocusable(false);

        defaultUsername = "7zWeeeee";
        defaultPassword = "1aB$aaaa";

        e1.setText(defaultUsername);
        e3.setText(defaultPassword);

    }

    //Show/hide "valid? password"
    private void toggleValidPassword(int x)
    {
        tv4.setVisibility(x);
        e4.setVisibility(x);
    }

    //Show/hide "valid? username"
    private void toggleValidUsername(int x)
    {
        tv2.setVisibility(x);
        e2.setVisibility(x);
    }

    private void initRefs()
    {
        e1 = (EditText)findViewById(R.id.editText1);
        e2 = (EditText)findViewById(R.id.editText2);
        e3 = (EditText)findViewById(R.id.editText3);
        e4 = (EditText)findViewById(R.id.editText4);

        b1 = (Button)findViewById(R.id.button1);
        b2 = (Button)findViewById(R.id.button2);
        submitButton = (Button)findViewById(R.id.submitButtonId);

        tv2 = (TextView)findViewById(R.id.textView2);
        tv4 = (TextView)findViewById(R.id.textView4);
    }

    private void setListeners()
    {
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                username = e1.getText().toString();
                password = e3.getText().toString();

                toggleValidUsername(View.VISIBLE);
                toggleValidPassword(View.VISIBLE);

                if(isValidUsername(username))
                {
                    e2.setTextColor(green);
                    e2.setText(R.string.good);
                }
                else
                {
                    e2.setTextColor(red);
                    e2.setText(R.string.bad);
                }

                if(isValidPassword(password))
                {
                    e4.setTextColor(green);
                    e4.setText(R.string.good);
                }
                else
                {
                    e4.setTextColor(red);
                    e4.setText(R.string.bad);
                }
            }
        });

        //Reset.
        b2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                //blank the username and password EditTexts.
                e1.setText(defaultUsername);
                e2.setText("");
                e3.setText(defaultPassword);
                e4.setText("");

                //re-hide the validations.
                toggleValidUsername(View.INVISIBLE);
                toggleValidPassword(View.INVISIBLE);
            }

        });

        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                username = e1.getText().toString();
                password = e3.getText().toString();

                if(isMatch(defaultUsername+defaultPassword,username+password))
                {
                    //Notify user of correct password.
                    msg(getString(R.string.correct_username_password));

                    //Start GatherMoreInfo activity.
                    intent = new Intent(MainActivity.this, GatherMoreInfo.class);

                    intent.putExtra("username",username);


                    startActivity(intent);

                }
                else
                {
                    //Notify user of bad password.
                    msg(getString(R.string.incorrect_username_password));
                }
            }

        });
    }

    private boolean isValidPassword(String in)
    {
        /*          PASSWORD may contain only:
        *   lowercase letters
        *   uppercase letters
        *   numbers
        *   special char: $!#
        *   has a minimum length of 8 characters
        *   has a maximum length of 10 characters
        */

        Pattern hasLowercase = Pattern.compile("[a-z]");
        Pattern hasUppercase = Pattern.compile("[A-Z]");
        Pattern hasNumber    = Pattern.compile("[0-9]");
        Pattern hasSpecial   = Pattern.compile("\\$|!|#");

        char chars[] = in.toCharArray();

        if (in.length() >= MIN_LENGTH && in.length() <= MAX_LENGTH)
        {
            for (int i = 0; i < chars.length; i++)
            {

                if (    hasLowercase.matcher(Character.toString(chars[i])).matches()   ||
                        hasUppercase.matcher(Character.toString(chars[i])).matches()   ||
                        hasNumber.matcher(Character.toString(chars[i])).matches()      ||
                        hasSpecial.matcher(Character.toString(chars[i])).matches()
                   )
                {

                }
                else
                {
                    return false;
                }
            }

            return true;

        }
        else
        {
            return false;
        }

    }

    private boolean isValidUsername(String in)
    {
        /*          Username may contain only:
        *   lowercase letters
        *   uppercase letters
        *   numbers
        *   has a minimum length of 8 characters
        *   has a maximum length of 10 characters
        */

        Pattern hasLowercase = Pattern.compile("[a-z]");
        Pattern hasUppercase = Pattern.compile("[A-Z]");
        Pattern hasNumber = Pattern.compile("[0-9]");

        char chars[] = in.toCharArray();

        if (in.length() >= MIN_LENGTH && in.length() <= MAX_LENGTH)
        {
            for (int i = 0; i < chars.length; i++)
            {

                if (    hasLowercase.matcher(Character.toString(chars[i])).matches() ||
                        hasUppercase.matcher(Character.toString(chars[i])).matches() ||
                        hasNumber.matcher(Character.toString(chars[i])).matches()
                   )
                {

                }
                else
                {
                    return false;
                }
            }

            return true;

        }
        else
        {
            return false;
        }

    }

    private boolean isMatch(String in1, String in2)
    {
        return in1.equals(in2);
    }

    public void msg(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
