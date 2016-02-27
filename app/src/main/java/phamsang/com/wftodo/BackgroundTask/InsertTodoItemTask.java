package phamsang.com.wftodo.BackgroundTask;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import phamsang.com.wftodo.TodoItem;
import phamsang.com.wftodo.data.Contract;
import phamsang.com.wftodo.data.TodoDatabaseHelper;

/**
 * Created by Quang Quang on 2/24/2016.
 */
public class InsertTodoItemTask extends AsyncTask<ContentValues,Void,Integer> {
    private Context mContext;
    private final String LOG_TAG = InsertTodoItemTask.class.getSimpleName();
    public InsertTodoItemTask(Context c) {
        super();
        mContext = c;
    }

    @Override
    protected Integer doInBackground(ContentValues... params) {
        TodoDatabaseHelper dbHelper = new TodoDatabaseHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int result=0;
        int failed = 0;
        for(int i=0;i<params.length;++i){
            if(db.insert(Contract.TodoItemEntry.TABLE_NAME,null,params[i])!=-1){
                result++;
            }else
                failed++;
        }
        Log.i(LOG_TAG, "insert todoItem total: "+result+" record(s) - failed: "+failed);
        db.close();
        return result;
    }
}
