package com.davidhernandez.mybooklist;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();

    // Data members for drawer
    private String[] mDrawerListViewItems;
    private ListView mDrawerListView;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Tool Bar Menu ================================================================ */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /* Navigation Drawer ============================================================ */
        // get the drawer's list items from strings.xml
        mDrawerListViewItems = getResources().getStringArray(R.array.navDrawerItems);

        // get the ListView defined in activity_main.xml
        mDrawerListView = (ListView) findViewById(R.id.left_drawer);

        mDrawerListView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, mDrawerListViewItems));

        // set click listener for items in the drawer
        mDrawerListView.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mActionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.string.drawer_open,
                R.string.drawer_close
        );

        // set actionBarDrawerToggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            // puts the hamburger icon in action bar
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        /* Floating Button ============================================================== */
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // create an intent for the bookList Detail View
                Intent addBookView = new Intent(view.getContext(), AddBookActivity.class);

                startActivity(addBookView);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        if(mActionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }


        switch (item.getItemId()) {
            case R.id.hide_action_bar:
                Log.i(TAG, "Hiding Action Bar");
                return true;
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onClickHide(MenuItem item) {
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Log.i(TAG, "Action bar is now hidden.");
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mActionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mActionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        // fires when item in nav drawer is tapped
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            // create a toast message
            Toast.makeText(MainActivity.this, ((TextView) view).getText(), Toast.LENGTH_LONG).show();

            // close the nav drawer
            mDrawerLayout.closeDrawer(mDrawerListView);
        }
    }
}
