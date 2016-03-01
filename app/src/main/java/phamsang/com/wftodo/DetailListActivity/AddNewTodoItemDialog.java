package phamsang.com.wftodo.DetailListActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;

import java.util.Calendar;

import phamsang.com.wftodo.BackgroundTask.InsertTodoItemTask;
import phamsang.com.wftodo.BackgroundTask.UpdateTodoItemTask;
import phamsang.com.wftodo.R;
import phamsang.com.wftodo.data.Contract;

/**
 * Created by Quang Quang on 2/25/2016.
 */
public class AddNewTodoItemDialog extends DialogFragment {
    public static final String DIALOG_TAG = "dialog_tag";
    public static final String ID_TODO_ITEM = "todo_item_id";
    public static final String TODO_ITEM_LIST_ID = "todo_item_list_id";
    public static final String IS_DONE = "is_done";
    public static final String CONTENT = "content";
    //if id from bundle is null -> add new item, else update the old one.
    private int mIdItem;

    private int mListId;
    private String mContent;
    private boolean mIsDone;

    private EditText editText;
    private CheckBox checkBox;

    public AddNewTodoItemDialog() {
        super();

    }




    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Bundle bundle = getArguments();
        if(bundle!=null){
            mIdItem = bundle.getInt(ID_TODO_ITEM,-1);
            mListId = bundle.getInt(TODO_ITEM_LIST_ID,-1);
            mIsDone = bundle.getBoolean(IS_DONE,false);
            mContent = bundle.getString(CONTENT,"");
        }else{
            mIdItem = -1;
            mListId =1;
            mIsDone = false;
            mContent ="";
        }
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout


        View rootView = inflater.inflate(R.layout.todo_item_dialog, null);
        editText = (EditText)rootView.findViewById(R.id.edit_text_item);
        checkBox = (CheckBox)rootView.findViewById(R.id.checkBox);
        checkBox.setChecked(mIsDone);
        editText.setText(mContent);
        builder.setView(rootView)
                // Add action buttons
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String content = editText.getText().toString().replaceAll("\\s","");
                        if(content.isEmpty()==true){
                            return;
                        }
                        Calendar carlendar = Calendar.getInstance();
                        final long time = carlendar.getTimeInMillis();
                        ContentValues value = new ContentValues();
                        value.put(Contract.TodoItemEntry.COLLUMN_CONTENT,content);
                        value.put(Contract.TodoItemEntry.COLLUMN_IS_DONE,checkBox.isChecked());
                        value.put(Contract.TodoItemEntry.COLUMN_TIME,time);
                        value.put(Contract.TodoItemEntry.COLLUMN_LIST_ID,mListId);
                        if(mIdItem==-1){//insert to database
                            InsertTodoItemTask insertTask = new InsertTodoItemTask(getContext());
                            insertTask.execute(value);
                        }else{//update
                            UpdateTodoItemTask updateTask = new UpdateTodoItemTask(getContext(),value,mIdItem);
                            updateTask.execute();
                        }
                        mListener.onDialogPositiveClick(AddNewTodoItemDialog.this);
                    }
                })
                .setNegativeButton(R.string.discard, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        return builder.create();
    }




    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }
    // Use this instance of the interface to deliver action events
    NoticeDialogListener mListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }
}
