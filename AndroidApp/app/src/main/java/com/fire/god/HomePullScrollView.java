package com.fire.god;

import android.animation.ValueAnimator;
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


public class HomePullScrollView extends LinearLayout {

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

    public HomePullScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public HomePullScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HomePullScrollView(Context context) {
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

       // contentlayout = headerView.findViewById(R.id.head_content_view);
        //imageView = headerView.findViewById(R.id.head_background_view);
        // imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
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

                //如果展示真实内容的view 到顶或者被下拉了 同时 布局内容view 下拉了  这是必然
                if (getTopPosition() && getScrollY() <= 0) {
                    //去负是因为向下滑是内容的偏移量是负的 坐标的偏移量是正的
                    if (getScrollY() >= -headerViewHeight) {
                        pullMove((int) (-delayY * damping));
                    }
                    int dy = Math.abs(deltyY);
                    if (delayY > 0 && getHeadPosition()) {
                        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
                        layoutParams.height = layoutParams.height + dy / 2;
                        headerViewHeight2 = layoutParams.height;
                        imageView.setLayoutParams(layoutParams);
                        MarginLayoutParams layoutParams1 = (MarginLayoutParams) contentlayout.getLayoutParams();
                        if (firstheaderViewHeight + layoutParams1.topMargin + dy / 2 <= headerViewHeight2) {
                            layoutParams1.topMargin = layoutParams1.topMargin + dy / 2;
                        } else {
                            layoutParams1.topMargin = headerViewHeight2 - firstheaderViewHeight;
                        }
                        contentlayout.setLayoutParams(layoutParams1);
                    } else {
                        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
                        int height = layoutParams.height - dy / 2;
                        if (height <= firstheaderViewHeight) {
                            height = firstheaderViewHeight;
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
                    }

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
                    //内容偏移的距离 差不多到完全展示loading 就刷新
                    if (absScrollY >= contentHeight + refreshHeight / 3 * 2) {
                        isAutoRefresh = false;
                        switchState(PullState.REFRESH);
                        //也就是下拉内容偏移的距离大于headview 中内容view 同时 没有到触发刷新的状态
                    } else if (absScrollY >= contentHeight && absScrollY < contentHeight + refreshHeight * 2 / 3) {
                        switchState(PullState.SHOWVIEW);
                        //也就是上拉 内容偏移的距离小于 headview 中 内容view 的 10分之9 就隐藏
                    } else if (absScrollY > 0 && absScrollY < contentHeight * 9 / 10) {
                        switchState(PullState.HIDDEN);
                    }
                } else if (state == PullState.HIDDEN) {
                    if (absScrollY <= contentHeight / 2) {
                        Log.d("xxx5","----->hideen");
                        //滑动的距离小于head中内容view 的一半就 还是隐藏
                        switchState(PullState.HIDDEN);
                    }else if (absScrollY >= contentHeight + refreshHeight / 3 * 2) {
                        isAutoRefresh = false;
                        Log.d("xxx5","----->刷新");
                        switchState(PullState.REFRESH);
                        //也就是下拉内容偏移的距离大于headview 中内容view 同时 没有到触发刷新的状态
                    } else if (absScrollY > contentHeight / 2) {
                        Log.d("xxx5","----->showview");
                        //滑动的距离大于head中内容view 的一半就 展示内容view
                        switchState(PullState.SHOWVIEW);
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
            //切换显示head 内容view 向下移动个内容view 的距离
            scrollToLocation(-getScrollY() - contentHeight);
        } else if (pullState == PullState.REFRESH) {
            state = PullState.REFRESH;
            //切换显示head loadingview 向下移动个loadingview 的距离
            startAnimator();
            scrollToLocation(-getScrollY() - firstheaderViewHeight);
            if (onRefreshListener != null) {
                onRefreshListener.onRefreshing();
            }
        } else {
            state = PullState.HIDDEN;
            //切换隐藏 不必多说直接还原偏移距离
            scrollToLocation(-getScrollY());
            startAnimator();
        }

        postInvalidate();
    }


    private void startAnimator() {
        final ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        final MarginLayoutParams params = (MarginLayoutParams) contentlayout.getLayoutParams();
        ValueAnimator anim = ValueAnimator.ofInt(layoutParams.height, firstheaderViewHeight);
        anim.setDuration(250);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                layoutParams.height = (int) animation.getAnimatedValue();
                imageView.setLayoutParams(layoutParams);
            }
        });
        ValueAnimator anim1 = ValueAnimator.ofInt(params.topMargin, 0);
        anim1.setDuration(250);
        anim1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                params.topMargin = (int) animation.getAnimatedValue();
                contentlayout.setLayoutParams(params);
            }
        });
        anim.start();
        anim1.start();
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
        } else {
            scrollTo(0, 0);
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