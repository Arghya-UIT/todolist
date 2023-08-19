package com.example.todolist.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class MyDbHelper extends SQLiteOpenHelper {

    public MyDbHelper(Context context) {
        super(context, Params.DB_NAME, null, Params.DB_VIRSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE " + Params.TABLE_NAME + "("
                + Params.KEY_ID + " INTEGER PRIMARY KEY,"
                + Params.KEY_TITLE + " TEXT,"
                + Params.KEY_DESCRIPTION + " TEXT,"
                + Params.KEY_DATE + " TEXT,"
                + Params.KEY_TIME + " TEXT,"
                + Params.KEY_PRIORITY + " TEXT"
                + ")";

        Log.d("db---arghya", "query " + create);
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // Implement if needed
    }

    public void addTask(TaskModel taskModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Params.KEY_TITLE, taskModel.getTitle());
        values.put(Params.KEY_DESCRIPTION, taskModel.getDescription());
        values.put(Params.KEY_DATE, taskModel.getDate_for_store());
        values.put(Params.KEY_TIME, taskModel.getTime_for_store());
        values.put(Params.KEY_PRIORITY, taskModel.getPriority());

        db.insert(Params.TABLE_NAME, null, values);
        Log.d("dbarghya", "db created");
        db.close();
    }

    public ArrayList<TaskModel> fetchTask() {
        ArrayList<TaskModel> taskList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String select = "SELECT * FROM " + Params.TABLE_NAME;
        Cursor cursor = db.rawQuery(select, null);
        if (cursor.moveToFirst()) {
            do {
                TaskModel taskModel = new TaskModel();
                taskModel.setId(cursor.getInt(0));
                taskModel.setTitle(cursor.getString(1));
                taskModel.setDescription(cursor.getString(2));
                taskModel.setDate_for_store(cursor.getString(3));
                taskModel.setTime_for_store(cursor.getString(4));
                taskModel.setPriority(cursor.getString(5));

                taskList.add(taskModel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return taskList;
    }
}

