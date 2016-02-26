package phamsang.com.wftodo;

import android.view.View;

/**
 * Created by Quang Quang on 2/25/2016.
 */
public interface ClickListener
{
    public void onClickViewHolder(View v, int position);
    public void onLongClickViewHolder(View v,int position);
}