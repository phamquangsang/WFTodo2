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
        value1.put(TodoListEntry.COLLUMN_TITLE,"going shopping");
        value1.put(TodoListEntry.COLLUMN_COLOR,"1");
        value1.put(TodoListEntry.COLLUMN_TIME,"1456296776177");
        long result = db.insert(TodoListEntry.TABLE_NAME,null,value1);
        Log.d(LOG_TAG, "insert list1 result: "+result);

        ContentValues value2 = new ContentValues();
        value2.put(TodoListEntry.COLLUMN_TITLE,"weekend todo");
        value2.put(TodoListEntry.COLLUMN_COLOR,"2");
        value2.put(TodoListEntry.COLLUMN_TIME,"1456296776177");
        result = db.insert(TodoListEntry.TABLE_NAME,null,value2);
        Log.d(LOG_TAG, "insert list2 result: "+result);

        ContentValues item1 = new ContentValues();
        item1.put(TodoItemEntry.COLLUMN_CONTENT,"item 1");
        item1.put(TodoItemEntry.COLLUMN_IS_DONE,"0");
        item1.put(TodoItemEntry.COLLUMN_LIST_ID,"1");
        item1.put(TodoItemEntry.COLUMN_TIME,"1456297018129");

        ContentValues item2 = new ContentValues();
        item2.put(TodoItemEntry.COLLUMN_CONTENT,"item 2");
        item2.put(TodoItemEntry.COLLUMN_IS_DONE,"0");
        item2.put(TodoItemEntry.COLLUMN_LIST_ID,"1");
        item2.put(TodoItemEntry.COLUMN_TIME,"1456297018129");

        ContentValues item3 = new ContentValues();
        item3.put(TodoItemEntry.COLLUMN_CONTENT,"item 3");
        item3.put(TodoItemEntry.COLLUMN_IS_DONE,"1");
        item3.put(TodoItemEntry.COLLUMN_LIST_ID,"1");
        item3.put(TodoItemEntry.COLUMN_TIME,"1456297018129");

        result = db.insert(TodoItemEntry.TABLE_NAME,null, item1);
        Log.d(LOG_TAG, "insert item1 result: "+result);
        result = db.insert(TodoItemEntry.TABLE_NAME,null, item2);
        Log.d(LOG_TAG, "insert item2 result: "+result);
        result = db.insert(TodoItemEntry.TABLE_NAME,null, item3);
        Log.d(LOG_TAG, "insert item3 result: "+result);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TodoListEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TodoItemEntry.TABLE_NAME);
        onCreate(db);
    }
}
