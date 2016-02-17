package com.me.diankun.interview;

import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConfigChangeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_change);

        ButterKnife.bind(this);

    }

    @OnClick(R.id.btn_config_interview)
    void showConfigInterview(View view) {
        openActivity(ConfigChangeInterviewActivity.class);
    }

    @OnClick(R.id.btn_config_recoverdata)
    void recoverData(View view) {
        openActivity(ConfigChangeRecoverDataActivity.class);
    }

    @OnClick(R.id.btn_config_asyn)
    void asyncData(View view) {
        openActivity(ConfigChangeAsynActivity.class);
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }
}
