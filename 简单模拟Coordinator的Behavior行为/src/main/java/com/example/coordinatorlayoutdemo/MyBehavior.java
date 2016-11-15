package com.example.coordinatorlayoutdemo;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

/**
 * Created by Orange on 2016/11/9.
 */

public class MyBehavior extends CoordinatorLayout.Behavior<Button> {

    private int widthPixels;

    public MyBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        widthPixels = context.getResources().getDisplayMetrics().widthPixels;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, Button child, View dependency) {
        return dependency instanceof MyView;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, Button child, View dependency) {
        int top = dependency.getTop();
        int left = dependency.getLeft();
        int x = widthPixels - left - child.getWidth();
        int y = top;
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) child
                .getLayoutParams();
        layoutParams.leftMargin = x;
        layoutParams.topMargin = y;
        child.setLayoutParams(layoutParams);
        child.requestLayout();
        return true;
    }
}
