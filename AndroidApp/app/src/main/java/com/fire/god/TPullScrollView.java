package com.fire.god;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Scroller;


/**
 * 服务页下拉View
 *
 * @author wdp
 */
public class TPullScrollView extends LinearLayout {

    private ViewGroup headerView;
    private ScrollView contentView;
    private Scroller scroller;
    private Context context;
    private int touchSlop;
    private int startY;
    private int startX;
    private int firstheaderViewHeight = 0;
    private int headerViewHeight = 0;
    private int headerViewHeight2 = 0;
    private int refreshHeight = 0;
    private int contentHeight = 0;
    private int contentHeight1 = 0;
    private PullState state = PullState.HIDDEN;
    private double damping = 0.5D;//给点阻力
    private OnRefreshListener onRefreshListener;
    /**
     * 是否是主动触发的刷新
     */
    private boolean isAutoRefresh;
    private float moreStartY = 0;
    private ImageView imageView;
    private LinearLayout contentlayout;

    public interface OnRefreshListener {
        /**
         * 正在刷新状态
         */
        void onRefreshing();
    }

    public TPullScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public TPullScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TPullScrollView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        ViewConfiguration configuration = ViewConfiguration.get(getContext());
        touchSlop = configuration.getScaledTouchSlop();
        scroller = new Scroller(context, new DecelerateInterpolator());

    }

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    /**
     * 恢复初始位置
     */
    public void hiddenView() {
        switchState(PullState.HIDDEN);
    }

    public void finishRefresh() {
        if (state != PullState.REFRESH) {
            return;
        }
        if (isAutoRefresh) {
            hiddenView();
            return;
        }
        switchState(PullState.SHOWVIEW);
    }

    public void autoRefresh() {
        isAutoRefresh = true;
        switchState(PullState.REFRESH);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getTopPosition();
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 2) {
            throw new RuntimeException("子View只能有两个");
        }
        headerView = (ViewGroup) getChildAt(0);
        contentView = (ScrollView) getChildAt(1);

      //  contentlayout = headerView.findViewById(R.id.head_content_view);
        //imageView = headerView.findViewById(R.id.head_background_view);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (state == PullState.REFRESH) {
            return true;
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = (int) ev.getY();
                startX = (int) ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) ev.getY();
                int delayY = moveY - startY;
                if (getScrollY() == 0 && delayY < touchSlop) {
                    return false;
                }
                if (getTopPosition() && Math.abs(delayY) > touchSlop) {
                    ev.setAction(MotionEvent.ACTION_DOWN);
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (state == PullState.REFRESH) {
                    return true;
                }
                int currentX = (int) event.getX();
                int currentY = (int) event.getY();
                int deltyX = currentX - startX;
                int deltyY = currentY - startY;


                //当前的纵坐标减去 开始的纵坐标 如果是向下 那就是正数  下上就是负数
                int delayY = (int) (event.getY() - startY);

                Log.d("xxx5", delayY + "--------->" + getScrollY() + "-------->stlp->" + touchSlop);
                //如果展示真实内容的view 到顶或者被下拉了 同时 布局内容view 下拉了  这是必然
                if (getTopPosition() && getScrollY() <= 0) {
                    //   if(event.getRawY()-moreStartY<headerViewHeight){
                    //去负是因为向下滑是内容的偏移量是负的 坐标的偏移量是正的
                    int move = (int) (-delayY * damping);
                    Log.d("xxx5", "move------>" + move + "-----getScrollY---->" + getScrollY() + "--->" + -headerViewHeight);
                    if (getScrollY() >= -headerViewHeight) {
                        pullMove((int) (-delayY * damping));
                    }

                    int dy = Math.abs(deltyY);
                    if (delayY > 0 && getHeadPosition()) {
                        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
                        layoutParams.height = layoutParams.height + dy / 2;
                        headerViewHeight2 = layoutParams.height;
                        Log.d("xxx5", "------------------>" + headerViewHeight2);
                        imageView.setLayoutParams(layoutParams);
                        ViewGroup.LayoutParams layoutParams2 = contentlayout.getLayoutParams();
                        MarginLayoutParams layoutParams1 = (MarginLayoutParams) contentlayout.getLayoutParams();
                        if (contentHeight1 + layoutParams1.topMargin + dy / 2 <= headerViewHeight2) {
                            layoutParams1.topMargin = layoutParams1.topMargin + dy / 2;
                            Log.d("xxx5", "下拉下拉------111111111111111----" + contentHeight1);
                        } else {
                            layoutParams1.topMargin = headerViewHeight2 - contentHeight1;
                            Log.d("xxx5", "下拉下拉------22222222222222---" + contentHeight1);
                        }
                        contentlayout.setLayoutParams(layoutParams1);
                        Log.d("xxx5", "下拉下拉------总高度" + layoutParams.height + "head高度---------》" + layoutParams2.height + "------->top偏移" + layoutParams1.topMargin);

                    } else {

                        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
                        int height = layoutParams.height - dy / 2;
                        if (headerViewHeight2 <= height) {
                            height = headerViewHeight;
                        }
                        layoutParams.height = height;
                        headerViewHeight2 = layoutParams.height;
                        imageView.setLayoutParams(layoutParams);
                        MarginLayoutParams layoutParams1 = (MarginLayoutParams) contentlayout.getLayoutParams();
                        int topMargin = layoutParams1.topMargin - dy / 2;
                        if (topMargin <= 0) {
                            topMargin = 0;
                        }
                        layoutParams1.topMargin = topMargin;
                        contentlayout.setLayoutParams(layoutParams1);
                        Log.d("xxx5", "上拉上拉-----总高度" + layoutParams.height + "------------>head高度--->" + layoutParams1.height + "------->top偏移" + layoutParams1.topMargin);


                    }

                    // TODO: 2020/3/26  判断headview 是否完全展示   缩放背景  滚动headview
                    // }

                } else {

                }
                startY = currentY;
                startX = currentX;
                startY = (int) event.getY();
                return true;
            case MotionEvent.ACTION_UP:
                int scrollY = getScrollY();
                int absScrollY = Math.abs(scrollY);
                if (scrollY >= 0) {
                    return false;
                }

                if (state == PullState.SHOWVIEW) {
                    Log.d("xxx5", "state---->111");
                    if (absScrollY >= contentHeight + refreshHeight * 2 / 3) {
                        isAutoRefresh = false;
                        switchState(PullState.REFRESH);
                    } else if (absScrollY >= contentHeight * 9 / 10 && absScrollY < contentHeight + refreshHeight * 2 / 3) {
                        Log.d("xxx5", "state---->2222");
                        switchState(PullState.SHOWVIEW);
                    } else if (absScrollY > 0 && absScrollY < contentHeight * 9 / 10) {
                        Log.d("xxx5", "state---->33333");
                        switchState(PullState.HIDDEN);
                    }
                } else if (state == PullState.HIDDEN) {
                    if (absScrollY <= contentHeight / 2) {
                        switchState(PullState.HIDDEN);
                        Log.d("xxx5", "state---->HIDDEN1111");
                    } else if (absScrollY > contentHeight / 2) {
                        switchState(PullState.SHOWVIEW);
                        Log.d("xxx5", "state---->HIDDEN222");
                    }
                }
                moreStartY = 0;
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        headerViewHeight = headerView.getMeasuredHeight();
        contentHeight = contentlayout.getMeasuredHeight();
        if (firstheaderViewHeight == 0) {
            firstheaderViewHeight = headerViewHeight;
        }
        //headerView中refreshView和contentView的比例为2：3
        refreshHeight = firstheaderViewHeight * 2 / 5;
        contentHeight = firstheaderViewHeight * 3 / 5;
        headerView.layout(l, -headerViewHeight, r, t);
        contentView.layout(l, t, r, b);
        contentView.setPadding(0, 0, 0, DeviceUtil.dp2px(80));
        contentView.setClipChildren(false);
        contentView.setClipToPadding(false);
    }

    private void switchState(PullState pullState) {
        if (pullState == PullState.SHOWVIEW) {
            state = PullState.SHOWVIEW;
            scrollToLocation(-getScrollY() - contentHeight);
        } else if (pullState == PullState.REFRESH) {
            state = PullState.REFRESH;
            scrollToLocation(-getScrollY() - headerViewHeight);
            if (onRefreshListener != null) {
                onRefreshListener.onRefreshing();
            }
        } else {
            state = PullState.HIDDEN;
            scrollToLocation(-getScrollY());
        }

        postInvalidate();
    }

    private void scrollToLocation(int dy) {
        scroller.startScroll(0, getScrollY(), 0, dy, 250);
        postInvalidate();
    }

    /**
     * 滑动
     *
     * @param delay
     */
    private void pullMove(int delay) {
        if (getScrollY() <= 0 && (getScrollY() + delay) <= 0) {
            scrollBy(0, delay);
            Log.d("xxx2", "滑动的---->" + delay);
        } else {
            scrollTo(0, 0);
            Log.d("xxx2", "滑动的---->" + 0);
        }
    }

    private void pullMove(View view, int delay) {
        if (getScrollY() <= 0 && (getScrollY() + delay) <= 0) {
            view.scrollBy(0, delay);
            Log.d("xxx2", "滑动的---->" + delay);
        } else {
            view.scrollTo(0, 0);
            Log.d("xxx2", "滑动的---->" + 0);
        }
    }

    private boolean getTopPosition() {
        if (contentView.getScrollY() <= 0) {
            return true;
        }
        return false;
    }

    private boolean getHeadPosition() {
        if (headerView.getScrollY() <= 0) {
            return true;
        }
        return false;
    }


    enum PullState {
        /**
         * 全部隐藏状态
         */
        HIDDEN,
        /**
         * 显示正文view状态
         */
        SHOWVIEW,
        /**
         * 刷新状态
         */
        REFRESH
    }
}