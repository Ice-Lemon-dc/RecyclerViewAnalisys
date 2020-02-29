package com.dc.recyclerviewanalisys.baseUse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dc.recyclerviewanalisys.R;
import com.dc.recyclerviewanalisys.TestBean;
import com.dc.recyclerviewanalisys.commonadapter.HolderImageLoader;
import com.dc.recyclerviewanalisys.commonadapter.ItemClickListener;
import com.dc.recyclerviewanalisys.commonadapter.RecyclerCommonAdapter;
import com.dc.recyclerviewanalisys.commonadapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class BaseUseActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    private List<TestBean> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_use);

        initData();
        mRecyclerView = findViewById(R.id.recycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerCommonAdapter adapter = new RecyclerAdapter(this, mDatas, R.layout.item_home);
        adapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(BaseUseActivity.this, mDatas.get(position).getLetter(), Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView.setAdapter(adapter);

        GridLayoutItemDecoration gridLayoutItemDecoration = new GridLayoutItemDecoration(this, R.drawable.item_dirver);

        mRecyclerView.addItemDecoration(gridLayoutItemDecoration);


    }

    private void initData() {
        mDatas = new ArrayList<>();
        for (int i = 'A'; i <= 'z'; i++) {
            TestBean testBean = new TestBean();
            testBean.setLetter("" + (char) i);
            testBean.setSrc(R.mipmap.ic_launcher);
            testBean.setPath("http://a0.att.hudong.com/16/12/01300535031999137270128786964.jpg");
            mDatas.add(testBean);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_action_gridview:
                mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
                break;
            case R.id.id_action_listview:
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                break;
        }
        return true;
    }

//    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
//
//        @NonNull
//        @Override
//        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(BaseUseActivity.this).inflate(R.layout.item_home, parent, false);
//            return new ViewHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//            holder.itemTv.setText(mDatas.get(position));
//        }
//
//        @Override
//        public int getItemCount() {
//            return mDatas.size();
//        }
//
//        public class ViewHolder extends RecyclerView.ViewHolder {
//
//            public TextView itemTv;
//
//            public ViewHolder(@NonNull View itemView) {
//                super(itemView);
//                itemTv = itemView.findViewById(R.id.id_num);
//            }
//        }
//    }

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
