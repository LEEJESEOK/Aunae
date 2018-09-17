package kr.co.company.aunae;

import android.graphics.Bitmap;

public class ListViewItem_place {
    private String mTitle;
    private Bitmap mBackground;
    private int mID;

    public ListViewItem_place(String title, Bitmap background, int ID) {
        mTitle = title;
        mBackground = background;
        mID = ID;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public Bitmap getmBackground() {
        return mBackground;
    }

    public void setmBackground(Bitmap mBackground) {
        this.mBackground = mBackground;
    }

    public int getmID() {
        return mID;
    }

    public void setmID(int mID) {
        this.mID = mID;
    }
}
