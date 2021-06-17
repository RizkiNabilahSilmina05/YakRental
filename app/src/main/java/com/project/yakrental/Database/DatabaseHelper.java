package com.project.yakrental.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.project.yakrental.R;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "db_yakrental";
    public static final String TABLE_USER = "tb_user";
    public static final String COL_USERNAME = "email";
    public static final String COL_PASSWORD = "password";
    public static final String COL_NAME = "name";
    public static final String COL_NIK = "nik";
    public static final String COL_NOHP = "no_hp";
    public static final String TABLE_CAR = "tb_mobil";
    public static final String COL_NO_MOBIL = "no_mobil";
    public static final String COL_JENIS_MOBIL = "jenis_mobil";
    public static final String COL_TIPE_MOBIL = "tipe_mobil";
    public static final String COL_WARNA_MOBIL = "warna";
    public static final String COL_HARGA = "harga";
    public static final String COL_GAMBAR = "gambar";

    private SQLiteDatabase db;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("PRAGMA foreign_keys=ON");
        db.execSQL("create table " + TABLE_USER + " (" + COL_NIK + " TEXT PRIMARY KEY, " + COL_USERNAME +
                " TEXT, " + COL_PASSWORD + " TEXT, " + COL_NOHP + " TEXT, " + COL_NAME + " TEXT)");
        db.execSQL("create table " + TABLE_CAR + " (" + COL_NO_MOBIL + " TEXT PRIMARY KEY, " +
                COL_JENIS_MOBIL + " TEXT, " + COL_TIPE_MOBIL + " TEXT" + ", " + COL_WARNA_MOBIL + " TEXT," + COL_HARGA + " TEXT," + COL_GAMBAR + " INTEGER)");

        insertCar(db);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CAR);
        onCreate(db);
    }
    public void open() throws SQLException {
        db = this.getWritableDatabase();
    }
    public boolean Register(String nik, String username, String name, String nohp, String password) throws SQLException {

        @SuppressLint("Recycle") Cursor mCursor = db.rawQuery("INSERT INTO " + TABLE_USER + "(" + COL_NIK + ", " + COL_USERNAME + ", " + COL_NAME + "," + COL_NOHP + ","+ COL_PASSWORD + ") VALUES (?,?,?,?,?)", new String[]{nik, username, name, nohp, password});
        if (mCursor != null) {
            return mCursor.getCount() > 0;
        }
        return false;
    }

    public boolean Login(String username, String password) throws SQLException {
        @SuppressLint("Recycle") Cursor mCursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE " + COL_USERNAME + "=? AND " + COL_PASSWORD + "=?", new String[]{username, password});
        if (mCursor != null) {
            return mCursor.getCount() > 0;
        }
        return false;
    }

    public boolean insertData(SQLiteDatabase db, String no, String jenis, String tipe, String warna, String harga, int gambar) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_NO_MOBIL,no);

        contentValues.put(COL_JENIS_MOBIL,jenis);

        contentValues.put(COL_TIPE_MOBIL,tipe);

        contentValues.put(COL_WARNA_MOBIL,warna);

        contentValues.put(COL_HARGA,harga);

        contentValues.put(COL_GAMBAR,gambar);

        long result = db.insert(TABLE_CAR, null, contentValues);

        if(result == -1) {

            return false;
        }

        else {

            return true;
        }

    }

    public void insertCar(SQLiteDatabase db) {
        String[] jenisMobil = new String[]{"Mobil 1", "Mobil 2", "Mobil 3", "Mobil 4", "Mobil 5", "Mobil 6"};
        String[] noMobil = new String[]{"B 6578 S", "BK 1452 KL", "DK 1234 R", "L 414 R", "BM 777 L", "N 8778 M"};
        String[] tipeMobil = new String[]{"Tipe A", "Tipe A", "Tipe A", "Tipe A", "Tipe A", "Tipe A"};
        String[] warnaMobil = new String[]{"Hitam", "Hitam", "Hitam", "Hitam", "Hitam", "Hitam"};
        String[] hargaSewa = new String[]{"2000000", "2500000", "1000000", "1500000", "3000000", "1500000"};
        int[] gambarMobil = new int[]{R.drawable.car1, R.drawable.car2, R.drawable.car3, R.drawable.car4, R.drawable.car5, R.drawable.car6};

        for (int i = 0; i<noMobil.length; i++) {
            insertData(db, noMobil[i], jenisMobil[i], tipeMobil[i], warnaMobil[i], hargaSewa[i], gambarMobil[i]);
        }
    }
}
