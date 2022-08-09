package com.example.newsapitest.model

data class NewsResponse(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)

//mutablelist yapmamızın sebebi .add gibi func ları kullanabilmek için
//list olsaydı kullanamazdık.