package com.example.newsapitest.utils

import com.example.newsapitest.model.Article

interface Callback {
    fun onItemClickListener(data:Article)
}