package org.example.android.bostonbabynurse;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class ArticleContentActivity extends AppCompatActivity {

    private TextView contentTitle;
    private TextView contentText;
    private Toolbar toolbar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_content_view);

        activateToolbarWithHomeEnabled();

        Bundle b = getIntent().getExtras();
        String artTitle = b.getString("title");
        String artContent = b.getString("content");

        setTitle(artTitle);
        contentText = (TextView) findViewById(R.id.contentText);

        contentText.setText(artContent);
        contentText.setMovementMethod(new ScrollingMovementMethod());

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

    protected Toolbar activateToolbar() {
        if (toolbar == null) {
            toolbar = (Toolbar) findViewById(R.id.app_bar);
            if (toolbar != null) {
                setSupportActionBar(toolbar);
            }
        }
        return toolbar;
    }

    protected Toolbar activateToolbarWithHomeEnabled() {
        activateToolbar();
        if (toolbar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        return toolbar;

    }

}
