package phamsang.com.wftodo;

/**
 * Created by Quang Quang on 2/26/2016.
 */
public class TodoListObject {
    private int mIdList;
    private int mColor;
    private String mTitle;
    private long mTime;

    public int getIdList() {
        return mIdList;
    }

    public TodoListObject(int idList,  String title, int color, long time) {
        mIdList = idList;
        mColor = color;
        mTitle = title;
        mTime = time;
    }

    public void setIdList(int idList) {
        mIdList = idList;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        mColor = color;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        mTime = time;
    }
}
