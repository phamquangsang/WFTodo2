package phamsang.com.wftodo.BackgroundTask;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import phamsang.com.wftodo.DetailListAdapter;
import phamsang.com.wftodo.data.Contract;
import phamsang.com.wftodo.data.TodoDatabaseHelper;

/**
 * Created by Quang Quang on 2/24/2016.
 */
public class QueryTodoItemByListID extends AsyncTask<Integer ,Void, Cursor> {
    private Context mContext;
    private DetailListAdapter mAdapter;
    private final String LOG_TAG = QueryTodoItemByListID.class.getSimpleName();
    public QueryTodoItemByListID(Context context, DetailListAdapter adapter) {
        super();
        mContext = context;
        mAdapter = adapter;
    }

    @Override
    protected Cursor doInBackground(Integer... params) {
        TodoDatabaseHelper dbHelper = new TodoDatabaseHelper(mContext);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selection = Contract.TodoItemEntry.COLLUMN_LIST_ID+"=?";
        String iDList = Integer.toString(params[0]);
        String[] selectionArg = {iDList};
        Cursor c = db.query(Contract.TodoItemEntry.TABLE_NAME,null,selection,selectionArg,null,null,null,null);
        Log.d(LOG_TAG,"load todoItem of "+iDList+" in background: "+c.getCount()+" loaded");
        return c;
    }

    @Override
    protected void onPostExecute(Cursor cursor) {
        super.onPostExecute(cursor);
        mAdapter.swapCursor(cursor);//refresh dataset
    }
}
