package com.example.troywhite.rural;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class FoodActivity extends AppCompatActivity implements LocationListener {
    private Toolbar toolbar;

    private TabLayout mTabs;

    private ScrollView sc;
    private TextView tt, ta, ti, tprice, ttime, tc, ttc, ttraffic, tp, txt_r1, txt_r2, txt_r3, txt_r4;
    private WebView webView;
    private LocationManager lmgr;
    double ulat, ulng;

    int _id, fav;
    String page, name, introduction, lat, lon, address, phone, price, time, creditcard, travelcard, traffic, parkinglot;

    private SQLiteDatabase db;
    private Cursor cursor;

    private DBHelper mDBHelper;

    private boolean isCall;

    private static final int LOCATION_REQUEST = 1;    // requestPermissions 與 onResultPermissionsResult 使用

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        sc = (ScrollView) findViewById(R.id.brief_scroll);
        sc.setVisibility(ScrollView.VISIBLE);
        tt = (TextView) findViewById(R.id.brief_tel);
        ta = (TextView) findViewById(R.id.brief_address);
        ti = (TextView) findViewById(R.id.brief_introduction);
        tprice = (TextView) findViewById(R.id.brief_price);
        ttime = (TextView) findViewById(R.id.brief_time);
        tc = (TextView) findViewById(R.id.brief_creditcard);
        ttc = (TextView) findViewById(R.id.brief_travelcard);
        ttraffic = (TextView) findViewById(R.id.brief_traffic);
        tp = (TextView) findViewById(R.id.brief_parkinglot);

        webView = (WebView) findViewById(R.id.map);
        webView.setVisibility(WebView.GONE);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        txt_r1 = (TextView) findViewById(R.id.content_tv_r1);
        txt_r1.setVisibility(TextView.GONE);
        txt_r2 = (TextView) findViewById(R.id.content_tv_r2);
        txt_r2.setVisibility(TextView.GONE);
        txt_r3 = (TextView) findViewById(R.id.content_tv_r3);
        txt_r3.setVisibility(TextView.GONE);
        txt_r4 = (TextView) findViewById(R.id.content_tv_r4);
        txt_r4.setVisibility(TextView.GONE);

        Intent it = getIntent();
        _id = it.getIntExtra("_id", 0);
        page = it.getStringExtra("page");
        name = it.getStringExtra("name");
        introduction = it.getStringExtra("introduction");
        lat = it.getStringExtra("lat");
        lon = it.getStringExtra("lon");
        address = it.getStringExtra("address");
        phone = it.getStringExtra("phone");
        fav = it.getIntExtra("fav", 7);
        price = it.getStringExtra("price");
        time = it.getStringExtra("time");
        creditcard = it.getStringExtra("creditcard");
        travelcard = it.getStringExtra("travelcard");
        traffic = it.getStringExtra("traffic");
        parkinglot = it.getStringExtra("parkinglot");

        tt.setText("電話：" + "\n" + phone);
        tt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call(phone);
            }
        });
        ta.setText("地址：" + "\n" + address);
        tprice.setText("價位：" + "\n" + price);
        ttime.setText("營業時段：" + "\n" + time);
        tc.setText("是否可用信用卡：" + creditcard);
        ttc.setText("是否可用旅遊卡：" + travelcard);
        ttraffic.setText("交通資訊：" + "\n" + traffic);
        tp.setText("停車資訊：" + "\n" + parkinglot);
        ti.setText("詳細介紹：" + "\n" + introduction);

        mDBHelper = new DBHelper(this);

        this.lmgr = (LocationManager) this.getSystemService(LOCATION_SERVICE);

        getNetworkFix();
        if (false == lmgr.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // 台北車站 25.047711, 121.517043
            webView.setWebViewClient(mWebViewClient);
            webView.loadUrl("http://www.google.com.tw/maps/dir/25.047711,121.517043/" + lat + "," + lon + "/");
//            webView.loadUrl("https://tw.yahoo.com/");
        }

        mTabs = (TabLayout) findViewById(R.id.tabs_content);
        mTabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int numTab = tab.getPosition();

                switch (numTab) {
                    case 0:
                        sc.setVisibility(ScrollView.VISIBLE);

                        webView.setVisibility(WebView.GONE);

                        txt_r1.setVisibility(TextView.GONE);
                        txt_r2.setVisibility(TextView.GONE);
                        txt_r3.setVisibility(TextView.GONE);
                        txt_r4.setVisibility(TextView.GONE);
                        return;

                    case 1:
                        sc.setVisibility(ScrollView.GONE);

                        webView.setVisibility(WebView.VISIBLE);

                        txt_r1.setVisibility(TextView.GONE);
                        txt_r2.setVisibility(TextView.GONE);
                        txt_r3.setVisibility(TextView.GONE);
                        txt_r4.setVisibility(TextView.GONE);
                        return;

                    case 2:
                        sc.setVisibility(ScrollView.GONE);

                        webView.setVisibility(WebView.GONE);

                        txt_r1.setVisibility(TextView.VISIBLE);
                        txt_r2.setVisibility(TextView.VISIBLE);
                        txt_r3.setVisibility(TextView.VISIBLE);
                        txt_r4.setVisibility(TextView.VISIBLE);
                        return;

                    default:
                        sc.setVisibility(ScrollView.VISIBLE);

                        webView.setVisibility(WebView.GONE);

                        txt_r1.setVisibility(TextView.GONE);
                        txt_r2.setVisibility(TextView.GONE);
                        txt_r3.setVisibility(TextView.GONE);
                        txt_r4.setVisibility(TextView.GONE);
                        return;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar_content);
//        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
        // Inflate a menu to be displayed in the toolbar
//        toolbar.inflateMenu(R.menu.menu_content_favorite);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(name);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
//        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_search);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        if (fav == 1) {
            getMenuInflater().inflate(R.menu.menu_content_favorite, menu);
        }else {
            getMenuInflater().inflate(R.menu.menu_content_unfavorite, menu);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.favorite:
                if (item.getIcon().getConstantState().equals(getResources().getDrawable(R.drawable.unfavorite).getConstantState())) {
                    item.setIcon(R.drawable.favorite);
                    Toast.makeText(getApplicationContext(), "加入收藏", Toast.LENGTH_SHORT).show();
                    addFav();
                } else {
                    item.setIcon(R.drawable.unfavorite);
                    Toast.makeText(getApplicationContext(), "取消收藏", Toast.LENGTH_SHORT).show();
                    delFav();
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addFav(){
        ContentValues values = new ContentValues();
        values.put("fav", 1);

        SQLiteOpenHelper mDBHelper = new DBHelper(FoodActivity.this);
        try {
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            db.update(page, values, "_id = ?", new String[]{Integer.toString(_id)});

            db.close();
        }catch (SQLiteException e){
            Log.i("troy", e.toString());
        }
    }

    private void delFav(){
        ContentValues values = new ContentValues();
        values.put("fav", 0);

        SQLiteOpenHelper mDBHelper = new DBHelper(FoodActivity.this);
        try {
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            db.update(page, values, "_id = ?", new String[]{Integer.toString(_id)});

            db.close();
        }catch (SQLiteException e){
            Log.i("troy", e.toString());
        }
    }

    //撥號出去
    private void call(String phone) {

        isCall = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED;

        if (!isCall){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},1);

        }else {
            Intent intentDial = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phone));
            startActivity(intentDial);
        }

    }

    WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                            return super.shouldOverrideUrlLoading(view, request);
            view.loadUrl(url);
            return true;
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        // 取得位置提供者，不下條件，讓系統決定最適用者，true 表示生效的 provider
        String provider = this.lmgr.getBestProvider(new Criteria(), true);
        if (provider == null) {
            Log.i("troy", "沒有 location provider 可以使用");
            return;
        }
        Log.i("troy", "取得 provider - " + provider);

        Log.i("troy", "requestLocationUpdates...");

        // 註冊 listener，兩個 0 不適合在實際環境使用，太耗電
        this.lmgr.requestLocationUpdates(provider, 1000, 5, this);

        Log.i("troy", "getLastKnownLocation...");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            return;
        }
        Location location = this.lmgr.getLastKnownLocation(provider);
        if (location == null) {
            Log.i("troy", "未取過 location");
            return;
        }
        Log.i("troy", "取得上次的 location");
        this.onLocationChanged(location);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("troy", "removeUpdates...");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            return;
        }
        this.lmgr.removeUpdates(this);
    }

    public void getNetworkFix() {
        this.lmgr = (LocationManager) this.getSystemService(LOCATION_SERVICE);    // 獲取系統位置服務
        // 檢查是否有授權
        if ( checkLocationPermission() ) {
            if (lmgr.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//                lmgr.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);    // 定位
            }
        } else {
            // 請求使用者授權
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_REQUEST);
        }
    }

    private boolean checkLocationPermission() {
        // 如果使用者的 Android 版本低於 6.0 ，直接回傳 True (在安裝時已授權)
        int api_version = Build.VERSION.SDK_INT;    //API版本
        String android_version = Build.VERSION.RELEASE;    //Android版本
        if(api_version < Build.VERSION_CODES.M && !android_version.matches("(6)\\..+")) return true;

        return (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 授權對話框結果，一個請求授權對應一個 requestCode
        if (requestCode == LOCATION_REQUEST) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                getNetworkFix();
            else
                // 台北車站 25.047711, 121.517043
                webView.setWebViewClient(mWebViewClient);
                webView.loadUrl("http://www.google.com.tw/maps/dir/25.047711,121.517043/" + lat + "," + lon + "/");
//            webView.loadUrl("https://tw.yahoo.com/");
                Toast.makeText(this, "需要您的授權使用定位功能", Toast.LENGTH_SHORT).show();    // 不授權顯示
        } else
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onLocationChanged(Location location) {
        ulat = location.getLatitude();
        ulng = location.getLongitude();
        Log.i("troy", "BBB"+ulat+ulng);

        if (ulat != 0 && ulng != 0) {
            webView.setWebViewClient(mWebViewClient);
            webView.loadUrl("https://www.google.com.tw/maps/dir/" + ulat + "," + ulng + "/" + lat + "," + lon + "/");
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
