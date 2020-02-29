package com.dc.recyclerviewanalisys.commonadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * RecyclerView通用Adapter
 *
 * @param <T> 数据实体类
 */
public abstract class RecyclerCommonAdapter<T> extends RecyclerView.Adapter<ViewHolder> {

    /**
     * 条目id不一样只能通过参数传递
     */
    private int mLayoutId;

    private List<T> mDatas;

    private Context mContext;

    private MulitiTypeSupport mTypeSupport;

    private ItemClickListener mItemClickListener;

    private ItemLongClickListener mItemLongClickListener;

    public RecyclerCommonAdapter(Context context, List<T> datas, int layoutId) {
        this.mContext = context;
        this.mDatas = datas;
        this.mLayoutId = layoutId;
    }

    public RecyclerCommonAdapter(Context context, List<T> datas, MulitiTypeSupport typeSupport) {
        this(context,datas, -1);
        mTypeSupport = typeSupport;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mTypeSupport != null) {
            mLayoutId = viewType;
        }
        View itemView = LayoutInflater.from(mContext).inflate(mLayoutId, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public int getItemViewType(int position) {
        if (mTypeSupport != null) {
            return mTypeSupport.getLayoutId(mDatas.get(position));
        }
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        convert(holder, mDatas.get(position), position);

        if (mItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(position);
                }
            });
        }

        if (mItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return mItemLongClickListener.onItemLongClick(position);
                }
            });
        }
    }

    /**
     * 绑定数据
     *
     * @param holder ViewHolder
     * @param t 当前位置条目数据
     * @param position 当前位置
     */
    protected abstract void convert(ViewHolder holder, T t, int position);

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    public void setItemLongClickListener(ItemLongClickListener itemLongClickListener) {
        mItemLongClickListener = itemLongClickListener;
    }
}
