package com.example.gscct.triceratopstracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase2 extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "lieu.db";
    public static final String TABLE_LIEU = "lieu_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "DATE";
    public static final String COL_4 = "VoyageId";
    public static final String COL_5 = "ImagePath";

    public DataBase2(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table lieu_table (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,DATE TEXT, VoyageId INTERGER, ImagePath TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_LIEU);
        onCreate(db);
    }

    public boolean insertLieu(int voyageId, String name, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, date);
        contentValues.put(COL_4, voyageId);
        long result = db.insert("lieu_table", null, contentValues);
        db.close();
        System.out.println(result);
        return result != -1;
    }

    public int addImageToLieu (String lieuId, String pathImage){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_5, pathImage);
        return db.update("lieu_table", contentValues, "ID = " + lieuId +";", null);

    }

    public Cursor getLieu(int voyageId){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from lieu_table WHERE voyageId = " + voyageId + ";", null);
        return res;
    }

    public Cursor getLieuWithLieuId(int lieuId){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from lieu_table WHERE ID = " + lieuId + ";", null);
        return res;
    }

    public Integer deleteLieu(int lieuId){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_LIEU, COL_1 + "=" + lieuId, null);
    }
/*
    public boolean updateData(String name, String date, String VoyageId){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryLastRowInserted = "select last_insert_rowid()";
        final Cursor cursor = db.rawQuery(queryLastRowInserted, null);
        int id = 0;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                id = cursor.getInt(0);
            }
            cursor.close();
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, date);
        contentValues.put(COL_4, VoyageId);
        db.update(TABLE_LIEU, contentValues, "ID = ?", new String[]{ id });
        return true;
    }
*/

}
