package org.example.android.bostonbabynurse;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by alexanderarsenault on 10/25/15.
 */
public class ForgotPassword extends AppCompatActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password_view);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
