package com.example.troywhite.rural;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by TroyWhite on 2016/7/22.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "opendata";
    public static final String DBLOCATION = "/data/data/com.example.troywhite.rural/databases/";
    private static final int DB_VERSION = 1;
    public DBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        updateMyDatabase(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        updateMyDatabase(db, oldVersion, newVersion);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            db.execSQL("CREATE TABLE page1 (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "name TEXT, "
                    + "introduction TEXT, "
                    + "lat TEXT,"
                    + "lon TEXT,"
                    + "area TEXT,"
                    + "city TEXT,"
                    + "address TEXT,"
                    + "phone TEXT,"
                    + "coordinate TEXT,"
                    + "time TEXT,"
                    + "traffic TEXT,"
                    + "note TEXT,"
                    + "fav TEXT,"
                    + "img TEXT,);");
            insertPage1(db, "文山休閒農場", "座落於小山上",
                    "24.085016", "120.72547", "中部" , "台中市", "大里區", "台中市大里市健東路125之5號",
                    "+886-4-24937679", "", "", "", "", "", "");
            insertPage1(db, "羅望子生態教育休閒農場", "座落於小山上",
                    "24.085016", "120.72547", "中部" , "台中市", "大里區", "台中市大里市健東路125之5號",
                    "+886-4-24937679", "", "", "", "", "", "");
        }
//        insertDrink(db, "Filter", "Our best drip coffee", R.drawable.filter);
//        if (oldVersion < 2) {
//            db.execSQL("ALTER TABLE DRINK ADD COLUMN FAVORITE NUMERIC;");
//        }
        if (oldVersion < 2){

        }
    }

    private static void insertPage1(SQLiteDatabase db, String name,
                                    String introduction, String lat,
                                    String lon, String area,
                                    String city, String town,
                                    String address, String phone,
                                    String coordinate, String time,
                                    String traffic, String note,
                                    String fav,
                                    String img) {
        ContentValues page1Values = new ContentValues();
        page1Values.put("name", name);
        page1Values.put("introduction", introduction);
        page1Values.put("lat", lat);
        page1Values.put("lon", lon);
        page1Values.put("area", area);
        page1Values.put("city", city);
        page1Values.put("town", town);
        page1Values.put("address", address);
        page1Values.put("phone", phone);
        page1Values.put("coordinate", coordinate);
        page1Values.put("time", time);
        page1Values.put("traffic", traffic);
        page1Values.put("note", note);
        page1Values.put("fav", fav);
        page1Values.put("img", img);
        db.insert("page1", null, page1Values);
    }
}
