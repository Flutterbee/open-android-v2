/*
   Copyright 2014 Citrus Payment Solutions Pvt. Ltd.
   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
     http://www.apache.org/licenses/LICENSE-2.0
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package com.citrus.sample;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.citruspay.sample.R;

public class WebPage extends Activity {
    WebView webView;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_page);

        String url = getIntent().getStringExtra("url");

        webView = (WebView) this.findViewById(R.id.webview);

        webView.getSettings().setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        webView.addJavascriptInterface(new JsInterface(), "CitrusResponse");

        webView.setWebViewClient(new WebViewClient() {
                                     @Override
                                     public boolean shouldOverrideUrlLoading(WebView view, String url) {

                                         view.loadUrl(url);

                                         return false;
                                     }

                                    // Implement this method, omitting this will cause errors on Bank's pages and thus result in poor success rate.
                                     @Override
                                     public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                                         super.onReceivedSslError(view, handler, error);
                                         handler.proceed();
                                     }
                                 }

        );

        // Omitting this will cause errors on Bank's pages and thus result in poor success rate.
        webView.setWebChromeClient(new WebChromeClient());

        webView.loadUrl(url);
    }


    private class JsInterface {

        @JavascriptInterface
        public void pgResponse(String response) {
            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
        }
    }
}