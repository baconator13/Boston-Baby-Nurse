package org.example.android.bostonbabynurse;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;


public class LoginSignupActivity extends Activity {
    // Declare Variables
    Button loginbutton;
    Button signup;
    String usernametxt;
    String passwordtxt;
    EditText password;
    EditText username;

    /**
     * Called when the activity is first created.
     */
    public void onCreate(Bundle savedInstanceState) {
        Log.v("LoginActivity", "Reached");
        super.onCreate(savedInstanceState);
        // Get the view from main.xml
        setContentView(R.layout.login_signup_view);
        // Locate EditTexts in main.xml
        username = (EditText) findViewById(R.id.username);
        username.getBackground().setColorFilter(getResources().getColor(R.color.md_white_1000), PorterDuff.Mode.SRC_ATOP);
        password = (EditText) findViewById(R.id.password);
        password.getBackground().setColorFilter(getResources().getColor(R.color.md_white_1000), PorterDuff.Mode.SRC_ATOP);
        TextView signup = (TextView) findViewById(R.id.signup);
        TextView forgotPassword = (TextView) findViewById(R.id.forgotpassword);
        password.setTransformationMethod(new PasswordTransformationMethod());

        // Locate Buttons in LoginSignupActivity.xml
        loginbutton = (Button) findViewById(R.id.login);

        // Login Button Click Listener
        loginbutton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // Retrieve the text entered from the EditText
                usernametxt = username.getText().toString();
                passwordtxt = password.getText().toString();

                // Send data to Parse.com for verification
                ParseUser.logInInBackground(usernametxt, passwordtxt,
                        new LogInCallback() {
                            public void done(ParseUser user, ParseException e) {
                                if (user != null) {
                                    // If user exist and authenticated, send user to ChatActivity.class
                                    Intent intent = new Intent(LoginSignupActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    Toast.makeText(getApplicationContext(), "Successfully Logged in", Toast.LENGTH_LONG).show();
                                    finish();
                                } else {
                                    Toast.makeText(
                                            getApplicationContext(), "Incorrect username or password", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginSignupActivity.this, SignupForm.class);
                startActivity(intent);
            }

        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginSignupActivity.this, ForgotPassword.class);
                startActivity(intent);
            }

        });


    }


}


