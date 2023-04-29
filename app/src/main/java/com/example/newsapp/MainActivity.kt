package com.example.newsapp

import android.app.ProgressDialog
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.JsonToKotlin.ApiResponse
import com.example.newsapp.JsonToKotlin.NewsArticles
import com.example.newsapp.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import retrofit2.Response

const val API_key = "fd50a8bb0c9b4e63bbf0d6336d44ac09"   // Replace your API key here

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    private lateinit var newsAdapter: NewsAdapter
    private lateinit var newsList : ArrayList<NewsArticles>
    private lateinit var progressDialog : ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        newsList = ArrayList()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Fetching News Articles")

        if (isConnected()){
            getResponse()
        }
        else{
            Toast.makeText(this,"Your not connected to the Internet",Toast.LENGTH_SHORT).show()
        }


        binding.searchQuery.setOnQueryTextListener(object :
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(myquery: String?): Boolean {
                getResponse(query = myquery)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText == ""){
                    getResponse()
                    return true
                }
                return false
            }
        })

        binding.catGeneral.setOnClickListener {
            getResponse()
            binding.catGeneral.visibility = View.GONE
        }

        binding.catBusiness.setOnClickListener {
            getResponse(category = "business")
            binding.catGeneral.visibility = View.VISIBLE
        }

        binding.catScience.setOnClickListener {
            getResponse(category = "science")
            binding.catGeneral.visibility = View.VISIBLE
        }

        binding.catSports.setOnClickListener {
            getResponse(category = "sports")
            binding.catGeneral.visibility = View.VISIBLE
        }

        binding.catEntertainment.setOnClickListener {
            getResponse(category = "entertainment")
            binding.catGeneral.visibility = View.VISIBLE
        }
        binding.catTechnology.setOnClickListener {
            getResponse(category = "technology")
            binding.catGeneral.visibility = View.VISIBLE
        }

        binding.catHealth.setOnClickListener {
            getResponse(category = "health")
            binding.catGeneral.visibility = View.VISIBLE
        }


        newsAdapter = NewsAdapter(newsList)

        binding.newRCV.adapter = newsAdapter
        binding.newRCV.layoutManager = GridLayoutManager(this,1)
        binding.newRCV.setHasFixedSize(true)
    }

    private fun getResponse(country: String = "us", category : String = "general", query : String? = null){
//        if (fetchCacheData(category)){
//            Log.d("MainActivity","fetch data body executed")
//            return
//        }

        val newsAPI = ApiClient.getApiInstance().create(ApiCall::class.java)
        GlobalScope.launch {
            withContext(Dispatchers.Main){
                progressDialog.show()
            }
            val response =  newsAPI.getTopHeadlines(country,category,query, API_key)
            if (response.isSuccessful){
//                Log.d("MainActivity","Response on query is successful")
                val newsListSize = response.body()!!.articles.size
                if (newsListSize == 0){
                    withContext(Dispatchers.Main){
                        Toast.makeText(this@MainActivity,"No Results Found",Toast.LENGTH_SHORT).show()
                    }
                }
                newsAdapter.newsList.clear()
                for (i in 0 until newsListSize){
                    newsList.add(response.body()!!.articles[i])
//                    Log.d("MainActivity",response.body()!!.articles[i].title)
                }

            }
            else if (!response.isSuccessful){
                Log.d("MainActivity","response failed")
            }
            withContext(Dispatchers.Main){
//                Log.d("MainActivity","Updating recycler view")
                newsAdapter.notifyDataSetChanged()
//                cacheData(category,newsList)
                progressDialog.dismiss()
            }
        }

    }

    private fun isConnected() : Boolean{
        var connected : Boolean = false
        try {
            val connectivityManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            connected = networkInfo?.isConnectedOrConnecting == true
            Log.d("MainActivity",connected.toString())
            return connected
        }catch (e : Exception){
            Toast.makeText(this,"Your not connected to the Internet",Toast.LENGTH_SHORT).show()
        }

        return connected
    }

    private fun cacheData(category : String, articles: ArrayList<NewsArticles>){
        ArticlesCache.cacheMap[category] = articles
        val arr = ArticlesCache.cacheMap[category]
        for (i in 0 until arr!!.size){
            Log.d("MainActivity","cache"+arr[i].title)
        }
    }

    private fun fetchCacheData(category: String) : Boolean{
        if (ArticlesCache.cacheMap.contains(category)){
            newsList  = ArrayList(ArticlesCache.cacheMap[category]!!)
            for (i in 0 until newsList.size){
                Log.d("MainActivity","fetch"+newsList[i].title)
            }
            newsAdapter.notifyDataSetChanged()
            return true
        }
        return false
    }
}