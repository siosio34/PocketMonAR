package com.ar.siosi.swmaestrobackendproject;

import android.graphics.drawable.Drawable;

/**
 * Created by siosi on 2016-07-18.
 */
public class FriendListView {

    private Drawable iconDrawable ;
    private String textStr ;

    public void setIcon(Drawable icon) {
        iconDrawable = icon ;
    }
    public void setText(String text) {
        textStr = text ;
    }

    public Drawable getIcon() {
        return this.iconDrawable ;
    }
    public String getText() {
        return this.textStr ;
    }
}

