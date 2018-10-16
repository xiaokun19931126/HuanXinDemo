package com.hyphenate.easeui.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hyphenate.easeui.R;
import com.hyphenate.easeui.widget.EaseChatMessageList;
import com.hyphenate.easeui.widget.EaseTitleBar;

/**
 * <pre>
 *      作者  ：肖坤
 *      时间  ：2018/10/16
 *      描述  ：历史消息页面
 *      版本  ：1.0
 * </pre>
 */
public class HistoricalNewsFragment extends Fragment {


    private EaseTitleBar mHistoryTitleBar;
    private EaseChatMessageList mHistoryMessageList;

    public static HistoricalNewsFragment newInstance() {
        Bundle args = new Bundle();
        HistoricalNewsFragment fragment = new HistoricalNewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_historical_news, container, false);
        initView(contentView);
        return contentView;
    }


    private void initView(View view) {
        mHistoryTitleBar = view.findViewById(R.id.history_title_bar);
        mHistoryMessageList = view.findViewById(R.id.history_message_list);

        mHistoryTitleBar.setTitle("历史消息");
        mHistoryTitleBar.setTitleColor(getContext().getResources().getColor(R.color.color_222222));
        mHistoryTitleBar.setTitleSize(18);

        mHistoryTitleBar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ChatActivity) getActivity()).onBackPressed();
            }
        });
    }
}
