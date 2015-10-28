package org.example.android.bostonbabynurse;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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

public class MessageContentActivity extends AppCompatActivity {

    private TextView contentTitle;
    private TextView contentText;
    private static String sUserId;
    private String parentId;

    private Button btReply;
    private EditText replyText;
    private ListView lvReplyChat;
    private ArrayList<Reply> mReplies;
    private boolean mFirstLoad;
    private ReplyListAdapter mReplyAdapter;
    public static final String USER_ID_KEY = "userId";

    private Handler handler = new Handler();

    private static final int MAX_CHAT_MESSAGES_TO_SHOW = 50;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_content_view);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle b = getIntent().getExtras();

        String msgTitle = b.getString("reply");
        String msgContent = b.getString("reply");

        setTitle(msgTitle);

        contentText = (TextView) findViewById(R.id.contentUserID);
        contentTitle = (TextView) findViewById(R.id.contentBody);

        contentText.setText(msgContent);
        contentText.setMovementMethod(new ScrollingMovementMethod());
        contentTitle.setText(msgTitle);

        sUserId = ParseUser.getCurrentUser().getObjectId();

        setupMessagePosting();

        handler.postDelayed(runnable, 100);
    }

    // Defines a runnable which is run every 100ms
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            refreshMessages();
            handler.postDelayed(this, 100);
        }
    };

    private void refreshMessages() {
        receiveMessage();
    }

    // Setup button event handler which posts the entered message to Parse
    private void setupMessagePosting() {
        // Find the text field and button
        replyText = (EditText) findViewById(R.id.etReplyContent);
        btReply = (Button) findViewById(R.id.btReply);
        // When send button is clicked, create message object on Parse

        lvReplyChat = (ListView) findViewById(R.id.lvReplyChat);
        mReplies = new ArrayList<>();
        // Automatically scroll to the bottom when a data set change notification is received and only if the last item is already visible on screen. Don't scroll to the bottom otherwise.
        lvReplyChat.setTranscriptMode(1);
        mFirstLoad = true;
        Bundle b = getIntent().getExtras();
        final String parentId = b.getString("objectID");
        mReplyAdapter = new ReplyListAdapter(MessageContentActivity.this, sUserId, parentId, mReplies);
        lvReplyChat.setAdapter(mReplyAdapter);
        btReply.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {

                String mReply = replyText.getText().toString();
                ParseUser currentUser = ParseUser.getCurrentUser();
                String username = currentUser.getString("username");

                ParseObject reply = ParseObject.create("Reply");
                reply.put("username", "Created by: " + username);
                reply.put("parent", parentId);
                reply.put(USER_ID_KEY, sUserId);
                reply.put("reply", mReply);

                reply.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        Toast.makeText(MessageContentActivity.this, "Successfully created a reply on Parse", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // Set on click listeners for the article items
//        lvReplyChat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                selectMessage(position);
//            }
//        });
    }


    protected void selectMessage(int position) {
        Intent intent = new Intent(MessageContentActivity.this, ReplyActivity.class);
        lvReplyChat.setItemChecked(position, true);

        Bundle b = new Bundle();
        b.putString("title", mReplies.get(position).getMessageTitle());
        b.putString("username", mReplies.get(position).getUsername());
        b.putString("reply", mReplies.get(position).getReply());
        b.putString("objectID", mReplies.get(position).getObjectId());

        intent.putExtras(b); // Put your id to your next Intent

        startActivity(intent);
        finish();
    }

    private void receiveMessage() {
        // Construct query to execute
        ParseQuery<Reply> query = ParseQuery.getQuery(Reply.class);
        // Configure limit and sort order
        query.setLimit(MAX_CHAT_MESSAGES_TO_SHOW);
        query.orderByAscending("createdAt");
        // Execute query to fetch all messages from Parse asynchronously
        // This is equivalent to a SELECT query with SQL
        query.findInBackground(new FindCallback<Reply>() {
            public void done(List<Reply> replies, ParseException e) {
                if (e == null) {
                    mReplies.clear();
                    mReplies.addAll(replies);
                    mReplyAdapter.notifyDataSetChanged(); // update adapter
                    // Scroll to the bottom of the list on initial load
                    if (mFirstLoad) {
                        lvReplyChat.setSelection(mReplyAdapter.getCount() - 1);
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

        int id = item.getItemId();

        if (id == android.R.id.home) {
            Intent myIntent = new Intent(getApplicationContext(), ForumActivity.class);
            startActivityForResult(myIntent, 0);
            return true;
        }

        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(this, "Successfully logged out", Toast.LENGTH_SHORT).show();
                ParseUser.logOut();
                Intent intent = new Intent(MessageContentActivity.this, LoginSignupActivity.class);
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