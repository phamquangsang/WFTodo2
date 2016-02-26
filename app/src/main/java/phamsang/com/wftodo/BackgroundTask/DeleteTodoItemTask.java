package phamsang.com.wftodo.BackgroundTask;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import phamsang.com.wftodo.data.Contract;
import phamsang.com.wftodo.data.TodoDatabaseHelper;

/**
 * Created by Quang Quang on 2/24/2016.
 */
public class DeleteTodoItemTask extends AsyncTask<Integer, Void, Integer> {
    private final String LOG_TAG = DeleteTodoItemTask.class.getSimpleName();
    private Context mContext;
    public DeleteTodoItemTask(Context c) {
        super();
        mContext = c;
    }

    @Override
    protected Integer doInBackground(Integer... params) {
        TodoDatabaseHelper dbHelper = new TodoDatabaseHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = Contract.TodoItemEntry._ID+"=?";
        String[] selectionArg = {params[0].toString()};
        int result = db.delete(Contract.TodoItemEntry.TABLE_NAME,selection,selectionArg);
        Log.i(LOG_TAG, "delete todoItem Id: "+params[0].toString()+" - result code: "+result);
        return result;
    }
}
