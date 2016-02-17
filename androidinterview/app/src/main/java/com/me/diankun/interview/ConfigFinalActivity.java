package com.me.diankun.interview;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by diankun on 2016/2/17.
 * 问题：以下说法错误
 *      在Activity里创建一个子线程来跑耗时操作，在异步操作没结束前旋转屏幕，线程没执行完，old Activity也就不会被销毁，会导致内存泄露
 * 打印各个销毁的时间
 *      FINAL: threadid = 4099	线程启动时间 = 2016-02-17 10:40:32
 *      FINAL: mainid = 1	Activity销毁时间 = 2016-02-17 10:40:35
 *      threadid = 4099	线程销毁时间 = 2016-02-17 10:40:36
 *      可以看到在线程结束之前Activity已经销毁掉了
 * 报如下错误：
        *java.lang.IllegalArgumentException: View=com.android.internal.policy.PhoneWindow$DecorView{f4e8fa2 V.E...... R......D 0,0-1026,483} not attached to window manager
 *      这个bug不是因为old activity没销毁导致内存泄露，而是activity被销毁后 progressDialog还持有这个activity的引用
 *      异步任务开始时显示对话框，任务完成后去取消progressDialog。当任务没结束时旋转屏幕，会导致old activity被销毁，然后到了线程执行结束要dismiss progressDialog的时候发现所属的activity已经不在了。
 * 解决方法：
 * 1.在布局文件创建progressBar来代替progressDialog
 * 2. 创建一个AsyncTask的时候把当前Activity的引用传给其构造函数。onRetainNonConfigurationInstance()中判断线程是否结束，如果结束了就把progressDialog取消掉，然后将AsyncTask对象mTask返回。在onCreate中通过getLastNonConfigurationInstance()接收mTask，关联当前activity——mtask.mContext = this;再重新启动一个progressDialog。保证了progressDialog在actviity销毁钱被dismiss掉。from How to handle screen orientation change when progress dialog and background thread active?中的其中一个回答。单单只是测试progressDialog在横竖屏切换时是否会崩溃，测试结果是正常的。
 * 3.网上还有说用IntentService来解决，没用过这个，先不测了
 */
public class ConfigFinalActivity extends AppCompatActivity {

    private SimpleDateFormat sdf;
    private ProgressDialog mDialog;

//    @Bind(R.id.progress_circular)
    ProgressBar mProgressBar;

    private Handler mHanlder = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.e("FINAL", "mProgressBar = " + mProgressBar);
            //mDialog.dismiss();
            mProgressBar.setVisibility(View.GONE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_change_use_frag);
        Log.i("FINAL", "onCreate   Context = " + this);
        ButterKnife.bind(this);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_circular);
        sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        mDialog = new ProgressDialog(this);
//        mDialog.setTitle("Load Info");
//        mDialog.setMessage("Loading...");
//        mDialog.setCancelable(true);
//        mDialog.setIndeterminate(true);
    }


    @OnClick(R.id.btn_createthread)
    void doWork() {
        //  启动线程
        new Thread(new MyThread()).start();
        //mDialog.show();
        mProgressBar.setVisibility(View.VISIBLE);
    }

    class MyThread implements Runnable {

        @Override
        public void run() {
            String startTime = sdf.format(new Date(System.currentTimeMillis()));
            Log.i("FINAL", "threadid = " + Thread.currentThread().getId() + "\t线程启动时间 = " + startTime);
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String finishTime = sdf.format(new Date(System.currentTimeMillis()));
            Log.i("FINAL", "threadid = " + Thread.currentThread().getId() + "\t线程销毁时间 = " + finishTime);
            mHanlder.sendEmptyMessage(0);
        }
    }

    @Override
    protected void onDestroy() {
        Log.i("FINAL", "onDestroy   Context = "+this);
        ButterKnife.unbind(this);
        String destroyTime = sdf.format(new Date(System.currentTimeMillis()));
        Log.i("FINAL", "mainid = " + Thread.currentThread().getId() + "\tActivity销毁时间 = " + destroyTime);
        super.onDestroy();
    }

}
