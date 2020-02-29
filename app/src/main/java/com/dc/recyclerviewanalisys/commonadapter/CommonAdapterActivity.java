package com.dc.recyclerviewanalisys.commonadapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.dc.recyclerviewanalisys.R;

import java.util.ArrayList;
import java.util.List;

public class CommonAdapterActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    private List<ChatData> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_adapter);

        mDatas = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            ChatData chatData;
            if (i % 3 == 0) {
                chatData = new ChatData("自己内容" + i, 1);
            } else {
                chatData = new  ChatData("朋友内容" + i, 0);
            }
            mDatas.add(chatData);
        }

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new RecyclerAdapter(this, mDatas));

    }

    private class RecyclerAdapter extends RecyclerCommonAdapter<ChatData> {


        public RecyclerAdapter(Context context, List<ChatData> datas) {
            super(context, datas, new MulitiTypeSupport<ChatData>() {
                @Override
                public int getLayoutId(ChatData chatData) {
                    if (chatData.isMe == 1) {
                        return R.layout.item_chat_me;
                    }
                    return R.layout.item_chat_friend;
                }
            });
        }

        @Override
        protected void convert(ViewHolder holder, ChatData chatData, int position) {
            holder.setText(R.id.chat_text, chatData.chatContent);
        }
    }

    public class ChatData {

        public String chatContent;

        public int isMe;

        public ChatData(String chatContent, int isMe) {
            this.chatContent = chatContent;
            this.isMe = isMe;
        }
    }
}
