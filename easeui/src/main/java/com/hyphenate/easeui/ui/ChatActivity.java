package com.hyphenate.easeui.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.widget.chatrow.CustomChatRowProvider;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;

import static android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN;

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

        final EaseChatFragment chatFragment = new EaseChatFragment();
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
            public void onMessageBubbleLongClick(View voiceToTextView, View v, EMMessage message, int position) {
                if (message.getType() == EMMessage.Type.VOICE) {
                    chatFragment.expandTextPopWindow(voiceToTextView, v, "转文字", position);
                }
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
        EMMessage message = EMMessage.createTxtSendMessage("商品详情消息类型", "xk");
        message.setAttribute(EaseConstant.MESSAGE_ATTR_IS_COMMODITY, true);
        EMClient.getInstance().chatManager().sendMessage(message);

        addFragment(chatFragment);
    }

    public void addFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setTransition(TRANSIT_FRAGMENT_OPEN);
        ft.add(R.id.container_fl, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        //当fragment栈只剩下一个时,返回键直接finish当前activity
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
            return;
        }
        super.onBackPressed();
    }
}
