package phamsang.com.wftodo.data;

import android.provider.BaseColumns;

/**
 * Created by Quang Quang on 2/24/2016.
 */
public final class Contract {
    public Contract(){

    }

    public static final class TodoItemEntry implements BaseColumns{
        public static final String TABLE_NAME = "todo_item";
        public static final String COLLUMN_CONTENT = "content";
        public static final String COLLUMN_IS_DONE = "is_done";
        public static final String COLUMN_TIME = "time";
        public static final String COLLUMN_LIST_ID = "list_id";
    }
    public static final class TodoListEntry implements BaseColumns{
        public static final String TABLE_NAME = "todolist";
        public static final String COLLUMN_TITLE = "title";
        public static final String COLLUMN_COLOR = "color";
        public static final String COLLUMN_TIME = "last_time";
    }
}
