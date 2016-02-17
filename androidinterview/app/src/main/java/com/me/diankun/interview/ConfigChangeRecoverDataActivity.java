package com.me.diankun.interview;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ConfigChangeRecoverDataActivity extends AppCompatActivity {

    @Bind(R.id.et_edit)
    EditText et_edit;

    private static final String TAG = "ConfigChangeRecoverDataActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_change_recover_data);
        ButterKnife.bind(this);

        String editvalue = savedInstanceState != null ? savedInstanceState.getString("editvalue") : "";
        Log.i(TAG, "onCreate() editvalue " + editvalue);
        et_edit.setText(editvalue);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String editvalue = savedInstanceState != null ? savedInstanceState.getString("editvalue") : "";
        Log.i(TAG, "onRestoreInstanceState() editvalue " + editvalue);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState()");
        String editvalue = et_edit.getText().toString();
        outState.putString("editvalue", editvalue);
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
        ButterKnife.unbind(this);
        super.onDestroy();
        Log.i(TAG, "onDestroy()");
    }

}
