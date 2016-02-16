package com.me.diankun.interview;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConfigChangeUseFragActivity extends AppCompatActivity implements RetaindFragment.OnFragmentInteractionListener {

    private RetaindFragment retaindFragment;
    @Bind(R.id.tv_showsth)
    TextView tv_showsth;
    private ProgressDialog progressDialog;
    private static final String TAG = "ConfigChangeUseFragActivity";
    private static final String KEY_CURRENT_NEWSDATA = "current_nesdata";

    private String info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_change_use_frag);

        ButterKnife.bind(this);

        if (null != savedInstanceState) {
            refreshNewsInfo(savedInstanceState.getString(KEY_CURRENT_NEWSDATA));
        }

        //第一个问题的方法四,如果是使用RetaindFragment的setRetainInstance(true);
        //在activity重启时获取到保留的fragment对象
        FragmentManager fragmentManager = getSupportFragmentManager();
        retaindFragment = (RetaindFragment) fragmentManager.findFragmentByTag("data");
        if (retaindFragment == null) {
            //添加Fragment
            retaindFragment = new RetaindFragment();
            fragmentManager.beginTransaction().add(retaindFragment, "data").commit();
        }
    }


    @OnClick(R.id.btn_createthread)
    void requestData() {
        //第一个问题第第三种解决方法
        //控制RetainFragment中的子线程启动
        progressDialog = ProgressDialog.show(this, "Load Info", "Loading...", true, true);
        retaindFragment.excuteLongTimeOperation();
    }

    @Override
    public void onFragmentInteraction(String info) {
        //关闭进度条
        progressDialog.dismiss();
        //更新数据
        this.info = info;
        refreshNewsInfo(info);
    }

    /**
     * 更新界面内容
     *
     * @param newsInfo
     */
    private void refreshNewsInfo(String newsInfo) {
        tv_showsth.setText(newsInfo);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //注意不要直接传newsInfo，否则在异步操作执行完成后旋转屏幕，内容还是会消失。因为该值只有在屏幕旋转的时候才赋值，
        outState.putString(KEY_CURRENT_NEWSDATA, tv_showsth.getText().toString());
        Log.i(TAG, "onSaveInstanceState");
    }
}
