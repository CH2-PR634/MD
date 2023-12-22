package com.capstone.vsl.ui.main.news

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.vsl.R
import com.capstone.vsl.data.news.News
import com.capstone.vsl.ui.detail.DetailActivity


class NewsFragment : Fragment() {


    private lateinit var tvQuotes: TextView
    private lateinit var rvNews: RecyclerView
    private val newsList = ArrayList<News>()

    private val quotes: Array<String> by lazy {
        resources.getStringArray(R.array.quotes)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news, container, false)

        rvNews = view.findViewById(R.id.rvNews)
        rvNews.layoutManager = LinearLayoutManager(requireContext())
        rvNews.setHasFixedSize(true)

        newsList.addAll(getNewsList())

        showRecyclerView()

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvQuotes = view.findViewById<TextView>(R.id.tvNewsQuotes)
        val randomIndex = quotes.indices.random()
        val randomQuotes = quotes[randomIndex]
        tvQuotes.text = randomQuotes
    }

    private fun getNewsList(): ArrayList<News> {
        val dataTitles = resources.getStringArray(R.array.data_name)
        val dataImages = resources.obtainTypedArray(R.array.data_photo)
        val dataDesc = resources.getStringArray(R.array.data_description)

        val listNews = ArrayList<News>()
        for (i in dataTitles.indices) {
            val newsItem = News(dataTitles[i], dataImages.getResourceId(i, -1), dataDesc[i])
            listNews.add(newsItem)
        }
        dataImages.recycle()

        return listNews
    }

    private fun showRecyclerView() {
        val newsAdapter = ListNewsAdapter(newsList, object : ListNewsAdapter.OnItemClickCallback {
            override fun onItemClicked(news: News) {
                showSelectedNews(news)
            }
        })
        rvNews.adapter = newsAdapter
    }

    private fun showSelectedNews(news: News) {
        val intent = Intent(requireContext(), DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_NEWS, news)
        startActivity(intent)
    }
}