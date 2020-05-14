package com.fire.god;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.OverScroller;

/**
 * Description:
 * Email: zhoushengchen@nxin.com
 * Author: 周昇辰
 * Date: 2020/3/25
 **/
public class DragImageView extends LinearLayout {

    private OverScroller mScroller;
    private int mTouchSlop;
    private int mMaximumVelocity;
    private int mMinimumVelocity;
    private ImageView imageView;
    private View view;
    private int downX;
    private int downY;

    public DragImageView(Context context) {
        super(context);
        init(context);
    }

    public DragImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DragImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        // 默认该View垂直排列
        setOrientation(LinearLayout.VERTICAL);
        // 用于配合处理该View的惯性滑动
        mScroller = new OverScroller(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mMaximumVelocity = ViewConfiguration.get(context)
                .getScaledMaximumFlingVelocity();
        mMinimumVelocity = ViewConfiguration.get(context)
                .getScaledMinimumFlingVelocity();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        LayoutParams params = (LayoutParams) getChildAt(1).getLayoutParams();
        // 头部可以全部隐藏，所以内容视图的高度即为该控件的高度
        params.height = getMeasuredHeight();
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        imageView = (ImageView) getChildAt(0);
        // 随着手指滑动，图片不断放大（宽高都大于或者等于ImageView的大小），并居中显示：
        // 根据上边的分析，CENTER_CROP：可以使用均衡的缩放图像（保持图像原始比例），使图片的两个坐标（宽、高）都大于等于 相应的视图坐标（负的内边距）,图像则位于视图的中央
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view = (View) getChildAt(1);
    }


 /*   @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            downX = (int) ev.getX();
            downY = (int) ev.getY();
        }
        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            int currentX = (int) ev.getX();
            int currentY = (int) ev.getY();
            // 确保是垂直滑动
            if (Math.abs(currentY - downY) > Math.abs(currentX - downX)) {
                View childView = listView.getChildAt(listView
                        .getFirstVisiblePosition());
                // 有两种情况需要拦截：
                // 1 图片没有完全隐藏
                // 2 图片完全隐藏，但是向下滑动，并且ListView滑动到顶部
                if (getScrollY() != imageHeight
                        || (getScrollY() == imageHeight && currentY - downY > 0
                        && childView != null && childView.getTop() == 0)) {
                    initVelocityTrackerIfNotExists();
                    mVelocityTracker.addMovement(ev);
                    return true;
                }
            }

        }
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            recycleVelocityTracker();
        }
        return super.onInterceptTouchEvent(ev);
    }*/
}
