package com.peidou.customerservicec;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.hyphenate.easeui.ui.EaseChatFragment;

/**
 * <pre>
 *      作者  ：肖坤
 *      时间  ：2018/10/10
 *      描述  ：
 *      版本  ：1.0
 * </pre>
 */
public class ChatActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        EaseChatFragment chatFragment = new EaseChatFragment();
        chatFragment.setArguments(getIntent().getExtras());
        int commit = getSupportFragmentManager().beginTransaction().add(R.id.container_fl, chatFragment).commit();
    }
}
