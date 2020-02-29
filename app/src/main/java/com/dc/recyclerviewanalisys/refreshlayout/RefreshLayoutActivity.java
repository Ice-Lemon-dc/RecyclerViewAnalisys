package com.dc.recyclerviewanalisys.refreshlayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dc.recyclerviewanalisys.R;
import com.dc.recyclerviewanalisys.TestBean;
import com.dc.recyclerviewanalisys.commonadapter.HolderImageLoader;
import com.dc.recyclerviewanalisys.commonadapter.RecyclerCommonAdapter;
import com.dc.recyclerviewanalisys.commonadapter.ViewHolder;
import com.dc.recyclerviewanalisys.wrap.WrapRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RefreshLayoutActivity extends AppCompatActivity {

    private RefreshLayout mRefreshLayout;

    private WrapRecyclerView mRecyclerView;

    private List<TestBean> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_layout);

        initData();
        mRefreshLayout = findViewById(R.id.refresh_layout);
        mRecyclerView = findViewById(R.id.recycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        final RecyclerCommonAdapter adapter = new RecyclerAdapter(this, mDatas, R.layout.item_home);
        mRecyclerView.setAdapter(adapter);
        final View view = LayoutInflater.from(this).inflate(R.layout.view_header, mRecyclerView, false);
        mRecyclerView.addHeaderView(view);
        mRecyclerView.addFooterView(view);

        HeaderView headerView = new HeaderView(this);
        mRefreshLayout.setHeaderView(headerView);

        mRefreshLayout.setFooterView(new FooterView(this));

        //设置刷新监听，触发刷新时回调
        mRefreshLayout.setOnRefreshListener(new RefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //延时3秒刷新完成，模拟网络加载的情况
                mRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //通知刷新完成
                        mRefreshLayout.finishRefresh(true);
                        //是否还有更多数据
                        mRefreshLayout.hasMore(true);
                    }
                }, 3000);
            }
        });

        mRefreshLayout.setOnLoadMoreListener(new RefreshLayout.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //通知加载完成
                        if (adapter.getItemCount() < 50) {
                            int start = adapter.getItemCount();
                            for (int i = start + 1; i <= start + 10; i++) {
                                TestBean testBean = new TestBean();
                                testBean.setLetter("" +  i);
                                testBean.setSrc(R.mipmap.ic_launcher);
                                testBean.setPath("http://a0.att.hudong.com/16/12/01300535031999137270128786964.jpg");
                                mDatas.add(testBean);
                            }
                            adapter.notifyDataSetChanged();
                            mRefreshLayout.finishLoadMore(true, true);
                        } else {
                            mRefreshLayout.finishLoadMore(true, false);
                        }
                    }
                }, 3000);
            }
        });
    }


    private void initData() {
        mDatas = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            TestBean testBean = new TestBean();
            testBean.setLetter("" +  i);
            testBean.setSrc(R.mipmap.ic_launcher);
            testBean.setPath("http://a0.att.hudong.com/16/12/01300535031999137270128786964.jpg");
            mDatas.add(testBean);
        }
    }

    private class RecyclerAdapter extends RecyclerCommonAdapter<TestBean> {

        private Context mContext;

        public RecyclerAdapter(Context context, List<TestBean> datas, int layoutId) {
            super(context, datas, layoutId);
            this.mContext = context;
        }

        @Override
        protected void convert(ViewHolder holder, TestBean testBean, int position) {
            holder.setText(R.id.id_num, testBean.getLetter())
                    .setImageResource(R.id.iv_res, testBean.getSrc())
                    .setImagePath(R.id.iv_path, testBean.getPath(), new HolderImageLoader() {
                        @Override
                        public void loadImage(ImageView imageView, String path) {
                            Glide.with(mContext).load(path).into(imageView);
                        }
                    });
        }

    }
}
