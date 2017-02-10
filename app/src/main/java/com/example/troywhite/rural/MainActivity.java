package com.example.troywhite.rural;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.troywhite.rural.drawer.AboutActivity;
import com.example.troywhite.rural.drawer.BrowseHistoryActivity;
import com.example.troywhite.rural.drawer.FavoriteActivity;
import com.example.troywhite.rural.drawer.ReportActivity;
import com.example.troywhite.rural.drawer.SettingActivity;
import com.example.troywhite.rural.drawer.ShoppingCartActivity;
import com.example.troywhite.rural.drawer.ShoppingHistoryActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private Toolbar toolbar;
    NavigationView navigationView;

    private SQLiteDatabase db;
    private Cursor cursor;

    private DBHelper mDBHelper;

    private TabLayout mTabs;

    private Spinner sp_area, sp_city;
    ArrayAdapter<CharSequence> arr_sp_area, arr_sp_city;
    private String[] area = new String []{"所有區域", "北部", "中部", "南部", "東部", "離島"};
    private String[] city = new String []{"所有縣市", "台北市", "新北市", "桃園市", "台中市", "台南市", "高雄市", "基隆市", "新竹市", "嘉義市", "新竹縣", "苗栗縣", "彰化縣", "南投縣", "雲林縣", "嘉義縣", "屏東縣", "宜蘭縣", "花蓮縣", "台東縣", "澎湖縣", "金門縣", "連江縣"};
    private String[][] city2 = new String[][]{
            {"所有縣市", "台北市", "新北市", "桃園市", "台中市", "台南市", "高雄市", "基隆市", "新竹市", "嘉義市", "新竹縣", "苗栗縣", "彰化縣", "南投縣", "雲林縣", "嘉義縣", "屏東縣", "宜蘭縣", "花蓮縣", "台東縣", "澎湖縣", "金門縣", "連江縣"},
            {"所有北部縣市", "基隆市", "宜蘭縣", "台北市", "新北市", "桃園市", "新竹市", "新竹縣"},
            {"所有中部縣市", "苗栗縣", "台中市", "彰化縣", "南投縣", "雲林縣"},
            {"所有南部縣市", "嘉義縣", "嘉義市", "台南市", "高雄市", "屏東縣"},
            {"所有東部縣市", "花蓮縣", "台東縣"},
            {"所有離島縣市", "澎湖縣", "金門縣", "連江縣"},
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp_area = (Spinner) findViewById(R.id.sp_area);
        sp_city = (Spinner) findViewById(R.id.sp_city);

        arr_sp_area = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_dropdown_item, area);
        arr_sp_area.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); //自訂getDropDownView()介面格式(Spinner介面展開時，View所使用的每個item格式)
        sp_area.setAdapter(arr_sp_area);
        sp_area.setOnItemSelectedListener(sp_areaOnItemSelected);

        arr_sp_city = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_dropdown_item,city);
        arr_sp_city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); //自訂getDropDownView()介面格式(Spinner介面展開時，View所使用的每個item格式)
        sp_city.setAdapter(arr_sp_city);

        final ListView listView = (ListView) findViewById(R.id.travel_lv);
        mDBHelper = new DBHelper(this);

        mTabs = (TabLayout)findViewById(R.id.tabs);
        mTabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int numTab = mTabs.getSelectedTabPosition();

                switch (numTab){
                    case 0:

                        try {
                            SQLiteOpenHelper mDBHelper = new DBHelper(getBaseContext());
                            db = mDBHelper.getReadableDatabase();

                            cursor = db.query("page1",
                                    new String[]{"_id", "name", "introduction", "lat", "lon", "address", "phone", "fav"},
                                    null, null, null, null, null);

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
                                Intent it = new Intent(MainActivity.this, TravelActivity.class);
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
                                    null, null, null, null, null);

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
                                Intent it = new Intent(MainActivity.this, FoodActivity.class);
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
                                    null, null, null, null, null);

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
                                Intent it = new Intent(MainActivity.this, HouseActivity.class);
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
                                    null, null, null, null, null);

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
                                Intent it = new Intent(MainActivity.this, SaleActivity.class);
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
                                    null, null, null, null, null);

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
                                Intent it = new Intent(MainActivity.this, TravelActivity.class);
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

        //Check exists database
        File database = getApplicationContext().getDatabasePath(DBHelper.DB_NAME);
        if(false == database.exists()) {
            mDBHelper.getReadableDatabase();
            //Copy db
            if(copyDatabase(this)) {
                Toast.makeText(this, "Copy database succes", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Copy data error", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        try {
            SQLiteOpenHelper mDBHelper = new DBHelper(this);
            db = mDBHelper.getReadableDatabase();

            cursor = db.query("page1",
                    new String[]{"_id", "name", "introduction", "lat", "lon", "address", "phone", "fav"},
                    null, null, null, null, null);

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
                Intent it = new Intent(MainActivity.this, TravelActivity.class);
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
        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
        // Inflate a menu to be displayed in the toolbar
        toolbar.inflateMenu(R.menu.menu_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("你農我農");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mActionBarDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    private AdapterView.OnItemSelectedListener sp_areaOnItemSelected = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            // An item was selected. You can retrieve the selected item using
            // parent.getItemAtPosition(pos)
            int pos = sp_area.getSelectedItemPosition();
            arr_sp_city = new ArrayAdapter<CharSequence>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, city2[pos]);
            sp_city.setAdapter(arr_sp_city);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            // Another interface callback
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        ListView listView = (ListView) findViewById(R.id.travel_lv);
//        try {
//            SQLiteOpenHelper mDBHelper = new DBHelper(this);
//            db = mDBHelper.getReadableDatabase();
//
//            Cursor newCusor = db.query("page1",
//                    new String[]{"_id", "name", "introduction", "lat", "lon", "address", "phone", "fav"},
//                    null, null, null, null, null);
//
//            CursorAdapter listAdapter = new SimpleCursorAdapter(this,
//                    android.R.layout.simple_list_item_1,
//                    cursor,
//                    new String[]{"name"},
//                    new int[]{android.R.id.text1},
//                    0);
//
//            listView.setAdapter(listAdapter);
//            listAdapter.changeCursor(newCusor);
//            cursor = newCusor;
//        } catch(SQLiteException e) {
//            Log.i("troy", e.toString());
//            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
//            toast.show();
//        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_fb) {

        } else if (id == R.id.nav_favorite) {
//            Intent Main2Favorite = new Intent(this,FavoriteActivity.class);
//            startActivity(Main2Favorite);
            startActivity(new Intent(this, FavoriteActivity.class));
        } else if (id == R.id.nav_browse_history) {
            startActivity(new Intent(this, BrowseHistoryActivity.class));
        } else if (id == R.id.nav_shopping_cart) {
            startActivity(new Intent(this, ShoppingCartActivity.class));
        } else if (id == R.id.nav_shopping_history) {
            startActivity(new Intent(this, ShoppingHistoryActivity.class));
        } else if (id == R.id.nav_setting) {
            startActivity(new Intent(this, SettingActivity.class));
        } else if (id == R.id.nav_report) {
            startActivity(new Intent(this, ReportActivity.class));
        } else if (id == R.id.nav_about) {
            startActivity(new Intent(this, AboutActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean copyDatabase(Context context) {
        try {

            InputStream inputStream = context.getAssets().open(DBHelper.DB_NAME);
            String outFileName = DBHelper.DBLOCATION + DBHelper.DB_NAME;
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[]buff = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            Log.w("MainActivity","DB copied");
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(
                new ComponentName(this, SearchableActivity.class)));
        searchView.setIconifiedByDefault(false);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Toast.makeText(this, "Searching by: "+ query, Toast.LENGTH_SHORT).show();

        } else if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            String uri = intent.getDataString();
            Toast.makeText(this, "Suggestion: "+ uri, Toast.LENGTH_SHORT).show();
        }
    }

}
