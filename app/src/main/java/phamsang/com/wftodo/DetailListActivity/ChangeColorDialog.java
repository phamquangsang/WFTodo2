package phamsang.com.wftodo.DetailListActivity;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import phamsang.com.wftodo.BackgroundTask.UpdateTodoListTask;
import phamsang.com.wftodo.Color;
import phamsang.com.wftodo.R;
import phamsang.com.wftodo.data.Contract;


public class ChangeColorDialog extends DialogFragment {
    public static final String DIALOG_TAG = "dialog_tag";
    public static final java.lang.String TODO_ITEM_LIST_ID = "todo_item_list_id";
    private int mListId;
    private ColorPickedListener mListener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Bundle bundle = getArguments();
        if(bundle!=null){
            mListId = bundle.getInt(TODO_ITEM_LIST_ID,-1);
        }else{
            mListId =-1;
        }
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View rootView = inflater.inflate(R.layout.pick_color, null);
        GridLayout gridLayout = (GridLayout)rootView.findViewById(R.id.grid_layout_color_container);
        for(int i=0;i<gridLayout.getChildCount();++i){
            ImageView im = (ImageView)gridLayout.getChildAt(i);
            im.setBackgroundColor(Color.getCorlor(getActivity(),i+1));

            im.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewGroup vg =(ViewGroup)v.getParent();
                    for(int i=0;i<vg.getChildCount();++i){
                        if(vg.getChildAt(i)==v){
                            ContentValues value = new ContentValues();
                            value.put(Contract.TodoListEntry.COLLUMN_COLOR, i+1);
                            UpdateTodoListTask updateTodoListTask = new UpdateTodoListTask(getActivity(),value,mListId);
                            updateTodoListTask.execute();
                            //Toast.makeText(getActivity(),"onImageViewClick() + color: "+(i+1),Toast.LENGTH_SHORT).show();
                            mListener.onColorPicked(i+1);
                            ChangeColorDialog.this.dismiss();
                        }
                    }
                }
            });
        }
        builder.setView(rootView);
        return builder.create();
    }

    public interface ColorPickedListener{
        void onColorPicked(int pickedColor);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the ColorPickedListener so we can send events to the host
            mListener = (ColorPickedListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement ColorPickedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener=null;
    }
}
