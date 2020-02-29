package com.dc.recyclerviewanalisys.commonadapter;

/**
 * 多条目布局
 *
 * @param <T> 数据实体类
 */
public interface MulitiTypeSupport<T> {

    int getLayoutId(T t);
}
