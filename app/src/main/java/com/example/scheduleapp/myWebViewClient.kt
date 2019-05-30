package com.example.scheduleapp

import android.os.Message
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.scheduleapp.model.NetworkModel
import com.example.scheduleapp.viewmodel.ClassDataViewModel

class myWebViewClient(val view: View) : WebViewClient() {
    var count = 0

    override fun onPageFinished(view: WebView?, url: String?) {
        if (view == null || url == null) {
            throw NullPointerException()
        }

        if (url.contains("my.waseda.jp/login/login")) {
            view.loadUrl(
                "javascript:var obj = document.getElementById(\"loginForm\");\n" +
                        "obj.method = \"post\";\n" +
                        "obj.action = \"/portal/view/portal-top-view\";\n" +
                        "obj.submit();"
            )
        } else if (url.contains("my.waseda.jp/portal/view/portal-top-view")) {

            view.loadUrl(
                "javascript:var form = document.getElementById(\"leftForm\");\n" +
                        "form.method = \"post\";\n" +
                        "form.target = \"_self\";\n" +
                        "form.action = \"https://my.waseda.jp/portal/view/template-submit?url=https://cnavi.waseda.jp/coursenavi/index3.php&communityId=1&menuId=1106&menuType=2&menuUrl=https://cnavi.waseda.jp/coursenavi/index3.php&type=3&pageServiceMenuId=140029&locationId=&openWindow=2\";\n" +
                        "form.submit();"
            )
        } else if (url.contains("cnavi.waseda.jp/index.php")) {
            //return
            when (count) {
                1 -> {
                    view.loadUrl(
                        "javascript:document.cForm.ControllerParameters.value = \"ZX14SubCon\";\n" +
                                "document.cForm.hidCommunityId.value =\"\";\n" +
                                "document.cForm.hidListMode.value = \"list\";\n" +
                                "document.cForm.target = \"_self\";\n" +
                                "document.cForm.submit();"
                    )
                }
                2 -> {
                    view.loadUrl(
                        "javascript:document.cForm.ControllerParameters.value = \"ZX141SubCon\";\n" +
                                "document.cForm.hidURL.value = \"\";\n" +
                                "document.cForm.hidNewWindowFlg.value = \"1\";\n" +
                                "document.cForm.target=\"_self\";\n" +
                                "document.cForm.submit();"
                    )
                }
                3 -> {
                    view.loadUrl(
                        "javascript:var table = document.evaluate(`//*[@id=\"cSchedule\"]/tbody`, document, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE ,null);\n" +
                                "var row = table.snapshotItem(1).childElementCount - 1;\n" +
                                "var col = table.snapshotItem(1).children[0].childElementCount - 1;\n" +
                                "android.setClassTableSize(row, col);\n" +
                                "\n" +
                                "for(var i = 0; i < row; i++) {\n" +
                                "    for(var j = 0; j < col; j++) {\n" +
                                "        var name = \"\", room = \"\";\n" +
                                "\n" +
                                "        var data = document.evaluate(`//*[@id=\"cSchedule\"]/tbody/tr[\${i + 2}]/td[\${j + 2}]/p`, document, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE ,null);\n" +
                                "        if(data.snapshotLength == 0 || data.snapshotItem(0).childElementCount < 2) {\n" +
                                "            android.setClassDatafromPosition(i, j, \"\", \"\");\n" +
                                "            continue;\n" +
                                "        }\n" +
                                "        name = data.snapshotItem(0).firstElementChild.text.trim();\n" +
                                "        var end = name.lastIndexOf('/');\n" +
                                "        if(end != -1) {\n" +
                                "            name = name.substr(0, end);\n" +
                                "        }\n" +
                                "\n" +
                                "        var text = data.snapshotItem(0).children[1].textContent;\n" +
                                "        var start = text.indexOf('[') + 1, end = text.lastIndexOf(']');\n" +
                                "        room = text.substr(start, end - start);\n" +
                                "        \n" +
                                "        android.setClassDatafromPosition(i, j, name, room);\n" +
                                "        console.log(name + \" \" + room);\n" +
                                "    }\n" +
                                "}"
                    )
                    Navigation.findNavController(view).navigate(R.id.action_web_view_to_class_table)
                    count = 0
                }
            }
            ++count
        }
    }


}

class myWebChromeClient : WebChromeClient() {
}