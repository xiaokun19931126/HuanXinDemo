package com.peidou.customerservicec;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

/**
 * <pre>
 *      作者  ：肖坤
 *      时间  ：2018/10/12
 *      描述  ：
 *      版本  ：1.0
 * </pre>
 */
public class WebActivity extends AppCompatActivity {


    private WebView webview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        this.webview = findViewById(R.id.web_view);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl("https://www.baidu.com/");
    }
}
