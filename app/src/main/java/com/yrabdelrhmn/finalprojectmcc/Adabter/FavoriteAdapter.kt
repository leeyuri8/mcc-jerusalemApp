package com.yrabdelrhmn.finalprojectmcc.Adabter


import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yrabdelrhmn.finalprojectmcc.DBHelper
import com.yrabdelrhmn.finalprojectmcc.FavItem
import com.yrabdelrhmn.finalprojectmcc.R
import kotlinx.android.synthetic.main.favorite_item.view.*


class FavoriteAdapter( var context: Context,  var data: ArrayList<FavItem>) :
    RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {
    private var favDB: DBHelper? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.favorite_item,
            parent, false
        )
        favDB = DBHelper(context)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data[position])

    }

    override fun getItemCount(): Int {
        return data.size
    }

     class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         @SuppressLint("SetTextI18n")
         fun bindItem(model: FavItem){
             itemView.favTextView.text = "${model.key_id}. ${model.item_title}"
             itemView.favImageView.setImageResource(model.item_image)
         }



    }


}