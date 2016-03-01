package phamsang.com.wftodo.ToDoListActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;

import phamsang.com.wftodo.BackgroundTask.CreateNewListTask;
import phamsang.com.wftodo.BackgroundTask.DeleteTodoListTask;
import phamsang.com.wftodo.BackgroundTask.QueryTodoList;
import phamsang.com.wftodo.R;
import phamsang.com.wftodo.data.Contract;
import phamsang.com.wftodo.data.TodoDatabaseHelper;

public class TodoListActivity extends AppCompatActivity {
    public static final int DETAIL_ACTIVITY_REQUEST_CODE=1;
    private static final String LOG_TAG = TodoListActivity.class.getSimpleName();
    private int mSpanCount = 2;//collumn number of StaggeredLayoutManager
    private RecyclerView mRecyclerView;
    private TodoListAdapter mAdapter;

    public static final String EXTRAS_BUNDLE = "extra_bundle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Todo List");

        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(mSpanCount, StaggeredGridLayoutManager.VERTICAL));
        mAdapter = new TodoListAdapter(this,null);
        mRecyclerView.setAdapter(mAdapter);
        refeshList();

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                TodoListAdapter.ViewHolder todoViewHolder = (TodoListAdapter.ViewHolder)viewHolder;
//                DeleteTodoListTask deleteTodoListTask = new DeleteTodoListTask(getApplicationContext());
//                deleteTodoListTask.execute(todoViewHolder.getIdList());

                TodoDatabaseHelper dbHelper = new TodoDatabaseHelper(TodoListActivity.this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                String selection = Contract.TodoListEntry._ID+"=?";
                String[] selectionArg = {Integer.toString(todoViewHolder.getIdList())};
                int result = db.delete(Contract.TodoListEntry.TABLE_NAME,selection,selectionArg);
                Log.i(LOG_TAG, "delete todoList Id: "+todoViewHolder.getIdList()+" - result code: "+result);
                db.close();

                if(result!=0){
                    mAdapter.getDataSet().remove(todoViewHolder.getData());
                    mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());

                }
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateNewListTask createListTask = new CreateNewListTask(view.getContext());
                createListTask.execute();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        recreate();

    }
    public void refeshList(){
        QueryTodoList queryTodoList = new QueryTodoList(this, mAdapter);
        queryTodoList.execute();
    }



}
