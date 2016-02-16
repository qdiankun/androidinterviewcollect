package com.me.diankun.interview;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 异步请求数据，显示正在加载的进度条，请求完成后更新数据
 * Blog的参见：http://blog.csdn.net/aliaooooo/article/details/23606179?utm_source=tuicool
 * 进度条的翻转：http://stackoverflow.com/questions/1111980/how-to-handle-screen-orientation-change-when-progress-dialog-and-background-thre
 */
public class ConfigChangeAsynActivity extends BaseActivity {

    private static final String TAG = "ConfigChangeAsynActivity";
    private ProgressDialog progressDialog;
    private String newsInfo;

    @Bind(R.id.tv_showsth)
    TextView tv_showsth;

    //处理请求异步任务时显示进度条，翻转屏幕后，报错，使用ProgerssBar,根本原因是：这个bug不是因为old activity没销毁导致内存泄露，而是activity被销毁后 progressDialog还持有这个activity的引用
    @Bind(R.id.progress_circular)
    ProgressBar progress_circular;

    //打印时间
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    /**
     * 以匿名类的形式创建handler
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //progressDialog.dismiss();
            //隐藏进度条
            progress_circular.setVisibility(View.GONE);
            //更新界面中的TextVeiw中的内容
            refreshNewsInfo(msg.getData().getString("message"));
        }
    };

    private void refreshNewsInfo(String message) {
        tv_showsth.setText(message);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_change_asyn);

        ButterKnife.bind(this);

        //打印主线程的部分信息
        Log.i(TAG, "ui threadid = " + Thread.currentThread().getId());

        Log.i(TAG, "savedInstanceState = " + savedInstanceState);
        //第一个问题的方法三,如果是旋转屏幕取出数据，初次进入请求数据
//        if (savedInstanceState != null) {
//            newsInfo = savedInstanceState.getString("newsInfo");
//            refreshNewsInfo(newsInfo);
//        } else {
//            excuteLongTimeOperation();
//        }

    }

    @OnClick(R.id.btn_createthread)
    void requestData() {
        excuteLongTimeOperation();
    }

    @OnClick(R.id.btn_use_fragment)
    void useFragment() {
        openActivity(ConfigChangeUseFragActivity.class);
    }

    /**
     * 点击按钮，创建子线程，并显示一个进度对话框
     */
    private void excuteLongTimeOperation() {
        //progressDialog = ProgressDialog.show(this, "Load Info", "Loading...", true, true);
        //显示进度条
        progress_circular.setVisibility(View.VISIBLE);
        Thread workerThread = new Thread(new MyNewThread());
        workerThread.start();
    }

    private class MyNewThread extends Thread {
        @Override
        public void run() {
            //打印子线程的部分信息
            Log.i(TAG, "threadid = " + Thread.currentThread().getId());
            //模拟执行耗时操作
            try {
                Thread.sleep(5 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            newsInfo = "#搜寻马航370#【澳联合协调中心今日记者会要点】1.发现油迹的地点距离信号发现地很近，油迹来源需进一步调查。2.黑匣子一般只有30天寿命，最多40天，今天已经是第38天了，但仍有可能收到信号";
            Message message = handler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("message", newsInfo);
            message.setData(bundle);
            handler.sendMessage(message);

            //异步操作执行的时间
            String time = simpleDateFormat.format(new Date(System.currentTimeMillis()));
            Log.i(TAG, "MyNewThread time=" + time);
        }
    }


//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        String newsInfo = savedInstanceState != null ? savedInstanceState.getString("editvalue") : "";
//        Log.i(TAG, "onRestoreInstanceState() editvalue " + editvalue);
//    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState() newsInfo = " + newsInfo);
        outState.putString("newsInfo", newsInfo);
    }


    /**
     * 指明了配置的变化只有在AndroidManifest.xml中对该Activity设置android:configChanges，该方法才会被回调
     *
     * @param newConfig
     */
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
        super.onDestroy();
        //ActivityDestory的时间
        String time = simpleDateFormat.format(new Date(System.currentTimeMillis()));
        Log.i(TAG, "onDestroy() time=" + time);
    }
}
