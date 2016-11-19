package com.example.appdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Orange on 2016/11/18.
 */

public class FlowLayout extends ViewGroup {
    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //第一个-->集合元素：存放的是每一行的高度
    private List<Integer> allHeights = new ArrayList<>();
    //第二个是集合的集合，外层集合元素为：存放的是每一行的childView的集合
    private List<List<View>> allViews = new ArrayList<>();

    //给每一个孩子放置在布局中
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //父布局的宽度
        int width = this.getWidth();
        //设置每一行的行高 与行宽
        int lineWidth = 0;
        int lineHeight = 0;

        List<View> lineList = new ArrayList<>();
        int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {

            View childView = getChildAt(i);
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();

            MarginLayoutParams mp = (MarginLayoutParams) childView.getLayoutParams();
            if (lineWidth+childWidth + mp.leftMargin + mp.rightMargin <= width) {
                lineList.add(childView);
                lineWidth += childWidth + mp.leftMargin + mp.rightMargin;
                lineHeight = Math.max(lineHeight, childHeight + mp.topMargin + mp.bottomMargin);
            } else {
                //换行,则把 "每行的子view的集合" 添加到所有的集合中,把行高加到行高的集合中
                allViews.add(lineList);
                allHeights.add(lineHeight);

                //换行以后需要执行的操作
                //重新new一个行的集合
                lineList = new ArrayList<>();
                //把新的一行的子view加到行的集合中
                lineList.add(childView);

                lineWidth = childWidth + mp.leftMargin + mp.rightMargin;
                lineHeight = childHeight + mp.topMargin + mp.bottomMargin;

            }

            if (i == childCount - 1) {
                allViews.add(lineList);
                allHeights.add(lineHeight);
            }
        }

        //遍历集合元素，调用每一个view的layout方法
        //提供需要累加的两个人变量
        int x=0;
        int y=0;
        //第一层得到的是每一行的数据
        for (int i=0;i<allViews.size();i++){
            List<View> lineViews=allViews.get(i);

            //第二层得到的是每一个子view
            for (int j=0;j<lineViews.size();j++){
                View childView=lineViews.get(j);
                //得到边距
                MarginLayoutParams mp= (MarginLayoutParams) childView.getLayoutParams();
                //到l,t,r,b
                int left=x+mp.leftMargin;
                int top=y+mp.topMargin;
                int right=left+childView.getMeasuredWidth();
                int bottom=top+childView.getMeasuredHeight();

                childView.layout(left,top,right,bottom);

                //重新覆下一个子视图的左上顶点
                x+=childView.getMeasuredWidth()+mp.leftMargin+mp.rightMargin;
            }

            //该层循环出来则表示 换行 了
            //换行后，x要复位为0，y要重新赋值
            x=0;
            y+=allHeights.get(i);
        }

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        //
        //获取当前流式布局的宽与高
        //720*926
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);


        //父布局的宽与高
        int width = 0;
        int height = 0;

        //找出ChildView的个数

        int childCount = getChildCount();

        //每一行的宽与高
        int lineWidth = 0;
        int lineHeight = 0;

        for (int i = 0; i < childCount; i++) {
            //拿到每一个子View
            View childView = getChildAt(i);

            //测量子View的宽与高  只有调用此方法 才可以获取到值
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);

            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();

            //获取子View的边距
            MarginLayoutParams mp = (MarginLayoutParams) getLayoutParams();

            //具体测量计算
            if (lineWidth + childWidth + mp.leftMargin + mp.rightMargin <= widthSize) {
                //此时这个子view宽度没有超过一行，此时这一行的行宽度lineWidth可以增加
                lineWidth += childWidth + mp.leftMargin + mp.rightMargin;
                //得到新的lineWidth 此时确定当前的行高度
                lineHeight = Math.max( childHeight + mp.topMargin + mp.bottomMargin,lineHeight);
            } else {
                //说明此时需要增加一行 那么首先我们要获得这一行的实际宽度是多少
                width = Math.max(width, lineWidth);
                //布局要增加高度
                height += lineHeight;

                lineWidth = childWidth + mp.leftMargin + mp.rightMargin;
                lineHeight = childHeight + mp.topMargin + mp.bottomMargin;

            }
            if (i == childCount - 1) {
                width = Math.max(width, lineWidth);
                height += lineHeight;
            }

        }

        //这样拿到的宽与高就是流式布局的实际宽与高
        setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize : width, heightMode ==
                MeasureSpec.EXACTLY ? heightSize : height);
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    //想要拿到margin的值 必须要重写这个方法

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        MarginLayoutParams mp = new MarginLayoutParams(getContext(), attrs);
        return mp;
    }
}
