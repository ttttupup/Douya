/*
 * Copyright (c) 2017 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.android.douya.item.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import butterknife.BindDimen;
import butterknife.ButterKnife;
import me.zhanghai.android.douya.R;

public class ItemContentRecyclerView extends RecyclerView {

    @BindDimen(R.dimen.item_content_padding_top_max)
    int mPaddingTopMax;

    private float mBackdropRatio;
    private int mPaddingTopNegativeMargin;
    private View mBackdropWrapper;

    public ItemContentRecyclerView(Context context) {
        super(context);

        init();
    }

    public ItemContentRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public ItemContentRecyclerView(Context context, @Nullable AttributeSet attrs,
                                   int defStyle) {
        super(context, attrs, defStyle);

        init();
    }

    private void init() {
        ButterKnife.bind(this);
    }

    public float getBackdropRatio() {
        return mBackdropRatio;
    }

    public void setBackdropRatio(float ratio) {
        if (mBackdropRatio != ratio) {
            mBackdropRatio = ratio;
            requestLayout();
            invalidate();
        }
    }

    public void setBackdropRatio(float width, float height) {
        setBackdropRatio(width / height);
    }

    public int getPaddingTopNegativeMargin() {
        return mPaddingTopNegativeMargin;
    }

    public void setPaddingTopNegativeMargin(int paddingTopNegativeMargin) {
        if (mPaddingTopNegativeMargin != paddingTopNegativeMargin) {
            mPaddingTopNegativeMargin = paddingTopNegativeMargin;
            requestLayout();
            invalidate();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mBackdropRatio > 0) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int paddingTop = Math.round(width / mBackdropRatio);
            if (mPaddingTopMax > 0) {
                paddingTop = Math.min(paddingTop, mPaddingTopMax);
            }
            paddingTop += mPaddingTopNegativeMargin;
            // HACK: Fix off-by-one-pixel visual glitch.
            --paddingTop;
            setPadding(getPaddingLeft(), paddingTop, getPaddingRight(), getPaddingBottom());
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public View getBackdropWrapper() {
        return mBackdropWrapper;
    }

    public void setBackdropWrapper(View backdropWrapper) {
        mBackdropWrapper = backdropWrapper;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        if (getScrollState() != SCROLL_STATE_DRAGGING) {
            mBackdropWrapper.dispatchTouchEvent(event);
        } else {
            int oldAction = event.getAction();
            event.setAction(MotionEvent.ACTION_CANCEL | (oldAction & ~MotionEvent.ACTION_MASK));
            mBackdropWrapper.dispatchTouchEvent(event);
            event.setAction(oldAction);
        }
        return true;
    }
}
