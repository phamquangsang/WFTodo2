package phamsang.com.wftodo.DetailListActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import phamsang.com.wftodo.R;
import phamsang.com.wftodo.ToDoListActivity.TodoListActivity;

public class DetailList extends AppCompatActivity implements DetailListFragment.OnListFragmentInteractionListener,
        AddNewTodoItemDialog.NoticeDialogListener {


    private static final String RECYCLER_FRAGMENT = "recycler_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Intent intent = getIntent();



        //create arg and set to fragment;

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        DetailListFragment detailListFragment = new DetailListFragment();
        detailListFragment.setArguments(intent.getExtras());
        ft.add(R.id.detail_activity_container,detailListFragment,RECYCLER_FRAGMENT);
        ft.commit();
        Log.d("DetailListActivity","DetailListActivity: onCreate()");

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_list, menu);
        return true;
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListFragmentInteraction(String dumyData) {

    }
    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
//        DetailListFragment fragment =
//                (DetailListFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_detail_list);
//        fragment.refeshList();
        DetailListFragment fragment = (DetailListFragment)getSupportFragmentManager().findFragmentByTag(RECYCLER_FRAGMENT);
        fragment.refeshList();

    }
}
