package com.me.diankun.interview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewStub;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class StubActivity extends AppCompatActivity {

    //把commLv2设置为类的成员变量
    private ListView commLv2 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewstub_layout);

        //如果想加载布局时，可以使用下面任意的一种方法

        //第一种使用Visibility判断是否加载
        //useViewStubWayOne();

        //第二种使用方式判断根布局是否为null ，判断是否加载
        useViewStubWayTwo();
    }

    private void useViewStubWayTwo() {
        ViewStub viewStub = (ViewStub) findViewById(R.id.stub_import);
        // 成员变量commLv2为空则代表未加载
        if (commLv2 == null) {
            // 加载评论列表布局, 并且获取评论ListView,inflate函数直接返回ListView对象
            commLv2 = (ListView) viewStub.inflate();
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, new String[]{"Hello", "China", "Welcome"});
            commLv2.setAdapter(adapter);
        } else {

        }
    }

    private void useViewStubWayOne() {
        //获取ViewStub
        ViewStub listStub = (ViewStub) findViewById(R.id.stub_import);
        //加载评论列表布局
        listStub.setVisibility(View.VISIBLE);
        //获取评论的ListView,此处通过ViewStub的inflatedId来获取
        ListView commListView = (ListView) findViewById(R.id.stub_comm_lv);
        if (listStub.getVisibility() == View.VISIBLE) {
            //已经加载，否则还没有加载
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, new String[]{"Hello", "China", "Welcome"});
            commListView.setAdapter(adapter);
        }
    }
}
