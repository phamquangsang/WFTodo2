package phamsang.com.wftodo.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import phamsang.com.wftodo.TodoItem;
import phamsang.com.wftodo.data.Contract.TodoItemEntry;
import phamsang.com.wftodo.data.Contract.TodoListEntry;

/**
 * Created by Quang Quang on 2/24/2016.
 */
public class TodoDatabaseHelper extends SQLiteOpenHelper {
    private final String LOG_TAG = TodoDatabaseHelper.class.getSimpleName();
    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "wftodo.db";
    public TodoDatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TODO_ITEM_TABLE = "CREATE TABLE "+
                TodoItemEntry.TABLE_NAME +" (" +
                TodoItemEntry._ID + " INTEGER PRIMARY KEY ,"+
                TodoItemEntry.COLLUMN_CONTENT + " TEXT NOT NULL," +
                TodoItemEntry.COLLUMN_IS_DONE + " INTEGER NOT NULL, " +
                TodoItemEntry.COLUMN_TIME+" TEXT NOT NULL, "   +
                TodoItemEntry.COLLUMN_LIST_ID + " INTEGER NOT NULL, "+
                " FOREIGN KEY (" + TodoItemEntry.COLLUMN_LIST_ID + ") REFERENCES " +
                TodoListEntry.TABLE_NAME + " (" + TodoListEntry._ID + ") " +
                ");";
        final String SQL_CREATE_TODO_LIST_TABLE = "CREATE TABLE "+
                TodoListEntry.TABLE_NAME +" (" +
                TodoListEntry._ID + " INTEGER PRIMARY KEY ,"+
                TodoListEntry.COLLUMN_TITLE + " TEXT NOT NULL ,"+
                TodoListEntry.COLLUMN_COLOR + " INTEGER NOT NULL," +
                TodoListEntry.COLLUMN_TIME + " TEXT NOT NULL " +
                ");";
        db.execSQL(SQL_CREATE_TODO_ITEM_TABLE);
        db.execSQL(SQL_CREATE_TODO_LIST_TABLE);

        ContentValues value1 = new ContentValues();
        value1.put(TodoListEntry.COLLUMN_TITLE,"Welcome to WF TodoApp");
        value1.put(TodoListEntry.COLLUMN_COLOR,"1");
        value1.put(TodoListEntry.COLLUMN_TIME,"1456296776177");
        long result = db.insert(TodoListEntry.TABLE_NAME,null,value1);
        Log.d(LOG_TAG, "insert list1 result: "+result);

        ContentValues item1 = new ContentValues();
        item1.put(TodoItemEntry.COLLUMN_CONTENT,"touch to view detail or edit");
        item1.put(TodoItemEntry.COLLUMN_IS_DONE,"0");
        item1.put(TodoItemEntry.COLLUMN_LIST_ID,"1");
        item1.put(TodoItemEntry.COLUMN_TIME,"1456297018129");

        ContentValues item2 = new ContentValues();
        item2.put(TodoItemEntry.COLLUMN_CONTENT,"swipe left or right to delete a note");
        item2.put(TodoItemEntry.COLLUMN_IS_DONE,"0");
        item2.put(TodoItemEntry.COLLUMN_LIST_ID,"1");
        item2.put(TodoItemEntry.COLUMN_TIME,"1456297018129");

        ContentValues item3 = new ContentValues();
        item3.put(TodoItemEntry.COLLUMN_CONTENT,"touch fab button to create new note");
        item3.put(TodoItemEntry.COLLUMN_IS_DONE,"1");
        item3.put(TodoItemEntry.COLLUMN_LIST_ID,"1");
        item3.put(TodoItemEntry.COLUMN_TIME,"1456297018129");

        ContentValues item4 = new ContentValues();
        item4.put(TodoItemEntry.COLLUMN_CONTENT,"for now color is picked randomly, I will support pick color later");
        item4.put(TodoItemEntry.COLLUMN_IS_DONE,"1");
        item4.put(TodoItemEntry.COLLUMN_LIST_ID,"1");
        item4.put(TodoItemEntry.COLUMN_TIME,"1456297018129");

        result = db.insert(TodoItemEntry.TABLE_NAME,null, item1);
        Log.d(LOG_TAG, "insert item1 result: "+result);
        result = db.insert(TodoItemEntry.TABLE_NAME,null, item2);
        Log.d(LOG_TAG, "insert item2 result: "+result);
        result = db.insert(TodoItemEntry.TABLE_NAME,null, item3);
        Log.d(LOG_TAG, "insert item3 result: "+result);
        result = db.insert(TodoItemEntry.TABLE_NAME,null, item4);
        Log.d(LOG_TAG, "insert item4 result: "+result);

        ContentValues value2 = new ContentValues();
        value2.put(TodoListEntry.COLLUMN_TITLE,"About");
        value2.put(TodoListEntry.COLLUMN_COLOR,"2");
        value2.put(TodoListEntry.COLLUMN_TIME,"1456296776177");
        result = db.insert(TodoListEntry.TABLE_NAME,null,value2);
        Log.d(LOG_TAG, "insert list2 result: "+result);

        ContentValues item21 = new ContentValues();
        item21.put(TodoItemEntry.COLLUMN_CONTENT,"Pham Ngoc Quang Sang");
        item21.put(TodoItemEntry.COLLUMN_IS_DONE,"0");
        item21.put(TodoItemEntry.COLLUMN_LIST_ID,"2");
        item21.put(TodoItemEntry.COLUMN_TIME,"1456297018129");

        ContentValues item22 = new ContentValues();
        item22.put(TodoItemEntry.COLLUMN_CONTENT,"from University of Information Technology");
        item22.put(TodoItemEntry.COLLUMN_IS_DONE,"0");
        item22.put(TodoItemEntry.COLLUMN_LIST_ID,"2");
        item22.put(TodoItemEntry.COLUMN_TIME,"1456297018129");

        ContentValues item23 = new ContentValues();
        item23.put(TodoItemEntry.COLLUMN_CONTENT,"phamngocquangsang@gmail.com");
        item23.put(TodoItemEntry.COLLUMN_IS_DONE,"0");
        item23.put(TodoItemEntry.COLLUMN_LIST_ID,"2");
        item23.put(TodoItemEntry.COLUMN_TIME,"1456297018129");

        ContentValues item24 = new ContentValues();
        item24.put(TodoItemEntry.COLLUMN_CONTENT,"latest version: https://github.com/phamquangsang");
        item24.put(TodoItemEntry.COLLUMN_IS_DONE,"0");
        item24.put(TodoItemEntry.COLLUMN_LIST_ID,"2");
        item24.put(TodoItemEntry.COLUMN_TIME,"1456297018129");

        ContentValues item25 = new ContentValues();
        item25.put(TodoItemEntry.COLLUMN_CONTENT,"doing this project, I've learned alot about Android");
        item25.put(TodoItemEntry.COLLUMN_IS_DONE,"0");
        item25.put(TodoItemEntry.COLLUMN_LIST_ID,"2");
        item25.put(TodoItemEntry.COLUMN_TIME,"1456297018129");

        result = db.insert(TodoItemEntry.TABLE_NAME,null, item21);
        Log.d(LOG_TAG, "insert item1 result: "+result);
        result = db.insert(TodoItemEntry.TABLE_NAME,null, item22);
        Log.d(LOG_TAG, "insert item2 result: "+result);
        result = db.insert(TodoItemEntry.TABLE_NAME,null, item23);
        Log.d(LOG_TAG, "insert item3 result: "+result);
        result = db.insert(TodoItemEntry.TABLE_NAME,null, item24);
        Log.d(LOG_TAG, "insert item4 result: "+result);
        result = db.insert(TodoItemEntry.TABLE_NAME,null, item25);
        Log.d(LOG_TAG, "insert item4 result: "+result);

        ContentValues value3 = new ContentValues();
        value3.put(TodoListEntry.COLLUMN_TITLE,"plan this weeken");
        value3.put(TodoListEntry.COLLUMN_COLOR,"3");
        value3.put(TodoListEntry.COLLUMN_TIME,"1456296776177");
        result = db.insert(TodoListEntry.TABLE_NAME,null,value3);
        Log.d(LOG_TAG, "insert list3 result: "+result);

        ContentValues item31 = new ContentValues();
        item31.put(TodoItemEntry.COLLUMN_CONTENT,"at 8AM, go to coffee with Quan, help him reinstall windows");
        item31.put(TodoItemEntry.COLLUMN_IS_DONE,"0");
        item31.put(TodoItemEntry.COLLUMN_LIST_ID,"3");
        item31.put(TodoItemEntry.COLUMN_TIME,"1456297018129");

        ContentValues item32 = new ContentValues();
        item32.put(TodoItemEntry.COLLUMN_CONTENT,"read chapter 3 of DBGDAMM book");
        item32.put(TodoItemEntry.COLLUMN_IS_DONE,"0");
        item32.put(TodoItemEntry.COLLUMN_LIST_ID,"3");
        item32.put(TodoItemEntry.COLUMN_TIME,"1456297018129");

        ContentValues item33 = new ContentValues();
        item33.put(TodoItemEntry.COLLUMN_CONTENT,"finish assignment of CoderSchool");
        item33.put(TodoItemEntry.COLLUMN_IS_DONE,"0");
        item33.put(TodoItemEntry.COLLUMN_LIST_ID,"3");
        item33.put(TodoItemEntry.COLUMN_TIME,"1456297018129");

        ContentValues item34 = new ContentValues();
        item34.put(TodoItemEntry.COLLUMN_CONTENT,"watch movie");
        item34.put(TodoItemEntry.COLLUMN_IS_DONE,"0");
        item34.put(TodoItemEntry.COLLUMN_LIST_ID,"3");
        item34.put(TodoItemEntry.COLUMN_TIME,"1456297018129");

        result = db.insert(TodoItemEntry.TABLE_NAME,null, item31);
        Log.d(LOG_TAG, "insert item1 result: "+result);
        result = db.insert(TodoItemEntry.TABLE_NAME,null, item32);
        Log.d(LOG_TAG, "insert item2 result: "+result);
        result = db.insert(TodoItemEntry.TABLE_NAME,null, item33);
        Log.d(LOG_TAG, "insert item3 result: "+result);
        result = db.insert(TodoItemEntry.TABLE_NAME,null, item34);
        Log.d(LOG_TAG, "insert item4 result: "+result);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TodoListEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TodoItemEntry.TABLE_NAME);
        onCreate(db);
    }
}
