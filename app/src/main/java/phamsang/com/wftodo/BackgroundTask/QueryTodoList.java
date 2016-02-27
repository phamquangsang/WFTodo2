package phamsang.com.wftodo.BackgroundTask;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import phamsang.com.wftodo.DetailListActivity.DetailListAdapter;
import phamsang.com.wftodo.ToDoListActivity.TodoListAdapter;
import phamsang.com.wftodo.data.Contract;
import phamsang.com.wftodo.data.TodoDatabaseHelper;

/**
 * Created by Quang Quang on 2/26/2016.
 */
public class QueryTodoList extends AsyncTask<Integer,Void,Cursor> {
    private Context mContext;
    private TodoListAdapter mAdapter;
    private final String LOG_TAG = QueryTodoItemByListID.class.getSimpleName();
    public QueryTodoList(Context context, TodoListAdapter adapter) {
        super();
        mContext = context;
        mAdapter = adapter;
    }

    @Override
    protected Cursor doInBackground(Integer... params) {
        TodoDatabaseHelper dbHelper = new TodoDatabaseHelper(mContext);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor c = db.query(Contract.TodoListEntry.TABLE_NAME,null,null,null,null,null,null,null);
        Log.d(LOG_TAG,"load all todoList "+" in background: "+c.getCount()+" loaded");
        db.close();
        return c;
    }

    @Override
    protected void onPostExecute(Cursor cursor) {
        mAdapter.swapCursor(cursor);//refresh dataset
    }
}
