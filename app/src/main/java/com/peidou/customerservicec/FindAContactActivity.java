package com.peidou.customerservicec;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.ui.ChatActivity;

import static com.peidou.customerservicec.Constants.ISLOGIN;
import static com.peidou.customerservicec.Constants.USERNAME;

/**
 * <pre>
 *      作者  ：肖坤
 *      时间  ：2018/10/10
 *      描述  ：
 *      版本  ：1.0
 * </pre>
 */
public class FindAContactActivity extends AppCompatActivity implements View.OnClickListener {


    private Button mStartChatBtn;
    private Button mSignOutBtn;
    private TextView mContactEt;
    private String mCurrentUserName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_a_contact);

        initView();
    }

    private void initView() {
        mContactEt = findViewById(R.id.contact_et);
        mStartChatBtn = findViewById(R.id.start_chat_btn);
        mSignOutBtn = findViewById(R.id.sign_out_btn);

        mCurrentUserName = MyApp.getSp().getString(USERNAME, "");
        if (mCurrentUserName.equals("xc")) {
            mStartChatBtn.setText("从商品详情进入");
        } else if (mCurrentUserName.equals("xk")) {
            mStartChatBtn.setText("对xc发起聊天");
        }
        mContactEt.setText("当前用户是" + mCurrentUserName);
        initListener(mSignOutBtn, mStartChatBtn);
    }

    private void initListener(View... views) {
        for (View view : views) {
            view.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_chat_btn:
                startChatActivity();
                break;
            case R.id.sign_out_btn:
                signOut();
                break;
            default:
                break;
        }
    }

    private void startChatActivity() {
        Intent intent = new Intent(this, ChatActivity.class);
        if (mCurrentUserName.equals("xc")) {
            intent.putExtra("userId", "xk");
        } else if (mCurrentUserName.equals("xk")) {
            intent.putExtra("userId", "xc");
        }
        startActivity(intent);
    }

    private void signOut() {
        // 调用sdk的退出登录方法，第一个参数表示是否解绑推送的token，没有使用推送或者被踢都要传false
        EMClient.getInstance().logout(false, new EMCallBack() {
            @Override
            public void onSuccess() {
                MyApp.getSp().edit().putBoolean(ISLOGIN, false).commit();
                Log.i("lzan13", "logout success");
                // 调用退出成功，结束app
                finish();
            }

            @Override
            public void onError(int i, String s) {
                Log.i("lzan13", "logout error " + i + " - " + s);
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }

}
