package com.example.scheduleapp.view

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.navigation.Navigation
import com.example.scheduleapp.R


class MyWebViewClient : WebViewClient() {
    override fun onPageFinished(view: WebView, url: String) {
        when {
            url.contains("my.waseda.jp/login/login") -> view.loadUrl("https://coursereg.waseda.jp/portal/simpleportal.php")
            url.contains("coursereg.waseda.jp/portal/simpleportal.php") -> view.loadUrl("javascript: document.querySelector('body > p > table:nth-child(2) > tbody > tr:nth-child(3) > td:nth-child(2) > a').click()")
            url.contains("wcrs.waseda.jp/kyomu") -> {
                view.loadUrl(
                    "javascript: contents = document.querySelector('body > table:nth-child(5) > tbody > tr > td > table > tbody > tr:nth-child(7) > td > table:nth-child(3) > tbody > tr > td > table:nth-child(3) > tbody');\n" +
                            "\n" +
                            "for (let i = 1; i < contents.children.length; ++i) {\n" +
                            "\tlet child = contents.children[i];\n" +
                            "        let classData = {Semester: child.children[0].innerText, WeekDay: child.children[1].innerText, thPeriod: String.fromCharCode(child.children[2].innerText.charCodeAt(0)-0xFEE0), Name: child.children[5].innerText, Teachers: child.children[6].innerText, Room: child.children[8].innerText};\n" +
                            "        console.log(JSON.stringify(classData));\n" +
                            "\tandroid.setDataFromJson(JSON.stringify(classData))\n" +
                            "}"
                )
                Navigation.findNavController(view).navigate(R.id.action_navigation_webview_to_navigation_schedule_table)
            }
        }
    }
}