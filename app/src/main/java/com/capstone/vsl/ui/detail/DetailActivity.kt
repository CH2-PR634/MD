package com.capstone.vsl.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.vsl.R
import com.capstone.vsl.data.news.News
import com.capstone.vsl.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val news = intent.getParcelableExtra<News>(EXTRA_NEWS)

        news?.let {
            binding.ivDetail.setImageResource(it.photo)
            binding.tvTitle.text = it.name
            binding.tvDesc.text  = it.desc
        }
    }


    companion object {
        const val EXTRA_NEWS = "extra_news"
    }
}