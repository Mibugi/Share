package com.mibugi.share;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + PictureContract.PictureEntry.TABLE_NAME + "(" +
                    PictureContract.PictureEntry._ID + "INTEGER PRIMARY KEY," +
                    PictureContract.PictureEntry.COLUMN_NAME_TITLE + " VARCHAR(200)," +
                    PictureContract.PictureEntry.COLUMN_NAME_CONTENT + " TEXT," +
                    PictureContract.PictureEntry.COLUMN_NAME_IMAGE + " VARCHAR(2000)," +
                    PictureContract.PictureEntry.COLUMN_NAME_USERNAME + " VARCHAR(200)," +
                    PictureContract.PictureEntry.COLUMN_NAME_LOVE_COUNT + " INTEGER" + ")";
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + PictureContract.PictureEntry.TABLE_NAME;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "share_picture.db";
    private Context mContext;
    public MySQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //建图片表
        db.execSQL(SQL_CREATE_ENTRIES);
        //建用户表
        String sql="CREATE TABLE user(id INTEGER PRIMARY KEY autoincrement,username VARCHAR(20),password VARCHAR(20),touxiang BLOB(100))";
        db.execSQL(sql);;
        // 建点赞表

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        String sql = "DROP TABLE IF EXISTS user";
        db.execSQL(sql);
        onCreate(db);
    }
}
