package phamsang.com.wftodo.DetailListActivity;

import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import phamsang.com.wftodo.BackgroundTask.CreateNewListTask;
import phamsang.com.wftodo.BackgroundTask.DeleteTodoListTask;
import phamsang.com.wftodo.BackgroundTask.QueryTodoItemByListID;
import phamsang.com.wftodo.BackgroundTask.UpdateTodoListTask;
import phamsang.com.wftodo.Color;
import phamsang.com.wftodo.R;
import phamsang.com.wftodo.data.Contract;
import phamsang.com.wftodo.data.Contract.TodoItemEntry;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailListFragment extends Fragment {
    private static final java.lang.String DIALOG_ADD_NEW_ITEM = AddNewTodoItemDialog.class.getSimpleName();
    public static final int INVALID_ID_LIST = -1;
    public static final String RECYCLER_FRAGMENT_TAG = "recycler_fragment";
    private final String LOG_TAG = DetailListFragment.class.getSimpleName();

    private DetailListAdapter mAdapter;
    private boolean mIsTitleChanged=false;
    private int mColumnCount = 1;
    private int mIdList = 1;
    private String mListTitle="";
    private int mColor;
    private RecyclerView mRecyclerView;
    private View mRootView;
    private OnListFragmentInteractionListener mListener;

    public void setIdList(int idList) {
        mIdList = idList;
    }

    public void refeshList(){

        Log.d(LOG_TAG,"refeshList() running in DetailListFragment");
        mAdapter.RefeshList();
    }

    private static final String[] TodoItemCollumns =
            {TodoItemEntry.TABLE_NAME+"."+TodoItemEntry._ID,
            TodoItemEntry.COLLUMN_CONTENT,
            TodoItemEntry.COLLUMN_IS_DONE,
            TodoItemEntry.COLUMN_TIME};

    // TODO: Customize parameter argument names
    public static final String ARG_COLUMN_COUNT = "column-count";
    public static final String ARG_ID_LIST = "id-list";
    public static final String ARG_TITLE = "list_title";
    public static final String ARG_COLOR = "list_color";



    public DetailListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static DetailListFragment newInstance(int columnCount,int idList, String title) {
        DetailListFragment fragment = new DetailListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putInt(ARG_ID_LIST, idList);
        args.putString(ARG_TITLE,title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT,1);
        mIdList = getArguments().getInt(ARG_ID_LIST,-1);
        mListTitle = getArguments().getString(ARG_TITLE,"");
        mColor = getArguments().getInt(ARG_COLOR, Color.getRandomColor());
        if(mIdList==INVALID_ID_LIST)
        {
            CreateNewListTask createTask = new CreateNewListTask(getContext());

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail_list, container, false);
        mRootView = rootView;


        Context context =getContext();
        FloatingActionButton fab = (FloatingActionButton)rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTodoItemDialog dialog = new AddNewTodoItemDialog();
                Bundle arg = new Bundle();
                arg.putInt(AddNewTodoItemDialog.TODO_ITEM_LIST_ID,mIdList);
                dialog.setArguments(arg);
                dialog.show(getFragmentManager(),DIALOG_ADD_NEW_ITEM);
            }
        });
        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));


        DetailListAdapter adapter = new DetailListAdapter(null,getContext(),mIdList);
        mAdapter = adapter;
        mRecyclerView.setAdapter(adapter);

        //set up color
        rootView.setBackgroundColor(Color.getCorlor(getContext(),mColor));
        mRecyclerView.setBackgroundColor(Color.getCorlor(getContext(),mColor));

        EditText titleEditText = (EditText) rootView.findViewById(R.id.title_edit_text);
        titleEditText.setText(mListTitle);
        titleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //Toast.makeText(getContext(),s.toString(),Toast.LENGTH_SHORT).show();
                mIsTitleChanged=true;
                mListTitle = s.toString();
                if(mIsTitleChanged==true){
                    ContentValues value = new ContentValues();
                    value.put(Contract.TodoListEntry.COLLUMN_TITLE,mListTitle);
                    UpdateTodoListTask updateListTask = new UpdateTodoListTask(getContext(),value,mIdList);
                    updateListTask.execute();
                    //Toast.makeText(getContext(),mListTitle+" ready to save..",Toast.LENGTH_SHORT).show();
                }
            }
        });
        titleEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });


        QueryTodoItemByListID queryTask = new QueryTodoItemByListID(context,adapter);
        queryTask.execute(mIdList);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }


        return rootView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.d(LOG_TAG,"onPause() running - mListTitle= "+mListTitle+" - "+" adapter item count: "+mAdapter.getItemCount());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        Log.d(LOG_TAG,"onDetach()");

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(String dumyData);
    }

    public void changeColor(int color){
        mColor = color;
        mRecyclerView.setBackgroundColor(Color.getCorlor(getContext(),color));
        mRootView.setBackgroundColor(Color.getCorlor(getContext(),color));
    }

    public int getIdList() {
        return mIdList;
    }

    public boolean isTitleChanged() {
        return mIsTitleChanged;
    }

    public String getListTitle() {
        return mListTitle;
    }

    public DetailListAdapter getAdapter() {
        return mAdapter;
    }
}
