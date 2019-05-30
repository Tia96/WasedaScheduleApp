package com.example.scheduleapp.view.setting

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment
import com.example.scheduleapp.R
import com.example.scheduleapp.model.NetworkModel
import com.example.scheduleapp.myWebChromeClient
import com.example.scheduleapp.myWebViewClient
import kotlinx.android.synthetic.main.web_view_frag.view.*
import android.webkit.CookieManager


class WebViewFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.web_view_frag, container, false)

        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        if(Build.VERSION.SDK_INT >= 21) {
            Log.d("Hello", "cookie")
            cookieManager.setAcceptThirdPartyCookies(view.web_view, true)
        }


        view.web_view.run {
            webViewClient = myWebViewClient(this)
            webChromeClient = myWebChromeClient()
            settings.setSupportMultipleWindows(false)
            settings.javaScriptEnabled = true

            addJavascriptInterface(NetworkModel, "android")
            Log.d("Hello", settings.userAgentString)
            settings.userAgentString = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36"

            settings.allowUniversalAccessFromFileURLs = true
            settings.allowFileAccessFromFileURLs = true
            settings.domStorageEnabled = true

            loadUrl("javascript:window.open(\"https://my.waseda.jp/login/login\", \"_self\", \"\")")
            //loadUrl("https://www.google.co.jp")
        }

        WebView.setWebContentsDebuggingEnabled(true)
        return view
    }
}