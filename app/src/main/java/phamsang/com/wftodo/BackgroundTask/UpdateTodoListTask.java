package phamsang.com.wftodo.BackgroundTask;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import phamsang.com.wftodo.data.Contract;
import phamsang.com.wftodo.data.TodoDatabaseHelper;

/**
 * Created by Quang Quang on 2/26/2016.
 */
public class UpdateTodoListTask extends AsyncTask<Void, Void, Integer> {
    private Context mContext;
    private int mListId;
    private ContentValues mValue;
    private final String LOG_TAG = UpdateTodoListTask.class.getSimpleName();
    public UpdateTodoListTask(Context context,ContentValues newValue,int updateID) {
        super();
        mContext = context;
        mListId=updateID;
        mValue=newValue;
    }


    @Override
    protected Integer doInBackground(Void... params) {
        TodoDatabaseHelper dbHelper = new TodoDatabaseHelper(mContext);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selection = Contract.TodoListEntry._ID+"=?";
        String[] selectionArg = {Integer.toString(mListId)};
        int result = db.update(Contract.TodoListEntry.TABLE_NAME,mValue,selection,selectionArg);
        return result;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        Log.i(LOG_TAG,"update todoList Id: "+mListId+" - finish code: "+integer);
    }
}
