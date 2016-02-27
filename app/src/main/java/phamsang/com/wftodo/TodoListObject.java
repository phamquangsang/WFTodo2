package phamsang.com.wftodo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import phamsang.com.wftodo.data.Contract;
import phamsang.com.wftodo.data.TodoDatabaseHelper;

/**
 * Created by Quang Quang on 2/26/2016.
 */
public class TodoListObject {
    private static final String LOG_TAG = TodoListObject.class.getSimpleName() ;
    private int mIdList;
    private int mColor;
    private String mTitle;
    private long mTime;
    private List<TodoItem> mListItem = new ArrayList<TodoItem>();
    Context mContext;



    public TodoListObject(Context c, int idList,  String title, int color, long time) {
        mIdList = idList;
        mColor = color;
        mTitle = title;
        mTime = time;
        mContext = c;

        TodoDatabaseHelper dbHelper = new TodoDatabaseHelper(mContext);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selection = Contract.TodoItemEntry.COLLUMN_LIST_ID+"=?";

        String[] selectionArg = {Integer.toString(idList)};
        Cursor dataSet = db.query(Contract.TodoItemEntry.TABLE_NAME,null,selection,selectionArg,null,null,null,null);
        Log.d(LOG_TAG,"load todoItem of "+idList+" in background: "+dataSet.getCount()+" loaded");
        db.close();

        //initialize ListItem
        if(dataSet!=null) {
            dataSet.moveToFirst();
            if (dataSet.getCount() != 0) {
                do {
                    int id =
                            dataSet.getInt(dataSet.getColumnIndex(Contract.TodoItemEntry._ID));
                    String content =
                            dataSet.getString(dataSet.getColumnIndex(Contract.TodoItemEntry.COLLUMN_CONTENT));
                    int isDone =
                            dataSet.getInt(dataSet.getColumnIndex(Contract.TodoItemEntry.COLLUMN_IS_DONE));
                    long iTime =
                            dataSet.getLong(dataSet.getColumnIndex(Contract.TodoItemEntry.COLUMN_TIME));
                    TodoItem item = new TodoItem(id, content, isDone, time, idList);
                    mListItem.add(item);
                    dataSet.moveToNext();
                } while (!dataSet.isAfterLast());

            }
            dataSet.close();
        }
    }

    public void setIdList(int idList) {
        mIdList = idList;
    }

    public int getIdList() {
        return mIdList;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        mColor = color;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        mTime = time;
    }

    public List<TodoItem> getListItem() {
        return mListItem;
    }

    public void setListItem(List<TodoItem> listItem) {
        mListItem = listItem;
    }
}
