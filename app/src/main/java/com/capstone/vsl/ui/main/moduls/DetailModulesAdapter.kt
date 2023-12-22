package com.capstone.vsl.ui.main.moduls

import android.app.Activity
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.capstone.vsl.R
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.squareup.picasso.Picasso

class DetailModulesAdapter(
    private val activity: Activity,
    private var title: List<String>,
    private var detail: List<String>,
    private var images: List<String>) :
    RecyclerView.Adapter<DetailModulesAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemTitle: TextView = itemView.findViewById(R.id.tvModulesTitle)
        val itemDetails: TextView = itemView.findViewById(R.id.tvModulesDesc)
        val itemImage: ImageView = itemView.findViewById(R.id.ivModulesDetail)

        init {
            itemImage.setOnClickListener { v: View ->
                val position: Int = adapterPosition
                Toast.makeText(itemView.context, "You clicked it #${position + 1}", Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DetailModulesAdapter.ImageViewHolder {
        return ImageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.modules_items, parent, false))
    }

    override fun onBindViewHolder(holder: DetailModulesAdapter.ImageViewHolder, position: Int) {
        holder.itemTitle.text = title[position]
        holder.itemDetails.text = detail[position]
        val imageUrl = Uri.parse(images[position])
        Log.d("DetailModulesAdapter", "Image URL: $imageUrl")
        GlideToVectorYou.justLoadImage(activity, imageUrl, holder.itemImage)

    }

    override fun getItemCount(): Int {
        return title.size
    }
}