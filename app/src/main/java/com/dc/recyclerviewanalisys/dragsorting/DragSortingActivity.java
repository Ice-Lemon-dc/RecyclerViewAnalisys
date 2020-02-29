package com.dc.recyclerviewanalisys.dragsorting;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
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
import com.dc.recyclerviewanalisys.wrap.HeaderFooterActivity;
import com.dc.recyclerviewanalisys.wrap.WrapRecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class DragSortingActivity extends AppCompatActivity {

    private WrapRecyclerView mRecyclerView;

    private List<TestBean> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_sorting);

        initData();
        mRecyclerView = findViewById(R.id.recycler_view);

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        final RecyclerCommonAdapter adapter = new RecyclerAdapter(this, mDatas, R.layout.item_home);
        mRecyclerView.setAdapter(adapter);
//        final View view = LayoutInflater.from(this).inflate(R.layout.view_header, mRecyclerView, false);
//        mRecyclerView.addHeaderView(view);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                // 获取触摸响应的方向   包含两个 1.拖动dragFlags 2.侧滑删除swipeFlags
                // 代表只能是向左侧滑删除，当前可以是这样ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT
                int swipeFlags = ItemTouchHelper.LEFT;
                // 拖动方向
                int dragFlags = 0;
                if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                    dragFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                } else {
                    dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                }
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                // 拖动的时候会不断的回调这个方法

                // 获取原来的位置
                int fromPosition = viewHolder.getAdapterPosition();
                // 目标位置
                int targetPosition = target.getAdapterPosition();

                // 改变实际的数据集
                if (fromPosition < targetPosition) {
                    for (int i = fromPosition; i <targetPosition ; i++) {
                        Collections.swap(mDatas, i, i + 1);
                    }
                } else {
                    for (int i = fromPosition; i > targetPosition; i--) {
                        Collections.swap(mDatas, i, i - 1);
                    }
                }


                adapter.notifyItemMoved(fromPosition, targetPosition);

                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // 侧滑删除之后的回调方法
                int position = viewHolder.getAdapterPosition();
                mDatas.remove(position);
                adapter.notifyItemRemoved(position);
            }

            @Override
            public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
                // 拖动选择状态改变回调
                if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                    viewHolder.itemView.setBackgroundColor(Color.GRAY);
                }
            }

            @Override
            public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                // 回到正常状态的时候回调
                viewHolder.itemView.setBackgroundColor(Color.TRANSPARENT);

                // 解决侧滑删除时由于item复用出现的不显示问题
                viewHolder.itemView.setTranslationX(0);
            }
        });

        itemTouchHelper.attachToRecyclerView(mRecyclerView);
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
