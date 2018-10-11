package com.hyphenate.easeui.widget.presenter;

import android.content.Context;
import android.widget.BaseAdapter;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;
import com.hyphenate.easeui.widget.chatrow.EaseChatRowCommodity;

/**
 * <pre>
 *      作者  ：肖坤
 *      时间  ：2018/10/11
 *      描述  ：
 *      版本  ：1.0
 * </pre>
 */
public class EaseChatCommodityPresenter extends EaseChatRowPresenter {
    @Override
    protected EaseChatRow onCreateChatRow(Context cxt, EMMessage message, int position, BaseAdapter adapter) {
        return new EaseChatRowCommodity(cxt, message, position, adapter);
    }
}
