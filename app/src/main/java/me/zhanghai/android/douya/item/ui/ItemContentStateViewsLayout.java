/*
 * Copyright (c) 2018 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.android.douya.item.ui;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import butterknife.BindDimen;
import butterknife.ButterKnife;
import me.zhanghai.android.douya.R;
import me.zhanghai.android.douya.util.ViewUtils;

public class ItemContentStateViewsLayout extends FrameLayout {

    @BindDimen(R.dimen.item_content_padding_top_max)
    int mPaddingTopMax;

    private float mBackdropRatio;

    public ItemContentStateViewsLayout(@NonNull Context context) {
        super(context);

        init();
    }

    public ItemContentStateViewsLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public ItemContentStateViewsLayout(@NonNull Context context, @Nullable AttributeSet attrs,
                                       int defStyle) {
        super(context, attrs, defStyle);

        init();
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    public ItemContentStateViewsLayout(@NonNull Context context, @Nullable AttributeSet attrs,
                                       int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mBackdropRatio > 0) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int paddingTop = Math.round(width / mBackdropRatio);
            if (mPaddingTopMax > 0) {
                paddingTop = Math.min(paddingTop, mPaddingTopMax);
            }
            // HACK: Fix off-by-one-pixel visual glitch.
            --paddingTop;
            setPadding(getPaddingLeft(), paddingTop, getPaddingRight(), getPaddingBottom());
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
