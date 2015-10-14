package org.example.android.bostonbabynurse;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by alexanderarsenault on 10/6/15.
 */
public class BaseActivity extends AppCompatActivity {

    protected ListView mDrawerList;
    protected RelativeLayout mDrawerPane;
    protected ActionBarDrawerToggle mDrawerToggle;
    protected DrawerLayout mDrawerLayout;
    public ArrayList<NavItem> mNavItems = new ArrayList<>();

    Context mContext;


    public class NavItem {
        String mTitle;
        String mSubtitle;
        int mIcon;

        public NavItem(String title, String subtitle, int icon) {
            mTitle = title;
            mSubtitle = subtitle;
            mIcon = icon;
        }
    }

//
//    NavItem mHome = new NavItem("Home", "The latest from the Boston Baby Nurse blog", R.drawable.ic_home_black_48dp);
//
//
//    mNavItems.add(mHome);
//    mNavItems.add(new NavItem("Community forum", "Reach out and connect with new parents", R.drawable.ic_forum_black_48dp));
//    mNavItems.add(new NavItem("Education", "Learning materials for new parents", R.drawable.ic_class_black_48dp));
//    mNavItems.add(new NavItem("Schedule a visit", "Reach out to the Boston Baby Nurse team", R.drawable.ic_event_black_48dp));
//
//





//    class DrawerListAdapter extends BaseAdapter {
//
//        public DrawerListAdapter(Context context, ArrayList<NavItem> navItems) {
//            mContext = context;
//            mNavItems = navItems;
//        }
//
//        @Override
//        public int getCount() {
//            return mNavItems.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return mNavItems.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return 0;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            View view;
//
//            if (convertView == null) {
//                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                view = inflater.inflate(R.layout.drawer_item, null);
//            } else {
//                view = convertView;
//            }
//
//            TextView titleView = (TextView) view.findViewById(R.id.title);
//            TextView subtitleView = (TextView) view.findViewById(R.id.subTitle);
//            ImageView iconView = (ImageView) view.findViewById(R.id.icon);
//
//            titleView.setText(mNavItems.get(position).mTitle);
//            subtitleView.setText(mNavItems.get(position).mSubtitle);
//            iconView.setImageResource(mNavItems.get(position).mIcon);
//
//            return view;
//        }
//    }
//
//    // Called when a particular item from the drawer is selected
//    protected void selectItemFromDrawer(int position) {
//        Intent intent;
//        Log.v("MainActivity", " this is " + position);
//
//        switch(position) {
//            default:
//            case 0:
//                intent = new Intent(getBaseContext(), MainActivity.class);
//                startActivity(intent);
//                break;
//            case 1:
//                intent = new Intent(getBaseContext(), ForumActivity.class);
//                startActivity(intent);
//                break;
//            case 2:
//                intent = new Intent(getBaseContext(), EducationActivity.class);
//                startActivity(intent);
//                break;
//            case 3:
//                intent = new Intent(getBaseContext(), InquiryActivity.class);
//                startActivity(intent);
//                break;
//        }
//
//        mDrawerList.setItemChecked(position, true);
//        setTitle(mNavItems.get(position).mTitle);
//
//        // Close the drawer
//        mDrawerLayout.closeDrawer(mDrawerPane);
//    }
}
