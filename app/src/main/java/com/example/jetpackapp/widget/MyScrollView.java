package com.example.jetpackapp.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView {
    private Boolean intercept;

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        Log.e("TAG", "parent  onInterceptTouchEvent  " + intercept);

        return intercept;
    }

    private int moveLastX = 0;
    private int moveLastY = 0;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int currentX = (int) ev.getX();
        int currentY = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercept = false;
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = currentX - moveLastX;
                int dy = currentY - moveLastY;

                if (Math.abs(dx) > Math.abs(dy)) {
                    intercept = true;
                } else {
                    intercept = false;
                }

                break;
        }
        moveLastX = currentX;
        moveLastY = currentY;
        Log.e("TAG", "parent  dispatchTouchEvent  " + intercept);
        return super.dispatchTouchEvent(ev);
    }

}
