package com.me.diankun.interview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class UiOptimizeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_optimize);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_use_viewstub)
    void openViewStub(View view) {
        Intent intent = new Intent(UiOptimizeActivity.this, StubActivity.class);
        startActivity(intent);
    }
}
