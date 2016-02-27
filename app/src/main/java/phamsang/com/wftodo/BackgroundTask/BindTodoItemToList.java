package phamsang.com.wftodo.BackgroundTask;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import phamsang.com.wftodo.DetailListActivity.DetailListAdapter;
import phamsang.com.wftodo.R;
import phamsang.com.wftodo.TodoItem;
import phamsang.com.wftodo.TodoListObject;
import phamsang.com.wftodo.data.Contract;
import phamsang.com.wftodo.data.TodoDatabaseHelper;

/**
 * Created by Quang Quang on 2/26/2016.
 */
public class BindTodoItemToList extends AsyncTask<Integer ,Void, Cursor> {
    private Context mContext;
    private ViewGroup mContainer;
    private TodoListObject mItem;
    private final String LOG_TAG = BindTodoItemToList.class.getSimpleName();
    public BindTodoItemToList(Context context, TodoListObject item, ViewGroup container) {
        super();
        mContext = context;
        mContainer = container;
        mItem = item;
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
    protected void onPostExecute(Cursor dataSet) {
        super.onPostExecute(dataSet);
        if(dataSet!=null){

            //Log.d(LOG_TAG,"cursor size: "+dataSet.getCount());
            dataSet.moveToFirst();

            if(dataSet.getCount()!=0){
                do {
                    int id =
                            dataSet.getInt(dataSet.getColumnIndex(Contract.TodoItemEntry._ID));
                    String content =
                            dataSet.getString(dataSet.getColumnIndex(Contract.TodoItemEntry.COLLUMN_CONTENT));
                    int isDone =
                            dataSet.getInt(dataSet.getColumnIndex(Contract.TodoItemEntry.COLLUMN_IS_DONE));
                    LayoutInflater inflater =
                        (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View view = (View)inflater.inflate(R.layout.compat_todo_item,null);
                    TextView contentView = (TextView)view.findViewById(R.id.compat_content_text_view);
                    contentView.setText(content);
                    CheckBox checkBox = (CheckBox)view.findViewById(R.id.checkBox_compat);
                    checkBox.setChecked((isDone==1)?true:false);
                    mContainer.addView(view);



                    dataSet.moveToNext();
                }while (!dataSet.isAfterLast());

            }


        }

    }
}
