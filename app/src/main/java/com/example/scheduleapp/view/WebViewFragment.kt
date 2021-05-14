package com.example.scheduleapp.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment
import com.example.scheduleapp.databinding.FragmentWebviewBinding
import com.example.scheduleapp.model.ScheduleModel
class WebViewFragment : Fragment() {

    private lateinit var binding: FragmentWebviewBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentWebviewBinding.inflate(inflater, container, false)
        binding.webview.apply {
            webViewClient = MyWebViewClient()
            settings.setSupportMultipleWindows(false)
            settings.javaScriptEnabled = true

            addJavascriptInterface(ScheduleModel, "android")
            //settings.userAgentString = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36"
            settings.domStorageEnabled = true
            settings.javaScriptCanOpenWindowsAutomatically = true

            loadUrl("https://my.waseda.jp/login/login")
        }

        WebView.setWebContentsDebuggingEnabled(true)
        return binding.root
    }
}