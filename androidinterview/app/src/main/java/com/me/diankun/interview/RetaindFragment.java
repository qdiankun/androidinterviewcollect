package com.me.diankun.interview;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;

/**
 * Class: RetaindFragment
 * Description:使用Fragment的setRetainInstance()处理
 *
 * @author diankun
 * @date 2015/11/25  15:05
 */
public class RetaindFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private String newsInfo;
    private static final String TAG = "RetaindFragment";

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            mListener.onFragmentInteraction(msg.getData().getString("message"));
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        //retain the fragment
        setRetainInstance(true);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach  context="+context);
        try {
            mListener = (OnFragmentInteractionListener) context;
        } catch (Exception e) {
            throw new ClassCastException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG,"onDetach");
        mListener = null;
    }

    public void excuteLongTimeOperation() {
        //progressDialog = ProgressDialog.show(this, "Load Info", "Loading...", true, true);
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
        }
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(String info);
    }
}
