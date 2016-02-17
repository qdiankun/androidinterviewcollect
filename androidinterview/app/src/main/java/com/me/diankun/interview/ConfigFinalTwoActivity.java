package com.me.diankun.interview;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

/**
 * Created by diankun on 2016/2/17.
 */
public class ConfigFinalTwoActivity extends AppCompatActivity{

    private Button btn_createthread;
    static ProgressDialog dialog;
    private Thread downloadThread;
    final static Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            super.handleMessage(msg);

            dialog.dismiss();

        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_change_use_frag);


        downloadThread = (Thread) getLastCustomNonConfigurationInstance();
        if (downloadThread != null && downloadThread.isAlive()) {
            dialog = ProgressDialog.show(ConfigFinalTwoActivity.this, "",
                    "Signing in...", false);
        }

        dialog = ProgressDialog.show(ConfigFinalTwoActivity.this, "",
                "Signing in ...", false);

        downloadThread = new MyThread();
        downloadThread.start();
    }

    // Save the thread
    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return downloadThread;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }

    static public class MyThread extends Thread {
        @Override
        public void run() {

            try {
                // Simulate a slow network
                try {
                    new Thread().sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(0);

            } finally {

            }
        }
    }
}
