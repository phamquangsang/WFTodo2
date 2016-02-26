package phamsang.com.wftodo;

/**
 * Created by Quang Quang on 2/24/2016.
 */
public class TodoItem {
    private int mId;
    private String mContent;
    private int mIsDone;
    private long mTime;
    private int mIdList;

    public TodoItem(int mId, String mContent, int mIsDone, long mTime, int mIdList) {
        this.mId = mId;
        this.mContent = mContent;
        this.mIsDone = mIsDone;
        this.mTime = mTime;
        this.mIdList = mIdList;
    }

    public int getmId() {
        return mId;
    }

    public String getmContent() {
        return mContent;
    }

    public int getmIsDone() {
        return mIsDone;
    }

    public long getmTime() {
        return mTime;
    }

    public int getmIdList() {
        return mIdList;
    }



}
