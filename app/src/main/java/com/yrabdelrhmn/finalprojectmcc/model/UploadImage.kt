package com.yrabdelrhmn.finalprojectmcc.model

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView


class UploadImage(var fileImageList:MutableList<Int> = arrayListOf(),
                  var context:Activity ) :
    RecyclerView.Adapter<UploadImage.ViewHolder> () {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v: View =
            LayoutInflater.from(context).inflate(R.layout.item_image, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.fileImageView.setImageResource(fileImageList[position])

    }

    override fun getItemCount(): Int {
        return fileImageList.size
    }
    inner class ViewHolder( mView: View) : RecyclerView.ViewHolder(
        mView
    ) {
        var fileImageView: ImageView=mView.findViewById(R.id.item_Image)

    }
}