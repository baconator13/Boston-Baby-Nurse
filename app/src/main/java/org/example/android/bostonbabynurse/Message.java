package org.example.android.bostonbabynurse;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Message")
public class Message extends ParseObject {

    public String getUserId() {
        return getString("userId");
    }

    public String getMessageTitle() { return getString("title"); }

    public String getContent() {
        return getString("content");
    }

    public String getUsername() { return getString("username"); }

    public String getReply() { return getString("reply");}


    public void setUserId(String userId) {
        put("userId", userId);
    }

    public void setBody(String body) {
        put("body", body);
    }

}