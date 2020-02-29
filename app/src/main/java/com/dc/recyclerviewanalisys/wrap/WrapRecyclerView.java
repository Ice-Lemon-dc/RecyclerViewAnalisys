package com.dc.recyclerviewanalisys.wrap;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 添加头部底部RecyclerView  需要先设置Adapter再添加头部或底部
 */
public class WrapRecyclerView extends RecyclerView {

    /**
     * 包裹了一层的头部底部Adapter
     */
    private WrapRecyclerAdapter mWrapRecyclerAdapter;

    /**
     * 这个是列表数据的Adapter
     */
    private RecyclerView.Adapter mAdapter;

    private AdapterDataObserver mDataObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            if (mAdapter == null) return;
            // 观察者  列表Adapter更新 包裹的也需要更新不然列表的notifyDataSetChanged没效果
            if (mWrapRecyclerAdapter != mAdapter)
                mWrapRecyclerAdapter.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            if (mAdapter == null) return;
            // 观察者  列表Adapter更新 包裹的也需要更新不然列表的notifyDataSetChanged没效果
            if (mWrapRecyclerAdapter != mAdapter)
                mWrapRecyclerAdapter.notifyItemRemoved(positionStart);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            if (mAdapter == null) return;
            // 观察者  列表Adapter更新 包裹的也需要更新不然列表的notifyItemMoved没效果
            if (mWrapRecyclerAdapter != mAdapter)
                mWrapRecyclerAdapter.notifyItemMoved(fromPosition, toPosition);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            if (mAdapter == null) return;
            // 观察者  列表Adapter更新 包裹的也需要更新不然列表的notifyItemChanged没效果
            if (mWrapRecyclerAdapter != mAdapter)
                mWrapRecyclerAdapter.notifyItemChanged(positionStart);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            if (mAdapter == null) return;
            // 观察者  列表Adapter更新 包裹的也需要更新不然列表的notifyItemChanged没效果
            if (mWrapRecyclerAdapter != mAdapter)
                mWrapRecyclerAdapter.notifyItemChanged(positionStart,payload);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            if (mAdapter == null) return;
            // 观察者  列表Adapter更新 包裹的也需要更新不然列表的notifyItemInserted没效果
            if (mWrapRecyclerAdapter != mAdapter)
                mWrapRecyclerAdapter.notifyItemInserted(positionStart);
        }
    };

    public WrapRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public WrapRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WrapRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setAdapter(@Nullable Adapter adapter) {
        // 为了防止多次设置Adapter
        if (mAdapter != null) {
            mAdapter.unregisterAdapterDataObserver(mDataObserver);
            mAdapter = null;
        }

        this.mAdapter = adapter;

        if (adapter instanceof WrapRecyclerAdapter) {
            mWrapRecyclerAdapter = (WrapRecyclerAdapter) adapter;
        } else {
            mWrapRecyclerAdapter = new WrapRecyclerAdapter(adapter);
        }

        super.setAdapter(mWrapRecyclerAdapter);

        // 注册一个观察者 删除的问题 列表的adapter改变了,但是WrapRecyclerAdapter没改变
        mAdapter.registerAdapterDataObserver(mDataObserver);

        // 解决GridLayout添加头部和底部也要占据一行
        mWrapRecyclerAdapter.adjustSpanSize(this);

    }

    public void addHeaderView(View view) {
        if (mAdapter != null) {
            mWrapRecyclerAdapter.addHeaderView(view);
        }
    }

    public void addFooterView(View view) {
        if (mAdapter != null) {
            mWrapRecyclerAdapter.addFooterView(view);
        }
    }

    public void removeHeaderView(View view) {
        if (mAdapter != null) {
            mWrapRecyclerAdapter.removeHeaderView(view);
        }
    }

    public void removeFooterView(View view) {
        if (mAdapter != null) {
            mWrapRecyclerAdapter.removeFooterView(view);
        }
    }
}
