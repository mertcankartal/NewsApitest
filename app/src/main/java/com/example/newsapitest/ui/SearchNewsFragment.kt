package com.example.newsapitest.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapitest.R
import com.example.newsapitest.databinding.FragmentSearchNewsBinding
import com.example.newsapitest.ui.adapers.NewsAdapter
import com.example.newsapitest.ui.viewmodel.NewsViewModel
import com.example.newsapitest.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchNewsFragment : Fragment() {

    private var _binding: FragmentSearchNewsBinding? = null
    private val binding get() = _binding

    val newViewModel : NewsViewModel by viewModels()
    lateinit var searchNewsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchNewsBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        var job: Job? = null
        binding?.etSearch?.addTextChangedListener { editText ->
            job?.cancel()
            job = MainScope().launch {
                delay(500L)
                editText?.let {
                    if (editText.toString().isNotEmpty()){
                        newViewModel.searchNews(editText.toString())
                    }
                }
            }
        }

        newViewModel.searchedNews.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is Resource.Success ->{
                    response.data?.let {
                        hideProgressBar()
                        searchNewsAdapter.differ.submitList(it.articles)
                    }

                }
                is Resource.Error ->{
                    response.message?.let { message ->
                        Log.d("searchFragmentError",message)
                    }
                }
                is Resource.Loading ->{
                    showProgressBar()
                }
            }
        })
    }


    private fun hideProgressBar(){
        binding?.paginationProgressBar?.visibility = View.INVISIBLE
    }


    private fun showProgressBar(){
        binding?.paginationProgressBar?.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        searchNewsAdapter = NewsAdapter()
        binding?.rvSearchNews?.apply {
            adapter = searchNewsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

}