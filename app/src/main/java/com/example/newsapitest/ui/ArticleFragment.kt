package com.example.newsapitest.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.newsapitest.R
import com.example.newsapitest.databinding.FragmentArticleBinding
import com.example.newsapitest.ui.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleFragment : Fragment() {

    private var _binding : FragmentArticleBinding? = null
    private val binding get() = _binding

    val args : ArticleFragmentArgs by navArgs()

    val newViewModel: NewsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArticleBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val article = args.article

        binding?.webView?.apply {
            webViewClient = WebViewClient()
            article.url?.let { loadUrl(it) }
        }

        binding?.fab?.setOnClickListener {
            newViewModel.saveArticle(article)
            Snackbar.make(view,"Article Saved",Snackbar.LENGTH_SHORT).show()
        }
    }
}