package com.example.frank.myshoppingmall.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.frank.myshoppingmall.R;

/**
 * 创建者     Frank
 * 创建时间   2016/5/19 15:58
 * 描述	      ${TODO}
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class WarePriceFragment extends BaseFragment {

    public static String TABLAYOUT_FRAGMENT = "tab_fragment";

    private TextView mTextView;
    private String id ;

    public static WarePriceFragment newInstance(String id) {
        WarePriceFragment fragment = new WarePriceFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(TABLAYOUT_FRAGMENT, id);
        fragment.setArguments(bundle);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments()!=null){
            id = (String) getArguments().getSerializable(TABLAYOUT_FRAGMENT);
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = View.inflate(mActivity, R.layout.fragment_ware_price, null);

        mTextView= (TextView) view.findViewById(R.id.warelist_tv);

        mTextView.setText(id+"");
        return view;

    }
}
