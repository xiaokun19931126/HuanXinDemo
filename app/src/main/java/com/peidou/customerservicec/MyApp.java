package com.peidou.customerservicec;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;

/**
 * <pre>
 *      作者  ：肖坤
 *      时间  ：2018/10/10
 *      描述  ：
 *      版本  ：1.0
 * </pre>
 */
public class MyApp extends Application {

    private static final String TAG = "MyApp";
    private static SharedPreferences mSp;

    @Override
    public void onCreate() {
        super.onCreate();
        mSp = getSharedPreferences("data", MODE_PRIVATE);
        //初始化环信
        initHx();
    }

    public static SharedPreferences getSp() {
        return mSp;
    }

    /**
     * 初始化环信
     */
    private void initHx() {
        EMOptions options = initChatOptions();
        EaseUI.getInstance().init(this, options);
    }

    private EMOptions initChatOptions() {
        Log.d(TAG, "init HuanXin Options");

        EMOptions options = new EMOptions();
        // 设置Appkey，如果配置文件已经配置，这里可以不用设置
        options.setAppKey("1146181010177839#hxtext");
        // 设置自动登录
        options.setAutoLogin(true);
        // 设置是否需要发送已读回执
        options.setRequireAck(true);
        // 设置是否需要发送回执，
        options.setRequireDeliveryAck(true);
        // 设置是否根据服务器时间排序，默认是true
        options.setSortMessageByServerTime(false);
        // 收到好友申请是否自动同意，如果是自动同意就不会收到好友请求的回调，因为sdk会自动处理，默认为true
        options.setAcceptInvitationAlways(false);
        // 设置是否自动接收加群邀请，如果设置了当收到群邀请会自动同意加入
        options.setAutoAcceptGroupInvitation(false);
        // 设置（主动或被动）退出群组时，是否删除群聊聊天记录
        options.setDeleteMessagesAsExitGroup(false);
        // 设置是否允许聊天室的Owner 离开并删除聊天室的会话
        options.allowChatroomOwnerLeave(true);
        // 设置google GCM推送id，国内可以不用设置
        // options.setGCMNumber(MLConstants.ML_GCM_NUMBER);
        // 设置集成小米推送的appid和appkey
        // options.setMipushConfig(MLConstants.ML_MI_APP_ID, MLConstants.ML_MI_APP_KEY);
        return options;
    }


}
