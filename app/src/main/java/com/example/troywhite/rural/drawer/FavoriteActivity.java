package com.example.troywhite.rural.drawer;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.troywhite.rural.FoodActivity;
import com.example.troywhite.rural.HouseActivity;
import com.example.troywhite.rural.SaleActivity;
import com.example.troywhite.rural.TravelActivity;
import com.example.troywhite.rural.DBHelper;
import com.example.troywhite.rural.R;

public class FavoriteActivity extends AppCompatActivity {
    private Toolbar toolbar;

    private TabLayout mTabs;

    private SQLiteDatabase db;
    private Cursor cursor;

    private DBHelper mDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        final ListView listView = (ListView) findViewById(R.id.fav_lv);
        mDBHelper = new DBHelper(this);

        mTabs = (TabLayout)findViewById(R.id.tabs_fav);
        mTabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int numTab = tab.getPosition();

                switch (numTab){
                    case 0:

                        try {
                            SQLiteOpenHelper mDBHelper = new DBHelper(getBaseContext());
                            db = mDBHelper.getReadableDatabase();

                            cursor = db.query("page1",
                                    new String[]{"_id", "name", "introduction", "lat", "lon", "address", "phone", "fav"},
                                    "fav LIKE '%1%'", null, null, null, null);

                            CursorAdapter listAdapter = new SimpleCursorAdapter(getBaseContext(),
                                    android.R.layout.simple_list_item_1,
                                    cursor,
                                    new String[]{"name"},
                                    new int[]{android.R.id.text1},
                                    0);
                            listView.setAdapter(listAdapter);
                        } catch(SQLiteException e) {
                            Log.i("troy", e.toString());
                            Toast toast = Toast.makeText(getApplicationContext(), "Database unavailable", Toast.LENGTH_SHORT);
                            toast.show();
                        }

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Intent it = new Intent(FavoriteActivity.this, TravelActivity.class);
                                it.putExtra("page", "page1");
                                it.putExtra("_id", cursor.getInt(0));
                                it.putExtra("name", cursor.getString(1));
                                it.putExtra("introduction", cursor.getString(2));
                                it.putExtra("lat", cursor.getString(3));
                                it.putExtra("lon", cursor.getString(4));
                                it.putExtra("address", cursor.getString(5));
                                it.putExtra("phone", cursor.getString(6));
                                it.putExtra("fav", cursor.getInt(7));
                                startActivity(it);
                            }
                        });
                        return;

                    case 1:

                        try {
                            SQLiteOpenHelper mDBHelper = new DBHelper(getBaseContext());
                            db = mDBHelper.getReadableDatabase();

                            cursor = db.query("page2",
                                    new String[]{"_id", "name", "introduction", "lat", "lon", "address", "phone", "fav", "price", "time", "creditcard", "travelcard", "traffic", "parkinglot"},
                                    "fav LIKE '%1%'", null, null, null, null);

                            CursorAdapter listAdapter = new SimpleCursorAdapter(getBaseContext(),
                                    android.R.layout.simple_list_item_1,
                                    cursor,
                                    new String[]{"name"},
                                    new int[]{android.R.id.text1},
                                    0);
                            listView.setAdapter(listAdapter);
                        } catch(SQLiteException e) {
                            Log.i("troy", e.toString());
                            Toast toast = Toast.makeText(getApplicationContext(), "Database unavailable", Toast.LENGTH_SHORT);
                            toast.show();
                        }

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Intent it = new Intent(FavoriteActivity.this, FoodActivity.class);
                                it.putExtra("page", "page2");
                                it.putExtra("_id", cursor.getInt(0));
                                it.putExtra("name", cursor.getString(1));
                                it.putExtra("introduction", cursor.getString(2));
                                it.putExtra("lat", cursor.getString(3));
                                it.putExtra("lon", cursor.getString(4));
                                it.putExtra("address", cursor.getString(5));
                                it.putExtra("phone", cursor.getString(6));
                                it.putExtra("fav", cursor.getInt(7));
                                it.putExtra("price", cursor.getString(8));
                                it.putExtra("time", cursor.getString(9));
                                it.putExtra("creditcard", cursor.getString(10));
                                it.putExtra("travelcard", cursor.getString(11));
                                it.putExtra("traffic", cursor.getString(12));
                                it.putExtra("parkinglot", cursor.getString(13));
                                startActivity(it);
                            }
                        });
                        return;

                    case 2:

                        try {
                            SQLiteOpenHelper mDBHelper = new DBHelper(getBaseContext());
                            db = mDBHelper.getReadableDatabase();

                            cursor = db.query("page3",
                                    new String[]{"_id", "name", "introduction", "lat", "lon", "address", "phone", "fav"},
                                    "fav LIKE '%1%'", null, null, null, null);

                            CursorAdapter listAdapter = new SimpleCursorAdapter(getBaseContext(),
                                    android.R.layout.simple_list_item_1,
                                    cursor,
                                    new String[]{"name"},
                                    new int[]{android.R.id.text1},
                                    0);
                            listView.setAdapter(listAdapter);
                        } catch(SQLiteException e) {
                            Log.i("troy", e.toString());
                            Toast toast = Toast.makeText(getApplicationContext(), "Database unavailable", Toast.LENGTH_SHORT);
                            toast.show();
                        }

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Intent it = new Intent(FavoriteActivity.this, HouseActivity.class);
                                it.putExtra("page", "page3");
                                it.putExtra("_id", cursor.getInt(0));
                                it.putExtra("name", cursor.getString(1));
                                it.putExtra("introduction", cursor.getString(2));
                                it.putExtra("lat", cursor.getString(3));
                                it.putExtra("lon", cursor.getString(4));
                                it.putExtra("address", cursor.getString(5));
                                it.putExtra("phone", cursor.getString(6));
                                it.putExtra("fav", cursor.getInt(7));
                                startActivity(it);
                            }
                        });
                        return;

                    case 3:

                        try {
                            SQLiteOpenHelper mDBHelper = new DBHelper(getBaseContext());
                            db = mDBHelper.getReadableDatabase();

                            cursor = db.query("page4",
                                    new String[]{"_id", "name", "introduction", "lat", "lon", "address", "phone", "fav"},
                                    "fav LIKE '%1%'", null, null, null, null);

                            CursorAdapter listAdapter = new SimpleCursorAdapter(getBaseContext(),
                                    android.R.layout.simple_list_item_1,
                                    cursor,
                                    new String[]{"name"},
                                    new int[]{android.R.id.text1},
                                    0);
                            listView.setAdapter(listAdapter);
                        } catch(SQLiteException e) {
                            Log.i("troy", e.toString());
                            Toast toast = Toast.makeText(getApplicationContext(), "Database unavailable", Toast.LENGTH_SHORT);
                            toast.show();
                        }

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Intent it = new Intent(FavoriteActivity.this, SaleActivity.class);
                                it.putExtra("page", "page4");
                                it.putExtra("_id", cursor.getInt(0));
                                it.putExtra("name", cursor.getString(1));
                                it.putExtra("introduction", cursor.getString(2));
                                it.putExtra("lat", cursor.getString(3));
                                it.putExtra("lon", cursor.getString(4));
                                it.putExtra("address", cursor.getString(5));
                                it.putExtra("phone", cursor.getString(6));
                                it.putExtra("fav", cursor.getInt(7));
                                startActivity(it);
                            }
                        });
                        return;

                    default:

                        try {
                            SQLiteOpenHelper mDBHelper = new DBHelper(getBaseContext());
                            db = mDBHelper.getReadableDatabase();

                            cursor = db.query("page1",
                                    new String[]{"_id", "name", "introduction", "lat", "lon", "address", "phone", "fav"},
                                    "fav LIKE '%1%'", null, null, null, null);

                            CursorAdapter listAdapter = new SimpleCursorAdapter(getBaseContext(),
                                    android.R.layout.simple_list_item_1,
                                    cursor,
                                    new String[]{"name"},
                                    new int[]{android.R.id.text1},
                                    0);
                            listView.setAdapter(listAdapter);
                        } catch(SQLiteException e) {
                            Log.i("troy", e.toString());
                            Toast toast = Toast.makeText(getApplicationContext(), "Database unavailable", Toast.LENGTH_SHORT);
                            toast.show();
                        }

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Intent it = new Intent(FavoriteActivity.this, TravelActivity.class);
                                it.putExtra("page", "page1");
                                it.putExtra("_id", cursor.getInt(0));
                                it.putExtra("name", cursor.getString(1));
                                it.putExtra("introduction", cursor.getString(2));
                                it.putExtra("lat", cursor.getString(3));
                                it.putExtra("lon", cursor.getString(4));
                                it.putExtra("address", cursor.getString(5));
                                it.putExtra("phone", cursor.getString(6));
                                it.putExtra("fav", cursor.getInt(7));
                                startActivity(it);
                            }
                        });
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        try {
            SQLiteOpenHelper mDBHelper = new DBHelper(this);
            db = mDBHelper.getReadableDatabase();

            cursor = db.query("page1",
                    new String[]{"_id", "name", "introduction", "lat", "lon", "address", "phone", "fav"},
                    "fav LIKE '%1%'", null, null, null, null);

            CursorAdapter listAdapter = new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_1,
                    cursor,
                    new String[]{"name"},
                    new int[]{android.R.id.text1},
                    0);
            listView.setAdapter(listAdapter);
        } catch(SQLiteException e) {
            Log.i("troy", e.toString());
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent(FavoriteActivity.this, TravelActivity.class);
                it.putExtra("page", "page1");
                it.putExtra("_id", cursor.getInt(0));
                it.putExtra("name", cursor.getString(1));
                it.putExtra("introduction", cursor.getString(2));
                it.putExtra("lat", cursor.getString(3));
                it.putExtra("lon", cursor.getString(4));
                it.putExtra("address", cursor.getString(5));
                it.putExtra("phone", cursor.getString(6));
                it.putExtra("fav", cursor.getInt(7));
                startActivity(it);
            }
        });

        // toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar_fav);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
        // Inflate a menu to be displayed in the toolbar
//        toolbar.inflateMenu(R.menu.menu_main);
//        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("我的收藏");

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
//        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_fav);

//        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close) {
//
//            @Override
//            public void onDrawerOpened(View drawerView) {
//                super.onDrawerOpened(drawerView);
//            }
//
//            @Override
//            public void onDrawerClosed(View drawerView) {
//                super.onDrawerClosed(drawerView);
//            }
//        };
//        mActionBarDrawerToggle.syncState();
//        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }
}
