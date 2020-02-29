package com.dc.recyclerviewanalisys.wrap;

import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 可以添加头部和底部的Adapter
 */
public class WrapRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /**
     * 数据列表Adapter,不包含头部底部
     */
    private RecyclerView.Adapter mAdapter;

    /**
     * 头部
     */
    private SparseArray<View> mHeaderViews;

    /**
     * 底部
     */
    private SparseArray<View> mFooterViews;

    /**
     * 基本的头部类型开始位置 用于viewType
     */
    private static int BASE_ITEM_TYPE_HEADER = 1000000;

    /**
     * 基本的底部类型开始位置
     */
    private static int BASE_ITEM_TYPE_FOOTER = 2000000;

    public WrapRecyclerAdapter(RecyclerView.Adapter adapter) {
        this.mAdapter = adapter;
        mHeaderViews = new SparseArray<>();
        mFooterViews = new SparseArray<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (isHeaderViewType(viewType)) {
            return createHeaderFooterViewHolder(mHeaderViews.get(viewType));
        } else if (isFooterViewType(viewType)) {
            return createHeaderFooterViewHolder(mFooterViews.get(viewType));
        }
        //列表
        return mAdapter.onCreateViewHolder(parent, viewType);
    }

    /**
     * 是不是头部类型
     */
    private boolean isHeaderViewType(int viewType) {
        int position = mHeaderViews.indexOfKey(viewType);
        return position >= 0;
    }

    /**
     * 是不是底部类型
     */
    private boolean isFooterViewType(int viewType) {
        int position = mFooterViews.indexOfKey(viewType);
        return position >= 0;
    }

    /**
     * 创建头部和底部的ViewHolder
     *
     * @param view View
     * @return ViewHolder
     */
    private RecyclerView.ViewHolder createHeaderFooterViewHolder(View view) {

        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // 头部不需要绑定数据
        if (isHeaderPosition(position) || isFooterPosition(position)) {
            return;
        }
        // 计算一下位置
        position = position - mHeaderViews.size();
        mAdapter.onBindViewHolder(holder, position);
    }

    /**
     * 是不是底部位置
     */
    private boolean isFooterPosition(int position) {
        return position >= (mHeaderViews.size() + mAdapter.getItemCount());
    }

    /**
     * 是不是头部位置
     */
    private boolean isHeaderPosition(int position) {
        return position < mHeaderViews.size();
    }

    /**
     * 获得item的返回值viewType传入onCreateViewHolder方法的参数viewType中
     *
     * @param position int
     * @return viewType
     */
    @Override
    public int getItemViewType(int position) {
        if (isHeaderPosition(position)) {
            // 直接返回position位置的key
            return mHeaderViews.keyAt(position);
        }
        if (isFooterPosition(position)) {
            // 直接返回position位置的key
            position = position - mHeaderViews.size() - mAdapter.getItemCount();
            return mFooterViews.keyAt(position);
        }
        // 返回列表Adapter的getItemViewType
        position = position - mHeaderViews.size();
        return mAdapter.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mAdapter.getItemCount() + mHeaderViews.size() + mFooterViews.size();
    }

    public void addHeaderView(View view) {
        int position = mHeaderViews.indexOfValue(view);
        if (position < 0) {
            mHeaderViews.put(BASE_ITEM_TYPE_HEADER++, view);
        }
        notifyDataSetChanged();
    }

    public void addFooterView(View view) {
        int position = mFooterViews.indexOfValue(view);
        if (position < 0) {
            mFooterViews.put(BASE_ITEM_TYPE_FOOTER++, view);
        }
        notifyDataSetChanged();
    }

    public void removeHeaderView(View view) {
        int index = mHeaderViews.indexOfValue(view);
        if (index < 0) {
            return;
        }
        mHeaderViews.removeAt(index);
        notifyDataSetChanged();
    }

    public void removeFooterView(View view) {
        int index = mFooterViews.indexOfValue(view);
        if (index < 0) {
            return;
        }
        mFooterViews.removeAt(index);
        notifyDataSetChanged();
    }

    /**
     * 解决GridLayoutManager添加头部和底部不占用一行的问题
     *
     * @param recycler
     */
    public void adjustSpanSize(RecyclerView recycler) {
        if (recycler.getLayoutManager() instanceof GridLayoutManager) {
            final GridLayoutManager layoutManager = (GridLayoutManager) recycler.getLayoutManager();
            layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    boolean isHeaderOrFooter =
                            isHeaderPosition(position) || isFooterPosition(position);
                    return isHeaderOrFooter ? layoutManager.getSpanCount() : 1;
                }
            });
        }
    }
}
