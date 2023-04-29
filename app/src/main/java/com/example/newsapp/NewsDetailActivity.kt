package com.example.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.newsapp.JsonToKotlin.NewsArticles
import com.example.newsapp.databinding.ActivityNewsAdetailBinding
import com.squareup.picasso.Picasso

class NewsDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsAdetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsAdetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val headlines = intent.getSerializableExtra("data") as NewsArticles

        binding.titleDetail.text = headlines.title
        if (headlines.urlToImage != null){
            Picasso.get().load(headlines.urlToImage).into(binding.imageDetail)
        }
        if (headlines.description != null){
            binding.discriptionDetail.text = headlines.description
        }
        else{
            binding.discriptionDetail.text = "Details not found"
        }
        if (headlines.author != null){
            binding.authorDetail.text = headlines.author
        }
        else{
            binding.authorDetail.text = "Author not found"
        }

        binding.timeDetail.text = headlines.publishedAt

//        Log.d("NewsDetailActivity",headlines.title)
//        Log.d("NewsDetailActivity",headlines.discription)
//        Log.d("NewsDetailActivity",headlines.author)
//        Log.d("NewsDetailActivity",headlines.publishedAt)
//        Log.d("NewsDetailActivity",headlines.content)
//        Log.d("NewsDetailActivity",headlines.urlToImage)
    }
}