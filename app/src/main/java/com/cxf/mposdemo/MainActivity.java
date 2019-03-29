package com.cxf.mposdemo;


import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cxf.module_route.MainRouteInfo;
import com.cxf.moudule_common.Base.BaseActivity;
import com.cxf.moudule_common.rxjava.RxBindUtils;

import butterknife.BindView;

//路由框架绑定
@Route(path = MainRouteInfo.MainActivity)
public class MainActivity extends BaseActivity {

    //绑定控件
    @BindView(R2.id.btn_main_show)
    Button button;

    @BindView(R2.id.tv_main_show)
    TextView textView;




    @Override
    protected void setActivityContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void setClickListening() {
        RxBindUtils.rxViewClicks(button, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("你点击了按钮");
            }
        });
    }

    @Override
    public void initData() {

    }

}
