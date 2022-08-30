package com.app.newsapplication.ui.newsdetails

import android.graphics.Bitmap
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.app.newsapplication.data.model.NewsDataDetails

@Composable
fun NewsDetailScreen(
    navController: NavController,
    newsUrl: String
){
    val url = remember { mutableStateOf(newsUrl)}
    val visibility = remember { mutableStateOf(false)}
    val progress = remember { mutableStateOf(0.0F)}

    Box{
        AndroidView(factory = { context ->
            WebView(context).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
                )
                settings.javaScriptEnabled = true

                webViewClient = object: WebViewClient(){
                    override fun onPageStarted(
                        view: WebView, url: String,
                        favicon: Bitmap?) {
                        visibility.value = true
                    }

                    override fun onPageFinished(
                        view: WebView, url: String) {
                        visibility.value = false
                    }
                }

                // Set web view chrome client
                webChromeClient = object: WebChromeClient(){
                    override fun onProgressChanged(
                        view: WebView, newProgress: Int) {
                        progress.value = newProgress.toFloat()
                    }
                }

                loadUrl(url.value)
            }
        },update = {
            it.loadUrl(url.value)
        })
    }
}