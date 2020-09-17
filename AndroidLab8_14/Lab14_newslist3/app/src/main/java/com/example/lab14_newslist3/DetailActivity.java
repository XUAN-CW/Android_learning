package com.example.lab14_newslist3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class DetailActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);


        Intent intent = getIntent();//获取 Intent 对象
        String url = intent.getStringExtra(Constants.NEWS_DETAIL_URL_KEY);//根据键找传过来的
        System.out.println(url);

        //获得控件
        WebView webView = (WebView) findViewById(R.id.wv_webview);
        //访问网页
        webView.loadUrl(url);
        //系统默认会通过手机浏览器打开网页，为了能够直接通过WebView显示网页，则必须设置
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //使用WebView加载显示url
                view.loadUrl(url);
                //返回true
                return true;
            }
        });
    }
}
