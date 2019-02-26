package com.gameex.dw.hospital.titleBar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gameex.dw.hospital.R;

public class TitleBarView extends RelativeLayout implements View.OnClickListener {
    private LinearLayout leftLayout,rightLayout;
    private TextView titleTV;
    private ImageView rightIV;
    private OnViewClick mClick;


    public TitleBarView(Context context) {
        this(context, null);
    }

    public TitleBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.title_bar_layout, this);
        initView();
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.titleBarView, defStyleAttr, 0);
        int count = array.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.titleBarView_centerTextColor:
                    titleTV.setTextColor(array.getColor(attr, Color.WHITE));
                    break;
                case R.styleable.titleBarView_centerText:
                    titleTV.setText(array.getString(attr));
                    break;
                default:
                    break;
            }
        }
        array.recycle();
    }

    private void initView() {
        leftLayout=findViewById(R.id.layout_left);
        leftLayout.setOnClickListener(this);
        titleTV = findViewById(R.id.tv_title);
        rightIV = findViewById(R.id.iv_right);
        rightLayout = findViewById(R.id.layout_right);
        rightLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_left:
                mClick.leftClick();
                break;
            case R.id.layout_right:
                mClick.rightClick();
                break;
            default:
                break;
        }
    }

    public void setOnViewClick(OnViewClick click) {
        this.mClick = click;
    }

    //设置标题
    public void setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            titleTV.setText(title);
        }
    }

    //设置标题大小
    public void setTitleSize(int size) {
        if (titleTV != null) {
            titleTV.setTextSize(size);
        }
    }

    //设置左图标可见否
    public void setLeftIVVisible(int visible) {
        leftLayout.setVisibility(visible);
    }

    //设置右图标可见否
    public void setRightIVVisible(int visible) {
        rightIV.setVisibility(visible);
    }

}
