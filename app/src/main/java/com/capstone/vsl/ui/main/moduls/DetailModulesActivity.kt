package com.capstone.vsl.ui.main.moduls

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.capstone.vsl.R
import com.capstone.vsl.data.remote.response.DataAlphaItem
import com.capstone.vsl.databinding.ActivityDetailModulesBinding
import com.capstone.vsl.ui.main.MainActivity
import com.capstone.vsl.ui.main.practice.PracticeFragment
import kotlin.math.abs

class DetailModulesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailModulesBinding

    private var titleList = mutableListOf<String>()
    private var descList = mutableListOf<String>()
    private var imgList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailModulesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dataAlphaItems = intent.getParcelableArrayListExtra<DataAlphaItem>("dataAlphaItems")

        dataAlphaItems?.let {
            for (dataAlphaItem in it) {
                val title = dataAlphaItem?.huruf ?: ""
                val description = dataAlphaItem?.deskripsi ?: ""
                val imageUrl = dataAlphaItem?.imageUrl ?: ""

                addToList(title, description, imageUrl)
            }

            binding.viewPager2.adapter = DetailModulesAdapter(this@DetailModulesActivity, titleList, descList, imgList)
            binding.viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL

            binding.circleIndicator3.setViewPager(binding.viewPager2)
        }
    }

    private fun addToList(title: String, desc: String, img: String) {
        titleList.add(title)
        descList.add(desc)
        imgList.add(img)
    }
}