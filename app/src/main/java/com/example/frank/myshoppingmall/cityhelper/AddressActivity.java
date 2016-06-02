package com.example.frank.myshoppingmall.cityhelper;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.frank.myshoppingmall.R;
import com.example.frank.myshoppingmall.activity.OrderActivity;
import com.example.frank.myshoppingmall.bean.AddressBean;
import com.example.frank.myshoppingmall.util.ToastUtils;
import com.example.frank.myshoppingmall.widget.MyToolBar;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AddressActivity extends Activity implements OnClickListener {

    @Bind(R.id.mytoolbar)
    MyToolBar mMytoolbar;
    @Bind(R.id.et_receiver)
    EditText  mEtReceiver;
    @Bind(R.id.et_Mobile)
    EditText  mEtMobile;
    @Bind(R.id.et_postcode)
    EditText  mEtPostcode;
    @Bind(R.id.tv_city1)
    TextView  mTvCity1;
    @Bind(R.id.et_detailaddress)
    EditText  mEtDetailaddress;
    @Bind(R.id.btn_save)
    Button    mBtnSave;
    private TextView        tv_city1;
    private City            city;
    private ArrayList<City> toCitys;
    private Button          mButtonSubmit;
    private String          name, phone, postcode, citys, address;//定义数据

    public static final int ADDRESSRESULTCODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStatus();
        setContentView(R.layout.activity_address);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tv_city1 = (TextView) findViewById(R.id.tv_city1);
        mButtonSubmit = (Button) findViewById(R.id.btn_save);
        mButtonSubmit.setOnClickListener(this);

        tv_city1.setOnClickListener(this);
        city = new City();
        toCitys = new ArrayList<City>();

        mMytoolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AddressActivity.this.finish();
            }
        });


    }

    //设置状态栏的颜色。
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setStatus() {
        Window window = this.getWindow();
        //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        window.setStatusBarColor(getResources().getColor(R.color.color_status_bar));

        ViewGroup mContentView = (ViewGroup) this.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 预留出系统 View 的空间.
            ViewCompat.setFitsSystemWindows(mChildView, true);
        }
    }

    @Override
    public void onClick(View v) {

        if (v == tv_city1) {
            Intent in = new Intent(this, CitySelect1Activity.class);
            in.putExtra("city", city);
            startActivityForResult(in, 1);
        }
        if (v.getId() == R.id.btn_save) {

            boolean allEditText = getAllEditText();
            if (allEditText) {
                AddressBean addressBean = new AddressBean(citys+address, phone, postcode, name);
                Intent intent = new Intent();
                intent.putExtra(OrderActivity.ADDRESSBEAN, addressBean);
                setResult(RESULT_OK, intent);
                this.finish();
            }

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 8) {
            if (requestCode == 1) {
                city = data.getParcelableExtra("city");
                tv_city1.setText(city.getProvince() + city.getCity() + city.getDistrict());

            } else if (requestCode == 2) {
                toCitys = data.getParcelableArrayListExtra("toCitys");
                StringBuffer ab = new StringBuffer();
                for (int i = 0; i < toCitys.size(); i++) {
                    if (i == toCitys.size() - 1) {//如果是最后一个城市就不需要逗号
                        ab.append(toCitys.get(i).getCity());
                    } else {
                        ab.append(toCitys.get(i).getCity() + "， ");//如果不是最后一个城市就需要逗号
                    }
                }
            }
        }
    }


    public boolean getAllEditText() {

        boolean isAllFinished = false;
        name = mEtReceiver.getText().toString();
        postcode = mEtPostcode.getText().toString();
        address = mEtDetailaddress.getText().toString();
        phone = mEtMobile.getText().toString();
        citys = mTvCity1.getText().toString();

        if (name.equals("")) {
            ToastUtils.show(AddressActivity.this, "收货人不能为空");
        } else if (postcode.equals("")) {
            ToastUtils.show(AddressActivity.this, "邮编不能为空");
        } else if (address.equals("")) {
            ToastUtils.show(AddressActivity.this, "请输入详细地址");
        } else if (phone.equals("")) {
            ToastUtils.show(AddressActivity.this, "电话不能为空");
        } else {
            isAllFinished = true;
        }

        return isAllFinished;

    }


}
