package com.example.newsapitest.ui.adapers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapitest.databinding.RecyclerRowBinding
import com.example.newsapitest.model.Article
import com.example.newsapitest.utils.Callback

class NewsAdapter(val callBack:Callback) : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {

    private val differCallBack = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding,callBack)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.bind(article)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class ArticleViewHolder(private val binding: RecyclerRowBinding,val callBack: Callback) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            Glide.with(binding.ivArticleImage.context).load(article.urlToImage)
                .into(binding.ivArticleImage)
            article.source?.let { binding.tvSource.text = it.name }
            binding.tvTitle.text = article.title
            binding.tvDescription.text = article.description
            binding.tvPublishedAt.text = article.publishedAt
            binding.root.setOnClickListener {
                callBack.onItemClickListener(article)
            }
        }

    }

}