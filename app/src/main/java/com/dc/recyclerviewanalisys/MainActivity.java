package com.dc.recyclerviewanalisys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dc.recyclerviewanalisys.baseUse.BaseUseActivity;
import com.dc.recyclerviewanalisys.commonadapter.CommonAdapterActivity;
import com.dc.recyclerviewanalisys.dragsorting.DragSortingActivity;
import com.dc.recyclerviewanalisys.refreshLoad.RefreshLoadActivity;
import com.dc.recyclerviewanalisys.refreshlayout.RefreshLayoutActivity;
import com.dc.recyclerviewanalisys.wrap.HeaderFooterActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 基本使用
     *
     * @param view View
     */
    public void baseUseClick(View view) {
        Intent intent = new Intent(this, BaseUseActivity.class);
        startActivity(intent);
    }

    public void commonAdapter(View view) {
        Intent intent = new Intent(this, CommonAdapterActivity.class);
        startActivity(intent);
    }

    public void headerFooter(View view) {
        Intent intent = new Intent(this, HeaderFooterActivity.class);
        startActivity(intent);
    }

    public void refreshLoad(View view) {
        Intent intent = new Intent(this, RefreshLoadActivity.class);
        startActivity(intent);
    }

    public void refreshLayout(View view) {
        Intent intent = new Intent(this, RefreshLayoutActivity.class);
        startActivity(intent);
    }

    public void dragSorting(View view) {
        Intent intent = new Intent(this, DragSortingActivity.class);
        startActivity(intent);
    }
}
