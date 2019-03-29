package com.cxf.moudule_common.Base;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.cxf.module_resource.EventBus.IEvent;
import com.cxf.moudule_common.Listener.ScreenListener;
import com.cxf.moudule_common.R;
import com.cxf.moudule_common.rxjava.RxJavaUtil;
import com.cxf.moudule_common.util.ToastUtil;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseActivity extends AppCompatActivity {


    TextView tv_back;
    TextView tv_title;



    //设置标题
    public void setTitle(String title) {
        if (null == tv_back) {
            tv_back = (TextView) findViewById(R.id.view_head_left);
        }
        if (null == tv_title) {
            tv_title = (TextView) findViewById(R.id.view_head_title);
        }
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_title.setText(title);
    }




    Unbinder unbinder;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityContentView();
        unbinder = ButterKnife.bind(this);
        initData();
        setClickListening();
    }
    /****
     * 加载布局
     */
    protected abstract void setActivityContentView();



    /****
     * 点击事件
     * listening
     */
    protected abstract void setClickListening();

    /****
     * 加载数据
     */
    public abstract void initData();

    protected ScreenListener screenListener = new ScreenListener(this);


    protected class RetrofitCallBack implements RxJavaUtil.callBackRetrofitWorks{
        @Override
        public void callBackResponse(Object response) {

        }

        @Override
        public void callBackResponseError(Object errorCode, Object errorMessage) {
            if("50001".equals(errorCode)){
                ToastUtil.showShort(BaseActivity.this,"错误50001");
            }
        }
    }










    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        hintKeyboard();
        return super.dispatchTouchEvent(ev);
    }

    //关闭软键盘
    public void hintKeyboard() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm.isActive()&&getCurrentFocus()!=null){
            if (getCurrentFocus().getWindowToken()!=null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(unbinder!=null){
            unbinder.unbind();
        }

    }

    /******
     *  EventBus.getDefault().post(new ChangeUiEvent());
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(IEvent event) {
        onEventBuMessageEvent(event);
    }


    public  void onEventBuMessageEvent(IEvent IEventBusEvent){}

}
