package phamsang.com.wftodo.BackgroundTask;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Calendar;

import phamsang.com.wftodo.DetailListActivity.DetailList;
import phamsang.com.wftodo.DetailListActivity.DetailListFragment;
import phamsang.com.wftodo.ToDoListActivity.TodoListActivity;
import phamsang.com.wftodo.data.Contract;
import phamsang.com.wftodo.data.TodoDatabaseHelper;

/**
 * Created by Quang Quang on 2/27/2016.
 */
public class CreateNewListTask extends AsyncTask<ContentValues,Void,Integer> {
    private static final String LOG_TAG = CreateNewListTask.class.getSimpleName() ;
    private Context mContext;
    public CreateNewListTask(Context context) {
        super();
        mContext = context;
    }

    @Override
    protected Integer doInBackground(ContentValues... params) {
        TodoDatabaseHelper dbHelper = new TodoDatabaseHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int result=0;
        int failed = 0;
        long resultId=0;
        ContentValues value = new ContentValues();
        value.put(Contract.TodoListEntry.COLLUMN_TITLE,"");
        value.put(Contract.TodoListEntry.COLLUMN_COLOR,1);//should be random later
        value.put(Contract.TodoListEntry.COLLUMN_TIME, Calendar.getInstance().getTimeInMillis());
        resultId = db.insert(Contract.TodoListEntry.TABLE_NAME,null,value);
//        for(int i=0;i<params.length;++i){
//            resultId = db.insert(Contract.TodoListEntry.TABLE_NAME,null,params[i]);
//            if(resultId!=-1){
//                result++;
//            }else
//                failed++;
//        }
        Log.i(LOG_TAG, "insert TodoList total: "+result+" record(s) - failed: "+failed + " idList: "+resultId);
        db.close();
        return (int)resultId;
    }

    @Override
    protected void onPostExecute(Integer idList) {
        super.onPostExecute(idList);
        if(idList==0){
            Log.d(LOG_TAG,"insert new TodoList failed");
        }else{

        }
        AppCompatActivity activity = (AppCompatActivity) mContext;
        Intent intent = new Intent(mContext,DetailList.class);
        Bundle bundle = new Bundle();
        bundle.putInt(DetailListFragment.ARG_ID_LIST,idList);
        intent.putExtras(bundle);

        ((AppCompatActivity) mContext).startActivityForResult(intent, TodoListActivity.DETAIL_ACTIVITY_REQUEST_CODE);
    }
}
