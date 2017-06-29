package com.bawei.recyclerview;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;

import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnLoadMoreListener;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.bawei.recyclerview.Bean.NewBean;
import com.google.gson.Gson;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class MainActivity extends Activity {
    private IRecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;


    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    recyclerView.setRefreshing(false);
                    break;
                case 2:
                    loadMoreFooterView.setStatus(LoadMoreFooterView.Status.GONE);
                    break;
            }
        }
    };
    private LoadMoreFooterView loadMoreFooterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = (IRecyclerView) findViewById(R.id.recycleview_id);

        loadMoreFooterView = (LoadMoreFooterView) recyclerView.getLoadMoreFooterView();



        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(this)
                        .color(Color.RED)
                        .build());

        recyclerView.setItemAnimator(new DefaultItemAnimator());


        recyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {

                System.out.println(" list_new");
                recyclerView.setRefreshing(true);
                handler.sendEmptyMessageDelayed(1, 2000);


            }
        });


        recyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                System.out.println(" list_new");
                loadMoreFooterView.setStatus(LoadMoreFooterView.Status.LOADING);
                handler.sendEmptyMessageDelayed(2, 2000);

            }
        });


        //访问网络
        httpget();
    }

    //访问网络
    private void httpget() {
        //访问网络，得到数据
        RequestParams patams = new RequestParams("http://qhb.2dyt.com/Bwei/news?type=1&page=1&postkey=1503d");
        x.http().get(patams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("list_new " + result);
                //成功
                Gson gson = new Gson();
                NewBean newBean = gson.fromJson(result, NewBean.class);
                //添加适配器
                MyRecyclerAdapter adapter = new MyRecyclerAdapter(MainActivity.this, newBean.getList());
                recyclerView.setIAdapter(adapter);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                //失败
            }

            @Override
            public void onCancelled(CancelledException cex) {
                //取消
            }

            @Override
            public void onFinished() {
                //成功
            }
        });
    }


}
