package com.example.viewdraghelper;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Orange on 2016/11/21.
 */

public class DragLayout_Demo2 extends LinearLayout {

    private ViewDragHelper dragHelper;
    private TextView mDragView;
    private TextView mAutoBackView;
    private TextView mEdgeTrackerView;
    private Point autoPoint = new Point();

    public DragLayout_Demo2(Context context) {
        super(context);
    }

    public DragLayout_Demo2(Context context, AttributeSet attrs) {
        super(context, attrs);
        dragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return child == mDragView || child == mAutoBackView;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                final int leftBound = getPaddingLeft();
                final int rightBound = getWidth() - child.getWidth() - leftBound;

                final int newLeft = Math.min(Math.max(left, leftBound), rightBound);

                return newLeft;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {


                final int topBound = getPaddingTop();
                final int bottomBound = getHeight() - child.getHeight() - topBound;
                final int newTop = Math.min(Math.max(top, topBound), bottomBound);
                return newTop;
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                if (releasedChild == mAutoBackView) {
                    dragHelper.settleCapturedViewAt(autoPoint.x, autoPoint.y);
                    invalidate();
                }
            }

            //在边界拖动时回调
            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                dragHelper.captureChildView(mEdgeTrackerView, pointerId);
            }

            @Override
            public int getViewHorizontalDragRange(View child)
            {
                return getMeasuredWidth()-child.getMeasuredWidth();
            }

            @Override
            public int getViewVerticalDragRange(View child)
            {
                return getMeasuredHeight()-child.getMeasuredHeight();
            }
        });

        dragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }

    public DragLayout_Demo2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        System.out.println(dragHelper.shouldInterceptTouchEvent(ev));
        return dragHelper.shouldInterceptTouchEvent(ev);
       // return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        System.out.println("onTouchEvent");
        dragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        System.out.println("onLayout");
        autoPoint.x = mAutoBackView.getLeft();
        autoPoint.y = mAutoBackView.getTop();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        System.out.println("onFinishInflate");
        mDragView = (TextView) getChildAt(0);
        mAutoBackView = (TextView) getChildAt(1);
        mEdgeTrackerView = (TextView) getChildAt(2);

    }

    @Override
    public void computeScroll()
    {
        if(dragHelper.continueSettling(true))
        {
            invalidate();
        }
    }
}
