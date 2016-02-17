package com.me.diankun.interview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * 基类
 */
public class BaseActivity extends AppCompatActivity {

    public void openActivity(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    public void openActivity(Class clazz, String name, int value) {
        Intent intent = new Intent(this, clazz);
        intent.putExtra(name, value);
        startActivity(intent);
    }
}
