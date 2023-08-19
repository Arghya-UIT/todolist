package com.example.todolist.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class MyDBHelper extends SQLiteOpenHelper {

    public MyDBHelper(Context context) {
        super(context, Params.DB_NAME, null, Params.DB_VIRSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE " + Params.TABLE_NAME + "("
                + Params.KEY_ID + " INTEGER PRIMARY KEY,"
                + Params.KEY_NAME + " TEXT,"
                + Params.KEY_PHONE_NO + " TEXT,"
                + Params.KEY_IMAGE_URI + " TEXT"
                + ")";

        Log.d("db---arghya", "query " + create);
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // Implement if needed
    }

    public void addContact(ContactModel contactModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Params.KEY_NAME, contactModel.getName());
        values.put(Params.KEY_PHONE_NO, contactModel.getPhoneNo());
        values.put(Params.KEY_IMAGE_URI, contactModel.getImageUri());

        db.insert(Params.TABLE_NAME, null, values);
        Log.d("dbarghya", "db created");
        db.close();
    }

    public ArrayList<ContactModel> fetchContact() {
        ArrayList<ContactModel> contactList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String select = "SELECT * FROM " + Params.TABLE_NAME;
        Cursor cursor = db.rawQuery(select, null);
        if (cursor.moveToFirst()) {
            do {
                ContactModel contactModel = new ContactModel();
                contactModel.setId(cursor.getInt(0));
                contactModel.setName(cursor.getString(1));
                contactModel.setPhoneNo(cursor.getString(2));
                contactModel.setImageUri(cursor.getString(3));
                contactList.add(contactModel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return contactList;
    }
}

