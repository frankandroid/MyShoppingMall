package com.example.frank.myshoppingmall.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.frank.myshoppingmall.R;

/**
 * 创建者     Frank
 * 创建时间   2016/4/18 22:19
 * 描述	      ${TODO}
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class MyToolBar extends Toolbar {

    public static final String TAG = "MyToolBar";

    private LayoutInflater mLayoutInflater;

    private String   mRightButtonText;
    private Drawable mRightIcon;
    private boolean  isShowSearchView;


    private EditText mSearchView;
    private TextView mTextTitle;
    private Button   mRightButton;
    private View     mRootView;


    public MyToolBar(Context context) {
        this(context, null);
    }

    public MyToolBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyToolBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (attrs != null) {
            TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MyToolBar,
                    defStyleAttr, 0);

            int count = typedArray.getIndexCount();

            for (int i = 0; i < count; i++) {

                int attr = typedArray.getIndex(i);

                switch (attr) {
                    case R.styleable.MyToolBar_rightButtonIcon:
                        mRightIcon = typedArray.getDrawable(R.styleable.MyToolBar_rightButtonIcon);
                        break;
                    case R.styleable.MyToolBar_isShowSeacherView:
                        isShowSearchView = typedArray.getBoolean(R.styleable.MyToolBar_isShowSeacherView, false);
                        break;
                    case R.styleable.MyToolBar_rightButtonText:
                        mRightButtonText = typedArray.getString(R.styleable.MyToolBar_rightButtonText);
                        break;
                }
            }


            typedArray.recycle();

        }

        initView();
        setContentInsetsRelative(10, 10);


    }

    private void initView() {

        if(mRootView==null) {
            mLayoutInflater = LayoutInflater.from(getContext());

            mRootView = mLayoutInflater.inflate(R.layout.mytoolbar, null);

            mSearchView = (EditText) mRootView.findViewById(R.id.mytoolbar_et);
            mTextTitle = (TextView) mRootView.findViewById(R.id.mytoolbar_title);
            mRightButton = (Button) mRootView.findViewById(R.id.mytoobar_bt);

            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams
                    .WRAP_CONTENT, Gravity.CENTER_HORIZONTAL);

            addView(mRootView,layoutParams);
        }

        setRightIcon(mRightIcon);
        setRightButtonText(mRightButtonText);

            if (isShowSearchView) {
                showSearchView();
            } else {
                ShowTitle();
            }

    }


    public void showSearchView() {
        mSearchView.setVisibility(View.VISIBLE);
        mTextTitle.setVisibility(View.GONE);
        mRightButton.setVisibility(View.GONE);
    }

    public void ShowTitle() {

        mSearchView.setVisibility(View.GONE);
        mTextTitle.setVisibility(View.VISIBLE);
        mRightButton.setVisibility(View.VISIBLE);

    }


    public void hideRightButton(){
        mRightButton.setVisibility(View.GONE);
    }



    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setRightIcon(Drawable drawable) {

        if (mRightButton != null) {
            mRightButton.setBackground(mRightIcon);
            ShowTitle();
        }
    }

    public void setRightIcon(int icon) {
        setRightIcon(getResources().getDrawable(icon));
    }

    public void setRightButtonText(String text) {

        if (mRightButton != null) {
            mRightButton.setText(text);
        }
    }

    public void setRightButton(int resId) {
        setRightButtonText(getResources().getString(resId));
    }


    public void setTitle(CharSequence title) {

        initView();
        if (mTextTitle != null) {
            mTextTitle.setText(title);
            Log.i(TAG, title.toString());
            ShowTitle();
        }
    }

    public void setTitle(int resId) {
        setTitle(getResources().getText(resId));
    }

    public Button getRightButton(){

        return mRightButton;
    }

    public void setOnRightButtonClickListener(OnClickListener li){
        mRightButton.setOnClickListener(li);
    }

}
