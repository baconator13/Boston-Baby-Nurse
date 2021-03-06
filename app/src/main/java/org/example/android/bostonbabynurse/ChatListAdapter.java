package org.example.android.bostonbabynurse;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.List;


public class ChatListAdapter extends ArrayAdapter<Message> {
    private String mUserId;

    public ChatListAdapter(Context context, String userId, List<Message> messages) {
        super(context, 0, messages);
        this.mUserId = userId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.chat_item, parent, false);
            final ViewHolder holder = new ViewHolder();
            //holder.imageLeft = (ImageView)convertView.findViewById(R.id.ivProfileLeft);
            //holder.imageRight = (ImageView)convertView.findViewById(R.id.ivProfileRight);
            holder.body = (TextView)convertView.findViewById(R.id.tvBody);
            holder.date = (TextView)convertView.findViewById(R.id.tvDate);
            holder.username = (TextView)convertView.findViewById(R.id.tvUsername);
            convertView.setTag(holder);
        }
        final Message message = (Message)getItem(position);
        final ViewHolder holder = (ViewHolder)convertView.getTag();
        String test = message.getString("userId");
        Log.v("userId", test);
        final boolean isMe = message.getString("userId").equals(mUserId);
//        Log.v("getUserID", muID);
        Log.v("mUserId", mUserId);

//        final boolean isMe = true;
        // Show-hide image based on the logged-in user.
        // Display the profile image to the right for our user, left for other users.
        if (isMe) {
            //holder.imageRight.setVisibility(View.VISIBLE);
            //holder.imageLeft.setVisibility(View.GONE);
            holder.body.setGravity(Gravity.LEFT | Gravity.LEFT);
            holder.date.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);

        } else {
            //holder.imageRight.setVisibility(View.GONE);
            //holder.imageLeft.setVisibility(View.VISIBLE);
            holder.body.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        }
        //final ImageView profileView = isMe ? holder.imageRight : holder.imageLeft;
        //Picasso.with(getContext()).load(getProfileUrl(message.getUserId())).into(holder.imageRight);
        holder.body.setText(message.getMessageTitle());
        holder.date.setText(message.getCreatedAt().toString());
        holder.username.setText(message.getUsername().toString());
        return convertView;
    }

    // Create a gravatar image based on the hash value obtained from userId
    private static String getProfileUrl(final String userId) {
        String hex = "";
        try {
            final MessageDigest digest = MessageDigest.getInstance("MD5");
            final byte[] hash = digest.digest(userId.getBytes());
            final BigInteger bigInt = new BigInteger(hash);
            hex = bigInt.abs().toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "http://www.gravatar.com/avatar/" + hex + "?d=identicon";
    }

    final class ViewHolder {
        public ImageView imageLeft;
        public ImageView imageRight;
        public TextView body;
        public TextView date;
        public TextView username;
    }

}