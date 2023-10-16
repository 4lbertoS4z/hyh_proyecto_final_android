package com.example.healthytaste.presentation.fragment

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.healthytaste.data.common.KCAL_CALCULATOR
import com.example.healthytaste.databinding.FragmentKcalCalculatorBinding


class KcalCalculatorFragment : Fragment() {
    private var _binding: FragmentKcalCalculatorBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("FragmentKcalBinding is not available.")


    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentKcalCalculatorBinding.inflate(inflater, container, false)
        val root: View = binding.root

        swipeRefresh()
        //webView

        binding.webView.webChromeClient = object : WebChromeClient() {

        }
        webClient()


        val settings = binding.webView.settings
        settings.javaScriptEnabled = true

        binding.webView.settings.domStorageEnabled = true
        binding.webView.loadUrl(KCAL_CALCULATOR)



        return root
    }

    private fun webClient() {
        binding.webView.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return false
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                binding.swipeRefresh.isRefreshing = true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                binding.swipeRefresh.isRefreshing = false
            }

        }
    }

    private fun swipeRefresh(){
    binding.swipeRefresh.setOnRefreshListener {
        binding.webView.reload()
    }
}

}
