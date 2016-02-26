package phamsang.com.wftodo.BackgroundTask;

import android.content.ContentValues;
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
public class UpdateTodoItemTask  extends AsyncTask<Void ,Void, Integer> {
    private Context mContext;
    private int mId;
    private ContentValues mValue;
    private final String LOG_TAG = UpdateTodoItemTask.class.getSimpleName();
    public UpdateTodoItemTask(Context context,ContentValues newValue,int updateID) {
        super();
        mContext = context;
        mId=updateID;
        mValue=newValue;
    }


    @Override
    protected Integer doInBackground(Void... params) {
        TodoDatabaseHelper dbHelper = new TodoDatabaseHelper(mContext);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selection = Contract.TodoItemEntry._ID+"=?";
        String[] selectionArg = {Integer.toString(mId)};
        int result = db.update(Contract.TodoItemEntry.TABLE_NAME,mValue,selection,selectionArg);
        return result;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        Log.i(LOG_TAG,"update todoItem Id: "+mId+" - finish code: "+integer);
    }
}