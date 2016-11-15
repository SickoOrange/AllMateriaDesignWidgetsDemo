package com.example.coordinatorlayoutdemo;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Orange on 2016/11/9.
 */

public class MyView extends View {
    private  int mHeigt;
    private  int mWidth;
    private  int screenWidth;
    private  int screenHeight;

    private  int lastX;
    private  int lastY;

    public MyView(Context context) {
        super(context);
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        screenWidth=metrics.widthPixels;
        screenHeight=metrics.heightPixels;
        System.out.println("screen metrics:"+screenHeight+" "+screenWidth);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mWidth=getMeasuredWidth();
        mHeigt=getMeasuredHeight();
        System.out.println("m metrics:"+mHeigt+" "+mWidth);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int x= (int) event.getRawX();
        int y= (int) event.getRawY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                CoordinatorLayout.LayoutParams layoutParams= (CoordinatorLayout.LayoutParams) getLayoutParams();

                layoutParams.leftMargin = layoutParams.leftMargin + x - lastX;
                layoutParams.topMargin=layoutParams.topMargin+y-lastY;
                setLayoutParams(layoutParams);
                requestLayout();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        lastX=x;
        lastY=y;
        return true;
    }
}
