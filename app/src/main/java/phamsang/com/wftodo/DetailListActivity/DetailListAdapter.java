package phamsang.com.wftodo.DetailListActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import phamsang.com.wftodo.BackgroundTask.DeleteTodoItemTask;
import phamsang.com.wftodo.BackgroundTask.QueryTodoItemByListID;
import phamsang.com.wftodo.BackgroundTask.UpdateTodoItemTask;
import phamsang.com.wftodo.R;
import phamsang.com.wftodo.TodoItem;
import phamsang.com.wftodo.data.Contract;

/**
 * Created by Quang Quang on 2/24/2016.
 */
public class DetailListAdapter extends RecyclerView.Adapter<DetailListAdapter.ViewHolder> {
    private static final String LOG_TAG = DetailListAdapter.class.getSimpleName();
    private Context mContext;
    private List<TodoItem> mDataSet = new ArrayList<TodoItem>();
    private DetailListAdapter mAdapter;
    private int mIdList;


    public DetailListAdapter(Cursor dataSet, Context context,int idList){
        Log.d(LOG_TAG,"contructing Adapter...");
        mContext = context;
        mAdapter = this;
        mIdList = idList;
        //initializeDataset(dataSet);
        if(dataSet!=null){
            dataSet.moveToFirst();
            mDataSet.clear();
            do{
                int id =
                        dataSet.getInt(dataSet.getColumnIndex(Contract.TodoItemEntry._ID));
                String content =
                        dataSet.getString(dataSet.getColumnIndex(Contract.TodoItemEntry.COLLUMN_CONTENT));
                int isDone =
                        dataSet.getInt(dataSet.getColumnIndex(Contract.TodoItemEntry.COLLUMN_IS_DONE));
                long time =
                        dataSet.getLong(dataSet.getColumnIndex(Contract.TodoItemEntry.COLUMN_TIME));
                TodoItem item = new TodoItem(id,content,isDone,time,idList);
                mDataSet.add(item);
                dataSet.moveToNext();
            }while (!dataSet.isAfterLast());
            Log.d(LOG_TAG,"cursor size: "+dataSet.getCount()+" - idList: "+idList+" - mDataset: "+mDataSet.size());
        }

    }
    @Override
    public int getItemCount() {
        if(mDataSet!=null){
            Log.d(LOG_TAG,"Dataset in adapter count: "+mDataSet.size());
            return mDataSet.size();
        }

        else
            Log.d(LOG_TAG,"Dataset is null");

        return 0;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(mDataSet==null){
            return;
        }
        TodoItem item = mDataSet.get(position);
        int isDone = item.getmIsDone();
        if(isDone==0){
            holder.mCheckbox.setChecked(false);
        }else
            holder.mCheckbox.setChecked(true);
        holder.mEditText.setText(item.getmContent());
        int idList= item.getmIdList();
        holder.mID = item.getmId();
        holder.mPosition = position;
        String idString = Integer.toString(item.getmId());
        holder.mTextViewId.setText(idString);
        holder.mTextViewId.setVisibility(View.GONE);
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final CheckBox mCheckbox;
        public final EditText mEditText;
        public final TextView mTextViewId;
        public final ImageView mDeleteView;
        public int mID;
        public int mPosition;



        public ViewHolder(View view) {
            super(view);
            mView = view;
            mCheckbox = (CheckBox) view.findViewById(R.id.checkBox);
            mCheckbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ContentValues cV = new ContentValues();
                    //cV.put(Contract.TodoItemEntry.COLLUMN_CONTENT,mID);
                    int b = (mCheckbox.isChecked())?1:0;
                    cV.put(Contract.TodoItemEntry.COLLUMN_IS_DONE,b);
                    cV.put(Contract.TodoItemEntry.COLLUMN_CONTENT,mEditText.getText().toString());
                    cV.put(Contract.TodoItemEntry.COLLUMN_LIST_ID,mIdList);
                    long l = Calendar.getInstance().getTimeInMillis();
                    cV.put(Contract.TodoItemEntry.COLUMN_TIME,Long.toString(l));
                    UpdateTodoItemTask updateTask = new UpdateTodoItemTask(mContext,cV,mID);
                    updateTask.execute();
                }
            });
            mEditText = (EditText) view.findViewById(R.id.edit_text_item);
            mEditText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String content = mEditText.getText().toString();
                    boolean isDone = mCheckbox.isChecked();
                    Bundle arg= new Bundle();
                    arg.putString(AddNewTodoItemDialog.CONTENT,content);
                    arg.putInt(AddNewTodoItemDialog.ID_TODO_ITEM,mID);
                    arg.putInt(AddNewTodoItemDialog.TODO_ITEM_LIST_ID,mIdList);
                    arg.putBoolean(AddNewTodoItemDialog.IS_DONE,isDone);

                    AddNewTodoItemDialog dialog = new AddNewTodoItemDialog();
                    dialog.setArguments(arg);
                    FragmentActivity activity = (FragmentActivity)mContext;
                    dialog.show(activity.getSupportFragmentManager(),AddNewTodoItemDialog.DIALOG_TAG);


                }
            });

            mTextViewId = (TextView) view.findViewById(R.id.text_view_id1);
            mDeleteView = (ImageView)view.findViewById(R.id.deleteButton);
            mDeleteView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DeleteTodoItemTask deleteTask = new DeleteTodoItemTask(mContext);
                    deleteTask.execute(mID);
                    mDataSet.remove(mPosition);
                    notifyItemRemoved(mPosition);
                    notifyItemRangeChanged(mPosition,mDataSet.size());
                    //mAdapter.RefeshList();
                }
            });

        }

//        @Override
//        public String toString() {
//            return super.toString() + " '" + mContentView.getText() + "'";
//        }
    }

    public void swapCursor(Cursor dataSet){
        Log.d(LOG_TAG,"swaping new cursor");
        initializeDataset(dataSet);
        notifyDataSetChanged();;
    }
    private void initializeDataset(Cursor dataSet){
        if(dataSet!=null){

            //Log.d(LOG_TAG,"cursor size: "+dataSet.getCount());
            dataSet.moveToFirst();
            mDataSet.clear();
            if(dataSet.getCount()!=0){
                do {
                    int id =
                            dataSet.getInt(dataSet.getColumnIndex(Contract.TodoItemEntry._ID));
                    String content =
                            dataSet.getString(dataSet.getColumnIndex(Contract.TodoItemEntry.COLLUMN_CONTENT));
                    int isDone =
                            dataSet.getInt(dataSet.getColumnIndex(Contract.TodoItemEntry.COLLUMN_IS_DONE));
                    long time =
                            dataSet.getLong(dataSet.getColumnIndex(Contract.TodoItemEntry.COLUMN_TIME));
                    int idList =
                            dataSet.getInt(dataSet.getColumnIndex(Contract.TodoItemEntry.COLLUMN_LIST_ID));
                    TodoItem item = new TodoItem(id,content,isDone,time,idList);
                    mDataSet.add(item);
                    dataSet.moveToNext();
                }while (!dataSet.isAfterLast());

            }

            Log.d(LOG_TAG,"cursor size: "+dataSet.getCount()+" - mDataset: "+mDataSet.size());
        }
        else{
            Log.d(LOG_TAG,"new cursor is null");

        }
    }
    public void addNewsEmptyItem(){

//        InsertTodoItemTask insertTask = new InsertTodoItemTask(mContext);
//        ContentValues emptyValue = new ContentValues();
//        emptyValue.put(Contract.TodoItemEntry.COLLUMN_CONTENT,"");
//        emptyValue.put(Contract.TodoItemEntry.COLLUMN_LIST_ID,mIdList);
//        emptyValue.put(Contract.TodoItemEntry.COLLUMN_IS_DONE,false);
//        emptyValue.put(Contract.TodoItemEntry.COLUMN_TIME,0000);
//        insertTask.execute(emptyValue);
//        notifyItemInserted(mItemCount);
//        mItemCount++;

    }
    public void RefeshList(){
        QueryTodoItemByListID queryTask = new QueryTodoItemByListID(mContext,this);
        queryTask.execute(mIdList);
    }
}
