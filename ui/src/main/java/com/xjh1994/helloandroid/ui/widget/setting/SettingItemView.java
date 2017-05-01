package com.xjh1994.helloandroid.ui.widget.setting;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xjh1994.helloandroid.ui.R;
import com.xjh1994.helloandroid.ui.utils.DisplayUtils;


/**
 * Created by xjh1994 on 16/11/23.
 * 设置子项
 */

public class SettingItemView extends LinearLayout {

    private ImageView ivLeftIcon;
    private TextView tvLeftTitle;
    private TextView tvLeftDesc;
    private ImageView ivRightIcon;
    private TextView tvRightTitle;
    private TextView tvRightDesc;
    private ImageView ivArrowForward;
    private View topLine;
    private View bottomLine;

    private int mLeftIconResId;
    private int mLeftIconWidth;
    private int mLeftIconHeight;
    private String mLeftTitle;
    private float mLeftTitleSize;
    private int mLeftTitleColor;
    private String mLeftDesc;
    private float mLeftDescSize;
    private int mLeftDescColor;
    private int mRightIconResId;
    private int mRightIconWidth;
    private int mRightIconHeight;
    private String mRightTitle;
    private float mRightTitleSize;
    private int mRightTitleColor;
    private String mRightDesc;
    private float mRightDescSize;
    private int mRightDescColor;
    private boolean mArrowEnabled;
    private int mArrowIconResId;
    private int mBackgroundResId;
    private boolean mTopLineEnabled;
    private boolean mBottomLineEnabled;

    public SettingItemView(Context context) {
        this(context, null);
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.tx_setting_item_view, this);
        initView();

        obtainAttrs(context, attrs);

        if (!TextUtils.isEmpty(mLeftTitle)) {
            tvLeftTitle.setText(mLeftTitle);
            tvLeftTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, mLeftTitleSize);
            if (mLeftTitleColor != 0) {
                tvLeftTitle.setTextColor(mLeftTitleColor);
            }
            tvLeftTitle.setVisibility(VISIBLE);
        }
        if (mLeftIconResId != 0) {
            ivLeftIcon.setImageResource(mLeftIconResId);
            ivLeftIcon.setVisibility(VISIBLE);
        }

        if (!TextUtils.isEmpty(mLeftDesc)) {
            tvLeftDesc.setText(mLeftDesc);
            tvLeftDesc.setTextSize(TypedValue.COMPLEX_UNIT_PX, mLeftDescSize);
            if (mLeftDescColor != 0) {
                tvLeftDesc.setTextColor(mLeftDescColor);
            }
            
            tvLeftDesc.setVisibility(VISIBLE);
        }
        if (mRightIconResId != 0) {
            ivRightIcon.setImageResource(mRightIconResId);
            ivRightIcon.setVisibility(VISIBLE);
        }
        if (!TextUtils.isEmpty(mRightTitle)) {
            tvRightTitle.setText(mRightTitle);
            tvRightTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, mRightTitleSize);
            if (mRightTitleColor != 0) {
                tvRightTitle.setTextColor(mRightTitleColor);
            }
            tvRightTitle.setVisibility(VISIBLE);
        }
        if (!TextUtils.isEmpty(mRightDesc)) {
            tvRightDesc.setText(mRightDesc);
            tvRightDesc.setTextSize(TypedValue.COMPLEX_UNIT_PX, mRightDescSize);
            if (mRightDescColor != 0) {
                tvRightDesc.setTextColor(mRightDescColor);
            }
            tvRightDesc.setVisibility(VISIBLE);
        }
        if (mArrowIconResId != 0) {
            ivArrowForward.setImageResource(mArrowIconResId);
            ivArrowForward.setVisibility(VISIBLE);
        }
        ivArrowForward.setVisibility(mArrowEnabled ? VISIBLE : GONE);
        topLine.setVisibility(mTopLineEnabled ? VISIBLE : GONE);
        bottomLine.setVisibility(mBottomLineEnabled ? VISIBLE : GONE);
    }

    private void obtainAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SettingItemView);

        mLeftIconResId = ta.getResourceId(R.styleable.SettingItemView_sivLeftIcon, 0);
        mLeftIconWidth = (int) ta.getDimension(R.styleable.SettingItemView_sivLeftIconWidth, DisplayUtils.dp2px(context, 40));
        mLeftIconHeight = (int) ta.getDimension(R.styleable.SettingItemView_sivLeftIconHeight, DisplayUtils.dp2px(context, 40));
        mLeftTitle = ta.getString(R.styleable.SettingItemView_sivLeftText);
        mLeftDesc = ta.getString(R.styleable.SettingItemView_sivLeftSubText);
        mLeftTitleSize = ta.getDimension(R.styleable.SettingItemView_sivLeftTextSize, DisplayUtils.sp2px(context, 14));
        mLeftTitleColor = ta.getColor(R.styleable.SettingItemView_sivLeftTextColor, 0);
        mLeftDescSize = ta.getDimension(R.styleable.SettingItemView_sivLeftSubTextSize, DisplayUtils.sp2px(context, 12));
        mLeftDescColor = ta.getColor(R.styleable.SettingItemView_sivLeftSubTextColor, 0);

        mRightIconResId = ta.getResourceId(R.styleable.SettingItemView_sivRightIcon, 0);
        mRightIconWidth = (int) ta.getDimension(R.styleable.SettingItemView_sivRightIconWidth, DisplayUtils.dp2px(context, 40));
        mRightIconHeight = (int) ta.getDimension(R.styleable.SettingItemView_sivRightIconHeight, DisplayUtils.dp2px(context, 40));
        mRightTitle = ta.getString(R.styleable.SettingItemView_sivRightText);
        mRightDesc = ta.getString(R.styleable.SettingItemView_sivRightSubText);
        mRightTitleSize = ta.getDimension(R.styleable.SettingItemView_sivRightTextSize, DisplayUtils.sp2px(context, 14));
        mRightTitleColor = ta.getColor(R.styleable.SettingItemView_sivRightTextColor, 0);
        mRightDescSize = ta.getDimension(R.styleable.SettingItemView_sivRightSubTextSize, DisplayUtils.sp2px(context, 12));
        mRightDescColor = ta.getColor(R.styleable.SettingItemView_sivRightSubTextColor, 0);

        mArrowEnabled = ta.getBoolean(R.styleable.SettingItemView_sivArrowEnabled, true);
        mArrowIconResId = ta.getResourceId(R.styleable.SettingItemView_sivArrowIcon, 0);
        mBackgroundResId = ta.getResourceId(R.styleable.SettingItemView_sivBackground, 0);
        mTopLineEnabled = ta.getBoolean(R.styleable.SettingItemView_sivTopLineEnabled, false);
        mBottomLineEnabled = ta.getBoolean(R.styleable.SettingItemView_sivBottomLineEnabled, false);

        ivLeftIcon.setLayoutParams(new RelativeLayout.LayoutParams(mLeftIconWidth, mLeftIconHeight));
        ivRightIcon.setLayoutParams(new RelativeLayout.LayoutParams(mRightIconWidth, mRightIconHeight));

        if (mBackgroundResId == 0) {
            setBg(getResources().getDrawable(R.drawable.tx_selector_bg_setting_item));
        } else {
            setBg(getResources().getDrawable(mBackgroundResId));
        }

        ta.recycle();
    }

    private void initView() {
        setOrientation(VERTICAL);

        topLine = findViewById(R.id.viewLineTop);
        bottomLine = findViewById(R.id.viewLineBottom);
        ivLeftIcon = (ImageView) findViewById(R.id.ivLeftIcon);
        tvLeftTitle = (TextView) findViewById(R.id.tvLeftTitle);
        tvLeftDesc = (TextView) findViewById(R.id.tvLeftDesc);
        ivRightIcon = (ImageView) findViewById(R.id.ivRightIcon);
        tvRightTitle = (TextView) findViewById(R.id.tvRightTitle);
        tvRightDesc = (TextView) findViewById(R.id.tvRightDesc);
        ivArrowForward = (ImageView) findViewById(R.id.ivArrowForward);

        setClickable(true);
    }

    private void setBg(Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(drawable);
        } else {
            setBackgroundDrawable(drawable);
        }
    }

    public ImageView getIvLeftIcon() {
        return ivLeftIcon;
    }

    public TextView getTvLeftTitle() {
        return tvLeftTitle;
    }

    public TextView getTvLeftDesc() {
        return tvLeftDesc;
    }

    public ImageView getIvRightIcon() {
        return ivRightIcon;
    }

    public TextView getTvRightTitle() {
        return tvRightTitle;
    }

    public TextView getTvRightDesc() {
        return tvRightDesc;
    }

    public ImageView getIvArrowForward() {
        return ivArrowForward;
    }

    public void setArrowEnabled(boolean isEnabled) {
        this.mArrowEnabled = isEnabled;
        ivArrowForward.setVisibility(mArrowEnabled ? VISIBLE : GONE);
    }
}
