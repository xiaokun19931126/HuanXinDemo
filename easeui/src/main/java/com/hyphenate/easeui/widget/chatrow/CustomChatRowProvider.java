package com.hyphenate.easeui.widget.chatrow;

import android.widget.BaseAdapter;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.widget.presenter.EaseChatCommodityPresenter;
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
        if (message.getType() == EMMessage.Type.TXT) {
            if (message.getBooleanAttribute(EaseConstant.MESSAGE_ATTR_IS_COMMODITY, false)) {
                return message.direct() == EMMessage.Direct.RECEIVE ? EaseConstant.MESSAGE_TYPE_RECV_COMMODITY : EaseConstant.MESSAGE_TYPE_SENT_COMMODITY;
            }
        }
        return 0;
    }

    @Override
    public EaseChatRowPresenter getCustomChatRow(EMMessage message, int position, BaseAdapter adapter) {
        if (message.getType() == EMMessage.Type.TXT) {
            // 商品类型消息
            if (message.getBooleanAttribute(EaseConstant.MESSAGE_ATTR_IS_COMMODITY, false)) {
                //生成一个商品chatPresenter
                return new EaseChatCommodityPresenter();
            }
        }
        return null;
    }
}
