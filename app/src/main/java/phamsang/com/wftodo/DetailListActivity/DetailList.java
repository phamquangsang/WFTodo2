package phamsang.com.wftodo.DetailListActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import phamsang.com.wftodo.BackgroundTask.DeleteTodoListTask;
import phamsang.com.wftodo.Color;
import phamsang.com.wftodo.R;
import phamsang.com.wftodo.ToDoListActivity.TodoListActivity;

public class DetailList extends AppCompatActivity implements DetailListFragment.OnListFragmentInteractionListener,
        AddNewTodoItemDialog.NoticeDialogListener, ChangeColorDialog.ColorPickedListener{




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int color = bundle.getInt(DetailListFragment.ARG_COLOR,3);

        toolbar.setBackgroundColor(Color.getCorlor(this, color));


        //set color for status bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.getCorlor(this,color));
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        DetailListFragment detailListFragment = new DetailListFragment();
        detailListFragment.setArguments(bundle);
        ft.add(R.id.detail_activity_container,detailListFragment,DetailListFragment.RECYCLER_FRAGMENT_TAG);
        ft.commit();
        Log.d("DetailListActivity","DetailListActivity: onCreate()");

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


        if(id == R.id.pick_color){
            ChangeColorDialog changeColorDialog = new ChangeColorDialog();
            Bundle arg = getIntent().getExtras();
            Bundle bundle = new Bundle();
            bundle.putInt(ChangeColorDialog.TODO_ITEM_LIST_ID,arg.getInt(DetailListFragment.ARG_ID_LIST));
            changeColorDialog.setArguments(bundle);
            changeColorDialog.show(getFragmentManager(),ChangeColorDialog.DIALOG_TAG);
        }else if( id== android.R.id.home){
            onBackPressed();
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
        DetailListFragment fragment = (DetailListFragment)getSupportFragmentManager().findFragmentByTag(DetailListFragment.RECYCLER_FRAGMENT_TAG);
        fragment.refeshList();

    }
    private void changeColor(int color){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.getCorlor(this,color));
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(Color.getCorlor(this, color));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        FragmentManager fm = getSupportFragmentManager();
        DetailListFragment detailListFragment =(DetailListFragment)fm.findFragmentByTag(DetailListFragment.RECYCLER_FRAGMENT_TAG);
        detailListFragment.changeColor(color);
    }



    @Override
    public void onColorPicked(int pickedColor) {
        changeColor(pickedColor);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        DetailListFragment detailFragment =(DetailListFragment)fm.findFragmentByTag(DetailListFragment.RECYCLER_FRAGMENT_TAG);
        String nonSpace = detailFragment.getListTitle().replaceAll("\\s","");
        if(nonSpace.isEmpty() && detailFragment.getAdapter().getItemCount()==0){
            //delete empty ListId
            DeleteTodoListTask deleteTodoListTask = new DeleteTodoListTask(this);
            deleteTodoListTask.execute(detailFragment.getIdList());
        }
        Log.d("DetailListActivity","onBackPressed()");
        super.onBackPressed();
    }
}
