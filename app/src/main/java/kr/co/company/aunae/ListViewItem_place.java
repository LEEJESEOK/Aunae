package kr.co.company.aunae;

import android.graphics.Bitmap;

public class ListViewItem_place {
    String mTitle;
    Bitmap mBackground;

    public ListViewItem_place(String title, Bitmap background) {
        mTitle = title;
        mBackground = background;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public Bitmap getBackground() {
        return mBackground;
    }

    public void setBackground(Bitmap background) {
        this.mBackground = background;
    }
}
