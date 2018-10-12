package com.peidou.customerservicec;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.widget.chatrow.CustomChatRowProvider;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;

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
        chatFragment.setChatFragmentHelper(new EaseChatFragment.EaseChatFragmentHelper() {
            @Override
            public void onSetMessageAttributes(EMMessage message) {

            }

            @Override
            public void onEnterToChatDetails() {

            }

            @Override
            public void onAvatarClick(String username) {

            }

            @Override
            public void onAvatarLongClick(String username) {

            }

            @Override
            public boolean onMessageBubbleClick(EMMessage message) {
                if (message.getBooleanAttribute(EaseConstant.MESSAGE_ATTR_IS_COMMODITY, false)) {
                    startActivity(new Intent(ChatActivity.this, WebActivity.class));
                }
                return false;
            }

            @Override
            public void onMessageBubbleLongClick(EMMessage message) {

            }

            @Override
            public boolean onExtendMenuItemClick(int itemId, View view) {
                return false;
            }

            @Override
            public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
                return new CustomChatRowProvider();
            }
        });
        chatFragment.setArguments(getIntent().getExtras());
        EMMessage message = EMMessage.createTxtSendMessage("测试", "xk");
        message.setAttribute(EaseConstant.MESSAGE_ATTR_IS_COMMODITY, true);
        EMClient.getInstance().chatManager().sendMessage(message);
        int commit = getSupportFragmentManager().beginTransaction().add(R.id.container_fl, chatFragment).commit();
    }
}
