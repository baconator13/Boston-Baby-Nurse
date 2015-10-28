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

import com.squareup.picasso.Picasso;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.List;


public class ReplyListAdapter extends ArrayAdapter<Reply> {
    private String mUserId;
    private String mParentId;

    public ReplyListAdapter(Context context, String userId, String parentId, List<Reply> replies) {
        super(context, 0, replies);
        this.mUserId = userId;
        this.mParentId = parentId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.reply_item, parent, false);
            final ViewHolder holder = new ViewHolder();
            holder.imageLeft = (ImageView) convertView.findViewById(R.id.ivProfileLeft);
            holder.imageRight = (ImageView) convertView.findViewById(R.id.ivProfileRight);
            holder.body = (TextView) convertView.findViewById(R.id.tvBody);
            holder.date = (TextView) convertView.findViewById(R.id.tvDate);
            holder.reply = (TextView) convertView.findViewById(R.id.tvReply);
            holder.username = (TextView) convertView.findViewById(R.id.tvUsername);
            convertView.setTag(holder);
        }

        final Reply reply = getItem(position);
        final ViewHolder holder = (ViewHolder) convertView.getTag();
        String test = reply.getString("userId");
        Log.v("userId", test);
        final boolean isChild = reply.getString("parent").equals(mParentId);
        Log.v("mUserId", mUserId);

        // Show-hide reply based on whether or not it belongs to the parent comment.
        if (isChild) {
            holder.imageLeft.setVisibility(View.VISIBLE);
            holder.imageRight.setVisibility(View.GONE);
            holder.reply.setVisibility(View.VISIBLE);
            holder.date.setVisibility(View.VISIBLE);
            holder.reply.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            holder.date.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);

        } else {
            holder.reply.setVisibility(View.GONE);
            holder.imageLeft.setVisibility(View.GONE);
            holder.imageRight.setVisibility(View.GONE);
            holder.date.setVisibility(View.GONE);
            holder.username.setVisibility(View.GONE);
            holder.reply.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        }

        final ImageView profileView = isChild ? holder.imageRight : holder.imageLeft;
        Picasso.with(getContext()).load(getProfileUrl(reply.getUserId())).into(profileView);
        holder.reply.setText(reply.getReply());
        holder.date.setText(reply.getCreatedAt().toString());
        holder.username.setText(reply.getUsername().toString());

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
        public TextView reply;
    }
}
