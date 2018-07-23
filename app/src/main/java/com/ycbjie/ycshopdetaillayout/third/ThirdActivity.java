package com.ycbjie.ycshopdetaillayout.third;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.ycbjie.ycshopdetaillayout.R;
import com.ycbjie.ycshopdetaillayout.first.ShopMainFragment;
import com.ycbjie.ycshopdetaillayoutlib.SlideDetailsLayout;

/**
 * Created by yc on 2018/7/23.
 */

public class ThirdActivity extends AppCompatActivity {

    private SlideDetailsLayout mSlideDetailsLayout;
    private ShopMainFragment shopMainFragment;
    private WebView webView;
    private LinearLayout mLlDetail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        initView();
        initShopMainFragment();
        initSlideDetailsLayout();
        initWebView();
    }

    private void initView() {
        mSlideDetailsLayout = findViewById(R.id.slideDetailsLayout);
        webView = findViewById(R.id.wb_view);
        mLlDetail = findViewById(R.id.ll_detail);
    }


    private void initShopMainFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        if(shopMainFragment==null){
            shopMainFragment = new ShopMainFragment();
            fragmentTransaction
                    .replace(R.id.fl_shop_main, shopMainFragment)
                    .commit();
        }else {
            fragmentTransaction.show(shopMainFragment);
        }
    }

    private void initSlideDetailsLayout() {
        mSlideDetailsLayout.setOnSlideDetailsListener(new SlideDetailsLayout.OnSlideDetailsListener() {
            @Override
            public void onStatusChanged(SlideDetailsLayout.Status status) {
                if (status == SlideDetailsLayout.Status.OPEN) {
                    //当前为图文详情页
                    Log.e("FirstActivity","图文详情页");
                    shopMainFragment.changBottomView(true);
                } else {
                    //当前为商品详情页
                    Log.e("FirstActivity","商品详情页");
                    shopMainFragment.changBottomView(false);
                }
            }
        });
    }


    @SuppressLint({"ObsoleteSdkInt", "SetJavaScriptEnabled"})
    private void initWebView() {
        final WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setUseWideViewPort(true);
        settings.setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR_MR1) {
            new Object() {
                void setLoadWithOverviewMode(boolean overview) {
                    settings.setLoadWithOverviewMode(overview);
                }
            }.setLoadWithOverviewMode(true);
        }

        settings.setCacheMode(WebSettings.LOAD_DEFAULT);

        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl("https://www.jianshu.com/p/d745ea0cb5bd");
            }
        });
    }


}