package phamsang.com.wftodo;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;


import phamsang.com.wftodo.BackgroundTask.QueryTodoItemByListID;
import phamsang.com.wftodo.data.Contract;
import phamsang.com.wftodo.data.Contract.TodoItemEntry;
import phamsang.com.wftodo.data.Contract.TodoListEntry;
import phamsang.com.wftodo.data.TodoDatabaseHelper;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailListFragment extends Fragment {
    private static final java.lang.String DIALOG_ADD_NEW_ITEM = AddNewTodoItemDialog.class.getSimpleName();
    private final String LOG_TAG = DetailListFragment.class.getSimpleName();
    private DetailListAdapter mAdapter;

    public void refeshList(){
        mAdapter.RefeshList();
    }

    private static final String[] TodoItemCollumns =
            {TodoItemEntry.TABLE_NAME+"."+TodoItemEntry._ID,
            TodoItemEntry.COLLUMN_CONTENT,
            TodoItemEntry.COLLUMN_IS_DONE,
            TodoItemEntry.COLUMN_TIME};

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String ARG_ID_LIST = "id-list";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private int mIdList = 1;
    private OnListFragmentInteractionListener mListener;

    public DetailListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static DetailListFragment newInstance(int columnCount,int idList) {
        DetailListFragment fragment = new DetailListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putInt(ARG_ID_LIST, idList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            mIdList = getArguments().getInt(ARG_ID_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail_list, container, false);
        Context context =getContext();
        FloatingActionButton fab = (FloatingActionButton)rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTodoItemDialog dialog = new AddNewTodoItemDialog();
                dialog.show(getFragmentManager(),DIALOG_ADD_NEW_ITEM);
            }
        });
        RecyclerView recyclerView = (RecyclerView)rootView.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
//            TodoDatabaseHelper db = new TodoDatabaseHelper(context);
//            String selection = TodoItemEntry.COLLUMN_LIST_ID+"=?";
//            String[] selectionArg = {Integer.toString(mIdList)};
//            Cursor c = db.getReadableDatabase().query(Contract.TodoItemEntry.TABLE_NAME,TodoItemCollumns,selection,selectionArg,null,null,null,null);
//            Log.d(LOG_TAG,"load todoItem of "+mIdList+": "+c.getCount()+" loaded");
        //set up adapter

        DetailListAdapter adapter = new DetailListAdapter(null,getContext(),mIdList);
        mAdapter = adapter;
        recyclerView.setAdapter(adapter);


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
    public void onDetach() {
        super.onDetach();
        mListener = null;
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


}
