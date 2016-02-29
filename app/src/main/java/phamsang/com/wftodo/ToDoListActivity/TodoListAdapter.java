package phamsang.com.wftodo.ToDoListActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import phamsang.com.wftodo.Color;
import phamsang.com.wftodo.DetailListActivity.DetailList;
import phamsang.com.wftodo.DetailListActivity.DetailListFragment;
import phamsang.com.wftodo.R;
import phamsang.com.wftodo.TodoItem;
import phamsang.com.wftodo.TodoListObject;
import phamsang.com.wftodo.data.Contract;


public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.ViewHolder> {
    private static final String LOG_TAG = TodoListAdapter.class.getSimpleName() ;


    private Context mContext;


    private List<TodoListObject> mDataSet = new ArrayList<TodoListObject>();


    public TodoListAdapter(Context context, Cursor dataSet) {
        super();
        mContext =context;
        initializeDataset(dataSet);
    }

    public List<TodoListObject> getDataSet() {

        return mDataSet;
    }


    @Override
    public TodoListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_list,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TodoListAdapter.ViewHolder holder, int position) {
        if(mDataSet==null){
            return;
        }

        TodoListObject item = mDataSet.get(position);
        holder.mData = item;
        if(holder.mView instanceof CardView){
            CardView cardView = (CardView)holder.mView;
            cardView.setCardBackgroundColor(Color.getCorlor(mContext,item.getColor()));
        }
        //holder.mView.setBackgroundColor(Color.getCorlor(mContext,item.getColor()));
        String title = item.getTitle();
        holder.mIdList = item.getIdList();
        if(title.isEmpty()){
            holder.mTitleTextView.setVisibility(View.GONE);
        }else{
            holder.mTitleTextView.setText(title);
            holder.mTitleTextView.setVisibility(View.VISIBLE);
        }
        holder.mView.setTag(holder);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TodoListAdapter.ViewHolder vh = (TodoListAdapter.ViewHolder)v.getTag();
                vh.startActivityDetail();
            }
        });


        List<TodoItem> listItem = item.getListItem();
        int childCount = holder.mContainer.getChildCount();
        int todoItemCount = listItem.size();
        int cout = (todoItemCount<childCount)?todoItemCount:childCount;
        for(int i=0;i<childCount;++i){
            View linearLayout = holder.mContainer.getChildAt(i);
            if(linearLayout instanceof LinearLayout){
                if(i<cout){
                    ViewGroup container = (ViewGroup)linearLayout;
                    container.setVisibility(View.VISIBLE);
                    CheckBox checkBox = (CheckBox)container.getChildAt(0);
                    TodoItem todoItem = listItem.get(i);
                    checkBox.setChecked((todoItem.getmIsDone()==1?true:false));
                    TextView textView = (TextView)container.getChildAt(1);
                    textView.setText(todoItem.getmContent());
                }else{
                    linearLayout.setVisibility(View.GONE);
                }

            }
        }

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private int mIdList;
        private TextView mTitleTextView;
        private View mView;
        private ViewGroup mContainer;
        private TodoListObject mData;

        public TodoListObject getData(){
            return mData;
        }

        public int getIdList() {
            return mIdList;
        }

        public ViewHolder(View itemView) {

            super(itemView);
            mView = itemView;
            mContainer = (ViewGroup)itemView.findViewById(R.id.container);
            mTitleTextView = (TextView)itemView.findViewById(R.id.text_view_title);

        }
        public void startActivityDetail(){
            AppCompatActivity activity = (AppCompatActivity) mContext;
            Intent intent = new Intent(mContext,DetailList.class);
            Bundle bundle = new Bundle();
            bundle.putInt(DetailListFragment.ARG_ID_LIST,mIdList);
            bundle.putString(DetailListFragment.ARG_TITLE, mData.getTitle());
            bundle.putInt(DetailListFragment.ARG_COLOR,mData.getColor());
            intent.putExtras(bundle);

            ((AppCompatActivity) mContext).startActivityForResult(intent, TodoListActivity.DETAIL_ACTIVITY_REQUEST_CODE);
        }
    }

    public void swapCursor(Cursor dataSet){
        initializeDataset(dataSet);
        notifyDataSetChanged();
    }
    private void initializeDataset(Cursor dataSet){
        if(dataSet!=null){

            //Log.d(LOG_TAG,"cursor size: "+dataSet.getCount());
            dataSet.moveToFirst();
            mDataSet.clear();
            if(dataSet.getCount()!=0){
                do {
                    int id =
                            dataSet.getInt(dataSet.getColumnIndex(Contract.TodoListEntry._ID));
                    String title =
                            dataSet.getString(dataSet.getColumnIndex(Contract.TodoListEntry.COLLUMN_TITLE));

                    long time =
                            dataSet.getLong(dataSet.getColumnIndex(Contract.TodoListEntry.COLLUMN_TIME));
                    int color =
                            dataSet.getInt(dataSet.getColumnIndex(Contract.TodoListEntry.COLLUMN_COLOR));
                    TodoListObject item = new TodoListObject(mContext,id,title,color,time);
                    mDataSet.add(item);
                    dataSet.moveToNext();
                }while (!dataSet.isAfterLast());

            }
            dataSet.close();
            Log.d(LOG_TAG,"cursor size: "+dataSet.getCount()+" - mDataset: "+mDataSet.size());
        }
        else{
            Log.d(LOG_TAG,"new cursor is null");

        }
    }
}
