package com.me.diankun.interview;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.ButterKnife;

/**
 * 有关Activity中的设置Config后，切换屏幕的生命周期调用
 */
public class ConfigChangeInterviewActivity extends AppCompatActivity {

    private static final String TAG = "ConfigChangeInterviewActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate()");
        String htmlStr = "<h2>一题关于android:configChanges的修正</h2><br>" +
                "<font color='#FF4081'>①</font><font size='22'>Activity未设置android:configChanges时，切横屏、切竖屏时生命周期分别重新调用一次</font><br><br>" +
                "<font color='#FF4081'>②</font><font size='22'>设置Activity的android:configChanges=”orientation”时，切横屏、切竖屏时生命周期也分别重新调用一次</font><br><br>" +
                "<font color='#FF4081'>③</font><font size='22'>设置Activity的android:configChanges=”orientation|keyboardHidden”时，在android3.2之前切屏不会重新调用各个生命周期，会执行onConfigurationChanged()方法；在android3.2之后，必须再添加screenSize才会在切屏的时候不会调用各个生命周期，执行onConfigurationChange()方法</font>";
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        TextView text = new TextView(this);
        text.setLayoutParams(params);
        text.setText(Html.fromHtml(htmlStr));
        //text.setText("Hello");

        setContentView(text);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart()");
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume()");
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop()");
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
        Log.i(TAG, "onDestroy()");
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.i(TAG, "onConfigurationChanged()");
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.i(TAG, "change to ORIENTATION_PORTRAIT");
        } else {
            Log.i(TAG, "change to ORIENTATION_LANDSCAPE");
        }
    }

}
