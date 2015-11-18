package org.example.android.bostonbabynurse;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class ForumActivity extends MainActivity {

    private static final String TAG = ForumActivity.class.getName();
    private static String sUserId;
    public static final String USER_ID_KEY = "userId";

    private EditText etMessageTitle;
    private EditText etMessageContent;
    private Button btSend;
    private TextView profileUser;

    private ListView lvChat;
    private ArrayList<Message> mMessages;
    private ChatListAdapter mAdapter;

    // Keep track of initial load to scroll to the bottom of the ListView
    private boolean mFirstLoad;

    private static final int MAX_CHAT_MESSAGES_TO_SHOW = 50;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum_view);
        setTitle("Community Forum");
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

        profileUser = (TextView) findViewById(R.id.mainTitle);

        ParseUser currentUser = ParseUser.getCurrentUser();
        profileUser.setText(currentUser.getString("name"));

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        // Drawer Item click listeners
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItemFromDrawer(position);
            }
        });

        sUserId = ParseUser.getCurrentUser().getObjectId();
        Log.v("userId*****", sUserId.toString());
        setupMessagePosting();

        refreshMessages();

    }


    private void refreshMessages() {
        receiveMessage();
    }


    // Setup button event handler which posts the entered message to Parse
    private void setupMessagePosting() {
        // Find the text field and button
        etMessageTitle = (EditText) findViewById(R.id.etMessage);
        etMessageContent = (EditText) findViewById(R.id.etMessageContent);
        btSend = (Button) findViewById(R.id.btSend);
        // When send button is clicked, create message object on Parse

        lvChat = (ListView) findViewById(R.id.lvChat);
        mMessages = new ArrayList<>();
        // Automatically scroll to the bottom when a data set change notification is received and only if the last item is already visible on screen. Don't scroll to the bottom otherwise.
        lvChat.setTranscriptMode(1);
        mFirstLoad = true;
        mAdapter = new ChatListAdapter(ForumActivity.this, sUserId, mMessages);
        lvChat.setAdapter(mAdapter);
        btSend.setOnClickListener(new Button.OnClickListener() {


            @Override
            public void onClick(View v) {
                String mMessage = etMessageTitle.getText().toString();
                String mMessageContent = etMessageContent.getText().toString();

                if (mMessage.isEmpty() || mMessageContent.isEmpty()) {
                    Toast.makeText(ForumActivity.this, "Please enter both a title and message",
                            Toast.LENGTH_SHORT).show();
                } else {

                    ParseUser currentUser = ParseUser.getCurrentUser();
                    String username = currentUser.getString("username");

                    ParseObject message = ParseObject.create("Message");
                    message.put("username", "Posted by: " + username);
                    message.put(USER_ID_KEY, sUserId);
                    message.put("title", mMessage);
                    message.put("content", mMessageContent);
                    message.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            Toast.makeText(ForumActivity.this, "Successfully created message on Parse",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                    etMessageTitle.setText("");
                }
                refreshMessages();
            }
        });


        // Set on click listeners for the article items
        lvChat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectMessage(position);
            }
        });
    }


    protected void selectMessage(int position) {
        Intent intent = new Intent(ForumActivity.this, MessageContentActivity.class);
        lvChat.setItemChecked(position, true);

        Bundle b = new Bundle();
        b.putString("title", mMessages.get(position).getMessageTitle());
        b.putString("username", mMessages.get(position).getUsername());
        b.putString("content", mMessages.get(position).getContent());
        b.putString("objectID", mMessages.get(position).getObjectId());

        intent.putExtras(b); // Put your id to your next Intent

        startActivity(intent);
        finish();
    }

    private void receiveMessage() {
        // Construct query to execute
        ParseQuery<Message> query = ParseQuery.getQuery(Message.class);
        // Configure limit and sort order
        query.setLimit(MAX_CHAT_MESSAGES_TO_SHOW);
        query.orderByAscending("createdAt");
        // Execute query to fetch all messages from Parse asynchronously
        // This is equivalent to a SELECT query with SQL
        query.findInBackground(new FindCallback<Message>() {
            public void done(List<Message> messages, ParseException e) {
                if (e == null) {
                    mMessages.clear();
                    mMessages.addAll(messages);
                    mAdapter.notifyDataSetChanged(); // update adapter
                    // Scroll to the bottom of the list on initial load
                    if (mFirstLoad) {
                        lvChat.setSelection(mAdapter.getCount() - 1);
                        mFirstLoad = false;
                    }
                } else {
                    Log.d("message", "Error: " + e.getMessage());
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        if (id == android.R.id.home) {
            Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivityForResult(myIntent, 0);
            return true;
        }

        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(this, "Successfully logged out", Toast.LENGTH_SHORT).show();
                ParseUser.logOut();
                Intent intent = new Intent(ForumActivity.this, LoginSignupActivity.class);
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