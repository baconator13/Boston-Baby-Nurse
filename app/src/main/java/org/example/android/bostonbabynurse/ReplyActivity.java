package org.example.android.bostonbabynurse;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseUser;

/**
 * Created by alexanderarsenault on 10/28/15.
 */
public class ReplyActivity extends AppCompatActivity {

    private TextView tvMessageTitle;
    private TextView tvMessageContent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reply_view);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle b = getIntent().getExtras();

        String msgTitle = b.getString("title");
        String msgContent = b.getString("reply");

        tvMessageContent = (TextView) findViewById(R.id.tvMessageContent);
        tvMessageTitle = (TextView) findViewById(R.id.tvMessageTitle);


        tvMessageTitle.setText(msgTitle);
        tvMessageContent.setText(msgContent);


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

        if (id == android.R.id.home) {
            Intent myIntent = new Intent(getApplicationContext(), MessageContentActivity.class);

            Bundle b = new Bundle();
            b.putString("title", b.getString("title"));
            b.putString("reply", b.getString("reply"));

            myIntent.putExtras(b); // Put your id to your next Intent


            startActivityForResult(myIntent, 0);
            return true;
        }

        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(this, "Successfully logged out", Toast.LENGTH_SHORT).show();
                ParseUser.logOut();
                Intent intent = new Intent(ReplyActivity.this, LoginSignupActivity.class);
                startActivity(intent);
                finish();
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ForumActivity.class);
        startActivity(intent);
    }
}
