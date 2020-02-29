package com.dc.recyclerviewanalisys.refreshLoad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dc.recyclerviewanalisys.R;
import com.dc.recyclerviewanalisys.TestBean;
import com.dc.recyclerviewanalisys.commonadapter.HolderImageLoader;
import com.dc.recyclerviewanalisys.commonadapter.RecyclerCommonAdapter;
import com.dc.recyclerviewanalisys.commonadapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class RefreshLoadActivity extends AppCompatActivity {

    private LoadRefreshRecyclerView mRecyclerView;

    private List<TestBean> mDatas;

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_load);

        initData();
        mRecyclerView = findViewById(R.id.recycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        final RecyclerCommonAdapter adapter = new RecyclerAdapter(this, mDatas, R.layout.item_home);
        mRecyclerView.setAdapter(adapter);
        View view = LayoutInflater.from(this).inflate(R.layout.view_header, mRecyclerView, false);
        // addRefreshViewCreator和addHeaderView先后顺序和效果有关
        mRecyclerView.addRefreshViewCreator(new DefaultRefreshCreator());
        mRecyclerView.addHeaderView(view);
        mRecyclerView.setOnRefreshListener(new RefreshRecyclerView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerView.onStopRefresh();
                    }
                }, 2000);
            }
        });

    }

    private void initData() {
        mDatas = new ArrayList<>();
        for (int i = '0'; i <= '9'; i++) {
            TestBean testBean = new TestBean();
            testBean.setLetter("" + (char) i);
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
