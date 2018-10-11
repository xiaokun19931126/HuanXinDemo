package com.hyphenate.easeui.widget.chatrow;

import android.widget.BaseAdapter;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.widget.presenter.EaseChatRowPresenter;

/**
 * <pre>
 *      作者  ：肖坤
 *      时间  ：2018/10/11
 *      描述  ：
 *      版本  ：1.0
 * </pre>
 */
public class CustomChatRowProvider implements EaseCustomChatRowProvider {
    @Override
    public int getCustomChatRowTypeCount() {
        return 2;
    }

    @Override
    public int getCustomChatRowType(EMMessage message) {
        return 0;
    }

    @Override
    public EaseChatRowPresenter getCustomChatRow(EMMessage message, int position, BaseAdapter adapter) {
        return null;
    }
}
