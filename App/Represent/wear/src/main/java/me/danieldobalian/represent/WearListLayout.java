package me.danieldobalian.represent;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.wearable.view.CircledImageView;
import android.support.wearable.view.WearableListView;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by danieldobalian on 3/3/16.
 */
public class WearListLayout extends LinearLayout implements WearableListView.OnCenterProximityListener {
    private CircledImageView mCircle;
    private TextView mName;
    private final float mFadedTextAlpha;
    private final int mUnselectedCircleColor, mSelectedCircleColor, mPressedCircleColor;
    private boolean mIsInCenter;

    private float mBigCircleRadius;
    private float mSmallCircleRadius;
    private ObjectAnimator mScalingDownAnimator;
    private ObjectAnimator mScalingUpAnimator;

    public WearListLayout(Context context) {
        this(context, null);
    }

    public WearListLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public WearListLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mFadedTextAlpha = 40 / 100f;
        mUnselectedCircleColor = Color.parseColor("#c1c1c1");
        mSelectedCircleColor = Color.parseColor("#3185ff");
        mPressedCircleColor = Color.parseColor("#2955c5");
        mSmallCircleRadius = getResources().getDimensionPixelSize(R.dimen.small_circle_radius);
        mBigCircleRadius = getResources().getDimensionPixelSize(R.dimen.big_circle_radius);

        setClipChildren(false);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mCircle = (CircledImageView) findViewById(R.id.circle);
        mName = (TextView) findViewById(R.id.name);

        mScalingUpAnimator = ObjectAnimator.ofFloat(mCircle, "circleRadius", mBigCircleRadius);
        mScalingUpAnimator.setDuration(150L);

        mScalingDownAnimator = ObjectAnimator.ofFloat(mCircle, "circleRadius", mSmallCircleRadius);
        mScalingDownAnimator.setDuration(150L);
    }

    @Override
    public void onCenterPosition(boolean animate) {
        if(animate) {
            mScalingDownAnimator.cancel();
            if (!mScalingUpAnimator.isRunning() && mCircle.getCircleRadius() != mBigCircleRadius) {
                mScalingUpAnimator.start();
            }
        } else {
            mCircle.setCircleRadius(mBigCircleRadius);
        }

        mName.setAlpha(1f);
        mCircle.setCircleColor(mSelectedCircleColor);
        mIsInCenter = true;
    }

    @Override
    public void onNonCenterPosition(boolean animate) {
        if(animate) {
            mScalingUpAnimator.cancel();
            if(!mScalingDownAnimator.isRunning() && mCircle.getCircleRadius() != mSmallCircleRadius) {
                mScalingDownAnimator.start();
            }
        } else {
            mCircle.setCircleRadius(mSmallCircleRadius);
        }

        mName.setAlpha(mFadedTextAlpha);
        mCircle.setCircleColor(mUnselectedCircleColor);
        mIsInCenter = false;
    }

    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);
        if(mIsInCenter && pressed) {
            mCircle.setCircleColor(mPressedCircleColor);
        }
        if(mIsInCenter && !pressed) {
            mCircle.setCircleColor(mSelectedCircleColor);
        }
    }

    public void setCircleRadius(float circleRadius)
    {
        mCircle.setCircleRadius(circleRadius);
    }

    public float getCircleRadius() {
        return mCircle.getCircleRadius();
    }
}
