package com.me.diankun.interview;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by diankun on 2016/2/16.
 */
public class LifeActivity extends AppCompatActivity {

    @Bind(R.id.btn_opendialog)
    Button btn_opendialog;

    @Bind(R.id.btn_dialogActivity)
    Button btn_dialogActivity;

    @Bind(R.id.btn_nextActivity)
    Button btn_nextActivity;

    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life);
        ButterKnife.bind(this);

        Log.i(TAG, "onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart");
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    @OnClick(R.id.btn_opendialog)
    void openDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Dialog").setMessage("Are you sure?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int arg1) {
                        dialog.dismiss();
                        Toast.makeText(LifeActivity.this, "Yes", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int arg1) {
                dialog.dismiss();
                Toast.makeText(LifeActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }

    @OnClick(R.id.btn_dialogActivity)
    void openDialogActivity(View view) {
        Intent intent = new Intent(LifeActivity.this, DialogActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_nextActivity)
    void openNextActivity(View view) {
        Intent intent = new Intent(LifeActivity.this, NextActivity.class);
        startActivity(intent);
    }
}
