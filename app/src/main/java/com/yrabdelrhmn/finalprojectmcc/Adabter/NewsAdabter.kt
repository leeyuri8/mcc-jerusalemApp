package com.example.cloud_project.Adabter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cloud_project.DetailsNews
import com.example.cloud_project.R
import com.yrabdelrhmn.finalprojectmcc.model.DataNews



class NewsAdabter (var activity: Context, var dataNews : MutableList<DataNews>):
    RecyclerView.Adapter<NewsAdabter.MyViewHolder>() {



    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val newsTitel = itemView.findViewById<TextView>(R.id.item_News_titel)
        val newsImage = itemView.findViewById<ImageView>(R.id.item_News_Image)
        val newsDescription = itemView.findViewById<TextView>(R.id.item_News_Description)
        val newsDate = itemView.findViewById<TextView>(R.id.item_News_Date)



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(activity).inflate(R.layout.item_news, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
       return dataNews.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       holder.newsTitel.text = dataNews[position].title
       holder.newsDescription.text =dataNews[position].description
       holder.newsDate.text = dataNews[position].publishedAt
        holder.newsDate.setOnClickListener {
            val options= Bundle()
            val intent = Intent(activity,DetailsNews::class.java)
            ContextCompat.startActivity(activity, intent, options)
        }
        Glide.with(activity).load(dataNews[position].urlToImage).into(holder.newsImage)

    }

}