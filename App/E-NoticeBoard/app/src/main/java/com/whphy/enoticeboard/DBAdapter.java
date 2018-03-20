package com.whphy.enoticeboard;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.whphy.enoticeboard.models.NotifModel;

import java.util.ArrayList;


public class DBAdapter {
    private DbHelper helper;
    private SQLiteDatabase db;
    private Cursor cursor;

    public DBAdapter(Context context) {
        helper = new DbHelper(context);
    }

    public void addNotif(NotifModel notifModel) {
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbHelper.NOTIF, notifModel.getData());
        values.put(DbHelper.TITLE, notifModel.getTitle());
        db.insert(DbHelper.TABLE_NAME, null, values);
        Log.e("DBDEBUG", "addNotif: "+"added" );
    }

    public void clear() {
        db = helper.getWritableDatabase();
        db.execSQL(DbHelper.TRUNCATE);
    }

    public ArrayList<NotifModel> getNotifList() {
        ArrayList<NotifModel> arrayList = new ArrayList<>();
        db = helper.getReadableDatabase();
        cursor = db.query(DbHelper.TABLE_NAME, new String[]{DbHelper.TITLE,DbHelper.NOTIF}, null, null, null, null, null);


        while (cursor.moveToNext()) {

                arrayList.add(new NotifModel(cursor.getString(cursor.getColumnIndex(DbHelper.TITLE)),cursor.getString(cursor.getColumnIndex(DbHelper.NOTIF))));


        }
        cursor.close();
        return arrayList;
    }

    boolean check(String status) {
        db = helper.getReadableDatabase();
        cursor = db.query(DbHelper.TABLE_NAME, new String[]{DbHelper.NOTIF}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            if (cursor.getString(cursor.getColumnIndex(DbHelper.NOTIF)).contains(status))
                return true;
        }
        cursor.close();
        return false;
    }

    private static class DbHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "notif_db";
        private static final String TABLE_NAME = "notif_fav";
        private static final String UID = "_id";
        private static final String NOTIF = "notif";
        private static final String TITLE = "title";
        private static final String CREATE_STM = "create table " + TABLE_NAME + " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT," + NOTIF + " VARCHAR(255),"+ TITLE + " VARCHAR(255))";
        private static final String DROP_STM = "DROP TABLE IF EXISTS " + TABLE_NAME;
        private static final String TRUNCATE = "DELETE FROM " + TABLE_NAME;
        private static final int VERSION_CODE = 1;

        DbHelper(Context context) {
            super(context, DATABASE_NAME, null, VERSION_CODE);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_STM);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL(DROP_STM);
            onCreate(sqLiteDatabase);
        }
    }
}
