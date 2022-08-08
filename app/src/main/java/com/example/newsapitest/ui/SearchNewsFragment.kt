package com.example.newsapitest.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapitest.R
import com.example.newsapitest.databinding.FragmentSearchNewsBinding
import com.example.newsapitest.model.Article
import com.example.newsapitest.ui.adapers.NewsAdapter
import com.example.newsapitest.ui.viewmodel.NewsViewModel
import com.example.newsapitest.utils.Callback
import com.example.newsapitest.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search_news.*


@AndroidEntryPoint
class SearchNewsFragment : Fragment(),Callback {

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

        binding?.etSearch?.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0!!.length >= 4){
                    newViewModel.searchNews(p0.toString())
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })


            binding?.etSearch?.setOnEditorActionListener { v, actionId, event ->
                when (actionId) {
                    EditorInfo.IME_ACTION_NEXT -> newViewModel.searchNews(v.toString())
                }
                false
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
        searchNewsAdapter = NewsAdapter(this)
        binding?.rvSearchNews?.apply {
            adapter = searchNewsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    override fun onItemClickListener(data: Article) {
        val bundle = Bundle().apply {
            putParcelable("articles",data)
        }
        findNavController().navigate(R.id.action_searchNewsFragment_to_articleFragment,bundle)

    }

}