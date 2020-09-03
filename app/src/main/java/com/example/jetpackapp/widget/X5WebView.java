package com.example.jetpackapp.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;

import androidx.annotation.Keep;

import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewExtension;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import static android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW;

@SuppressLint("SetJavaScriptEnabled")
public class X5WebView extends DWebView {

    public X5WebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public X5WebView(Context context) {
        super(context);
        init();
    }

    @Override
    protected void init() {
        super.init();
        this.setWebViewClient(client);
        initWebViewSettings();
        this.getView().setClickable(true);
    }

    @Keep
    private void initWebViewSettings() {
        WebSettings webSetting = this.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setSupportZoom(true);
        webSetting.setTextZoom(100);
        webSetting.setTextSize(WebSettings.TextSize.NORMAL);
        webSetting.setDefaultFixedFontSize(16);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setDisplayZoomControls(false); //不显示webview缩放按钮
        webSetting.setSupportMultipleWindows(true);
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSetting.setBlockNetworkImage(false); // 解决图片不显示
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSetting.setAllowUniversalAccessFromFileURLs(true);
        webSetting.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        webSetting.setUserAgent(webSetting.getUserAgentString() + " fromapp");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSetting.setMixedContentMode(MIXED_CONTENT_ALWAYS_ALLOW);
        }

        String dir = getContext().getApplicationContext().getDir("database",
                Context.MODE_PRIVATE).getPath();
        //启用地理定位
        webSetting.setGeolocationEnabled(true);
        //设置定位的数据库路径
        webSetting.setGeolocationDatabasePath(dir);

        webSetting.setMediaPlaybackRequiresUserGesture(false);
        IX5WebViewExtension x5WebViewExtension = getX5WebViewExtension();
        if(x5WebViewExtension != null){
            getX5WebViewExtension().setScrollBarFadingEnabled(false);
            getX5WebViewExtension().setHorizontalScrollBarEnabled(false);
            getX5WebViewExtension().setVerticalScrollBarEnabled(false);
        }

    }

    private WebViewClient client = new WebViewClient() {
        /**
         * 防止加载网页时调起系统浏览器
         */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }

    };
}
