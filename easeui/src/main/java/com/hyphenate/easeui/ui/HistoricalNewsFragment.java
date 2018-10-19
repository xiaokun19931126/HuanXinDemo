package com.hyphenate.easeui.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.widget.EaseChatMessageList;
import com.hyphenate.easeui.widget.EaseTitleBar;
import com.hyphenate.easeui.widget.chatrow.CustomChatRowProvider;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <pre>
 *      作者  ：肖坤
 *      时间  ：2018/10/16
 *      描述  ：历史消息页面
 *      版本  ：1.0
 * </pre>
 */
public class HistoricalNewsFragment extends Fragment {

    private static final String KEY_CHAT_USERNAME = "CHAT_USERNAME";
    private static final String KEY_CHAT_TYPE = "CHAT_TYPE";
    private EaseTitleBar mHistoryTitleBar;
    private EaseChatMessageList mHistoryMessageList;
    private ExecutorService fetchQueue;
    private int pagesize = 20;
    private String mChatUserName;
    private int mChatType;
    private EMConversation conversation;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private boolean isloading;
    private boolean haveMoreData = true;

    public static HistoricalNewsFragment newInstance(String chatUserName, int chatType) {
        Bundle args = new Bundle();
        args.putInt(KEY_CHAT_TYPE, chatType);
        args.putString(KEY_CHAT_USERNAME, chatUserName);
        HistoricalNewsFragment fragment = new HistoricalNewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_historical_news, container, false);
        Bundle arguments = getArguments();
        mChatUserName = arguments.getString(KEY_CHAT_USERNAME, "");
        mChatType = arguments.getInt(KEY_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        conversation = EMClient.getInstance().chatManager().getConversation(mChatUserName, EaseCommonUtils.getConversationType(mChatType), true);
        initView(contentView);
        return contentView;
    }


    private void initView(View view) {
        mHistoryTitleBar = view.findViewById(R.id.history_title_bar);
        mHistoryMessageList = view.findViewById(R.id.history_message_list);

        listView = mHistoryMessageList.getListView();

        mHistoryTitleBar.setTitle("历史消息");
        mHistoryTitleBar.setTitleColor(getContext().getResources().getColor(R.color.color_222222));
        mHistoryTitleBar.setTitleSize(18);

        mHistoryTitleBar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ChatActivity) getActivity()).onBackPressed();
            }
        });

        swipeRefreshLayout = mHistoryMessageList.getSwipeRefreshLayout();
        swipeRefreshLayout.setColorSchemeResources(R.color.holo_blue_bright, R.color.holo_green_light,
                R.color.holo_orange_light, R.color.holo_red_light);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadHistoryMessages();
            }
        });

        fetchQueue = Executors.newSingleThreadExecutor();
        loadHistoryMessages();
        mHistoryMessageList.init(mChatUserName, mChatType, new CustomChatRowProvider());
    }

    private void loadHistoryMessages() {
        if (!haveMoreData) {
            Toast.makeText(getActivity(), getResources().getString(R.string.no_more_messages),
                    Toast.LENGTH_SHORT).show();
            swipeRefreshLayout.setRefreshing(false);
            return;
        }
        fetchQueue.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    List<EMMessage> messages = conversation.getAllMessages();
                    EMClient.getInstance().chatManager().fetchHistoryMessages(
                            mChatUserName, EaseCommonUtils.getConversationType(mChatType), pagesize,
                            (messages != null && messages.size() > 0) ? messages.get(0).getMsgId() : "");
                } catch (HyphenateException e) {
                    e.printStackTrace();
                } finally {
                    Activity activity = getActivity();
                    if (activity != null) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                loadMoreLocalMessage();
                            }
                        });
                    }
                }
            }
        });
    }

    private void loadMoreLocalMessage() {
        if (listView.getFirstVisiblePosition() == 0 && !isloading && haveMoreData) {
            List<EMMessage> messages;
            try {
                messages = conversation.loadMoreMsgFromDB(conversation.getAllMessages().size() == 0 ? "" : conversation.getAllMessages().get(0).getMsgId(),
                        pagesize);
            } catch (Exception e1) {
                swipeRefreshLayout.setRefreshing(false);
                return;
            }
            if (messages.size() > 0) {
                mHistoryMessageList.refreshSeekTo(messages.size() - 1);
                if (messages.size() != pagesize) {
                    haveMoreData = false;
                }
            } else {
                haveMoreData = false;
            }

            isloading = false;
        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.no_more_messages),
                    Toast.LENGTH_SHORT).show();
        }
        swipeRefreshLayout.setRefreshing(false);
    }
}
