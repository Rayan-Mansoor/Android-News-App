package com.example.newsapp

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.JsonToKotlin.NewsArticles
import com.squareup.picasso.Picasso

class NewsAdapter(val newsList : ArrayList<NewsArticles>) : RecyclerView.Adapter<NewsAdapter.NewViewHolder>() {

    class NewViewHolder(ItemView : View) : RecyclerView.ViewHolder(ItemView) {
        val newsTitle = ItemView.findViewById<TextView>(R.id.newsTitle)
        val newsSource = ItemView.findViewById<TextView>(R.id.newsSource)
        val newsImage = ItemView.findViewById<ImageView>(R.id.newsImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewViewHolder {
        Log.d("NewsAdapter","on create called")
        return  NewViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.news_item,parent,false))
    }

    override fun onBindViewHolder(holder: NewViewHolder, position: Int) {
        holder.newsTitle.text = newsList[position].title
        holder.newsSource.text = newsList[position].source.name
        if (newsList[position].urlToImage != null){
            Picasso.get().load(newsList[position].urlToImage).into(holder.newsImage)
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context,NewsDetailActivity::class.java)
            intent.putExtra("data",newsList[position])
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return newsList.size
    }
}