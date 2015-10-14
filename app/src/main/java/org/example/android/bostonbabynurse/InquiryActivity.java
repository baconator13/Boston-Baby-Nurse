package org.example.android.bostonbabynurse;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InquiryActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private Button mSubmitBtn;
    private EditText mFirstName;
    private EditText mLastName;
    private EditText mEmail;
    private EditText mMessage;

    private String firstName, lastName, Email, Message;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inquiry_view);
        setTitle("Reach out to Boston Baby Nurse");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSubmitBtn = (Button) findViewById(R.id.submitBtn);
        mFirstName = (EditText) findViewById(R.id.firstNameTxt);
        mLastName = (EditText) findViewById(R.id.lastNameTxt);
        mEmail = (EditText) findViewById(R.id.emailTxt);
        mMessage = (EditText) findViewById(R.id.messageTxt);

        mSubmitBtn.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        firstName = mFirstName.getText().toString();
                        lastName = mLastName.getText().toString();
                        Email = mEmail.getText().toString();
                        Message = mMessage.getText().toString();

                        Intent submitIntent = new Intent(Intent.ACTION_SEND);
                        submitIntent.setType("message/rfc822");
                        submitIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"alexarsenault92@gmail.com"});
                        submitIntent.putExtra(Intent.EXTRA_SUBJECT, "New BBN Inquiry from App");
                        submitIntent.putExtra(Intent.EXTRA_TEXT, "Inquiry from: " + firstName + " " + lastName + "\n\n" + "Return email: " + Email + "\n\n" + Message);


                        try {
                            startActivity(Intent.createChooser(submitIntent, "Send mail..."));
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(InquiryActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        //inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivityForResult(myIntent, 0);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
