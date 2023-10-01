package com.example.healthytaste.presentation.fragment

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
import androidx.lifecycle.ViewModelProvider
import com.example.healthytaste.KCAL_CALCULATOR
import com.example.healthytaste.databinding.FragmentKcalCalculatorBinding
import com.example.healthytaste.presentation.viewModel.KcalCalculatorViewModel


class KcalCalculatorFragment : Fragment() {
    private var _binding: FragmentKcalCalculatorBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("FragmentNewsBinding is not available.")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val homeViewModel =
            ViewModelProvider(this)[KcalCalculatorViewModel::class.java]

        _binding = FragmentKcalCalculatorBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.swipeRefresh.setOnRefreshListener {
            binding.webView.reload()
        }
        //webView

        binding.webView.webChromeClient = object : WebChromeClient() {

        }
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

        val settings = binding.webView.settings
        settings.javaScriptEnabled = true

        binding.webView.settings.domStorageEnabled = true
        binding.webView.loadUrl(KCAL_CALCULATOR)



        return root
    }


}
