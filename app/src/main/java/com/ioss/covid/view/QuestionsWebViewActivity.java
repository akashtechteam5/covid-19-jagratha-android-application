package com.ioss.covid.view;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.ioss.covid.R;
import com.ioss.covid.core.CoreActivity;
import com.ioss.covid.utilities.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class QuestionsWebViewActivity extends CoreActivity {

    private WebView webView;
    private String url;
    private String successUrl=Constants.URL+"questionnaire/index";
    private String failureUrl="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_web_view);

        getSupportActionBar().setTitle(getString(R.string.app_name));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        url = intent.getStringExtra("url");


        initViews();
    }

    @Override
    protected void initViews() {
        super.initViews();

        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl(url);

        showProgress();
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                Constants.makeLog("url : "+url);

                if (url.toLowerCase().equalsIgnoreCase(successUrl.toLowerCase())) {
                    Handler handler = new Handler();

                    handler.postDelayed(new Runnable() {
                        public void run() {
                            finish();
                        }
                    }, 1000);
                }

//                if (url.toLowerCase().contains(successUrl.toLowerCase())) {
//
//                }

//                if (url.toLowerCase().contains(failureUrl.toLowerCase())) {
//                    triggerFailure();
//                }
            }

            @Override
            public void onPageFinished(WebView view, String _url) {
                super.onPageFinished(view, url);
                hideProgress();
            }
        });
    }

    private void triggerFailure() {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED,intent);
        finish();
    }

    private void triggerSuccess() {

                setResult(RESULT_OK);
                finish();
    }
}
