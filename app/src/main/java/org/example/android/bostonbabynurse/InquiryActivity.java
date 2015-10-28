package org.example.android.bostonbabynurse;


import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.parse.ParseUser;

public class InquiryActivity extends MainActivity {

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
        setTitle("Reach Out");

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mDrawerPane = (RelativeLayout) findViewById(R.id.drawerPane);
        mDrawerList = (ListView) findViewById(R.id.navList);
        MainActivity.DrawerListAdapter adapter = new MainActivity.DrawerListAdapter(this, mNavItems);
        mDrawerList.setAdapter(adapter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

                invalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        // Drawer Item click listeners
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItemFromDrawer(position);
            }
        });

        mSubmitBtn = (Button) findViewById(R.id.submitBtn);

        mEmail = (EditText) findViewById(R.id.emailTxt);
        mMessage = (EditText) findViewById(R.id.messageTxt);

        mEmail.getBackground().setColorFilter(getResources().getColor(R.color.md_grey_300), PorterDuff.Mode.SRC_ATOP);
        mMessage.getBackground().setColorFilter(getResources().getColor(R.color.md_grey_300), PorterDuff.Mode.SRC_ATOP);
        mMessage.setGravity(Gravity.TOP | Gravity.LEFT);

        mSubmitBtn.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        //firstName = mFirstName.getText().toString();
                        //lastName = mLastName.getText().toString();
                        Email = mEmail.getText().toString();
                        Message = mMessage.getText().toString();

                        Intent submitIntent = new Intent(Intent.ACTION_SEND);
                        submitIntent.setType("message/rfc822");
                        submitIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"alexarsenault92@gmail.com"});
                        submitIntent.putExtra(Intent.EXTRA_SUBJECT, "New BBN Inquiry from App");
                        submitIntent.putExtra(Intent.EXTRA_TEXT, Email + "\n\n" + Message);


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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

//        if (id == android.R.id.home) {
//            Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
//            startActivityForResult(myIntent, 0);
//            return true;
//        }

        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(this, "Successfully logged out", Toast.LENGTH_SHORT).show();
                ParseUser.logOut();
                Intent intent = new Intent(InquiryActivity.this, LoginSignupActivity.class);
                startActivity(intent);
                finish();
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
