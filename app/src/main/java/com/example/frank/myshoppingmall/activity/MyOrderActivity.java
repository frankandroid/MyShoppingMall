package com.example.frank.myshoppingmall.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.frank.myshoppingmall.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 创建者     Frank
 * 创建时间   2016/5/21 11:16
 * 描述	      ${TODO}
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class MyOrderActivity extends AppCompatActivity {


    @Bind(R.id.activity_location)
    TextView mActivityLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myorder);
        ButterKnife.bind(this);
    }
}
