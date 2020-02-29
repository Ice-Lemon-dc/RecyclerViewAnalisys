package com.dc.recyclerviewanalisys.wrap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dc.recyclerviewanalisys.R;
import com.dc.recyclerviewanalisys.TestBean;
import com.dc.recyclerviewanalisys.baseUse.BaseUseActivity;
import com.dc.recyclerviewanalisys.commonadapter.HolderImageLoader;
import com.dc.recyclerviewanalisys.commonadapter.ItemClickListener;
import com.dc.recyclerviewanalisys.commonadapter.RecyclerCommonAdapter;
import com.dc.recyclerviewanalisys.commonadapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class HeaderFooterActivity extends AppCompatActivity {

    private WrapRecyclerView mRecyclerView;

    private List<TestBean> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header_footer);

        initData();
        mRecyclerView = findViewById(R.id.recycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        final RecyclerCommonAdapter adapter = new RecyclerAdapter(this, mDatas, R.layout.item_home);
//        WrapRecyclerAdapter wrapRecyclerAdapter = new WrapRecyclerAdapter(adapter);
//        wrapRecyclerAdapter.addHeaderView(LayoutInflater.from(this).inflate(R.layout.view_header, mRecyclerView, false));
//        wrapRecyclerAdapter.addFooterView(LayoutInflater.from(this).inflate(R.layout.view_header, mRecyclerView, false));
//        mRecyclerView.setAdapter(wrapRecyclerAdapter);
        mRecyclerView.setAdapter(adapter);
        final View view = LayoutInflater.from(this).inflate(R.layout.view_header, mRecyclerView, false);
        mRecyclerView.addHeaderView(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerView.removeHeaderView(view);
            }
        });

        adapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(HeaderFooterActivity.this, "删除" + mDatas.get(position).getLetter(), Toast.LENGTH_SHORT).show();
                mDatas.remove(position);
                adapter.notifyDataSetChanged();
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