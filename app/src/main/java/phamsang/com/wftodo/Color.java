package phamsang.com.wftodo;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;

import java.util.Random;

/**
 * Created by Quang Quang on 2/27/2016.
 */
public class Color {
    public static int getCorlor(Context context, int color){
        int result =0;
        switch (color){
            case 1:
                result = ContextCompat.getColor(context, R.color.amber);
                break;
            case 2:
                result = ContextCompat.getColor(context, R.color.blue);
                break;
            case 3:
                result = ContextCompat.getColor(context, R.color.grey);
                break;
            case 4:
                result = ContextCompat.getColor(context, R.color.light_green);
                break;
            case 5:
                result = ContextCompat.getColor(context, R.color.red);
                break;
            case 6:
                result = ContextCompat.getColor(context, R.color.teal);
                break;

        }
        return result;
    }

    public static int getRandomColor(){
        Random random = new Random();
        return random.nextInt(6)+1;
    }
}
