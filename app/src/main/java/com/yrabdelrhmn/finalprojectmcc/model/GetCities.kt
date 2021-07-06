package com.yrabdelrhmn.finalprojectmcc.model

import android.R
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.cloud_project.DetailsCities
import com.example.cloud_project.model.GetCities
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.yrabdelrhmn.finalprojectmcc.Adabter.DBHelper
import com.yrabdelrhmn.finalprojectmcc.FavItem


class GetCities(val context: Context, val  options: FirestoreRecyclerOptions<DataCities>) : FirestoreRecyclerAdapter<DataCities, GetCities.StoreViewHolder>(options) {
    var db: FirebaseFirestore = Firebase.firestore
    var auth: FirebaseAuth = Firebase.auth
     private lateinit var dbHelper : DBHelper
    private lateinit var cityItems : ArrayList<DataCities>


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GetCities.StoreViewHolder {
        val view = LayoutInflater.from(context)
                .inflate(R.layout.item_cities, parent, false)
        return StoreViewHolder(view)
    }

    override fun onBindViewHolder(holder: GetCities.StoreViewHolder, position: Int, model: DataCities) {
        holder.name.text = model.CityName
        val cityitem: DataCities = cityItems[position]
        holder.image.setImageResource(model.CityPictures[1])
        holder.item.setOnClickListener {
            val intent = Intent(context, DetailsCities::class.java)
            intent.putExtra("id", model.id)
            val options = Bundle()
            startActivity(context, intent, options)
        }
        readCursorData(cityitem,holder)



        }



private fun readCursorData(cityItem: DataCities, viewHolder: RecyclerView.ViewHolder) {
    val cursor: Cursor = dbHelper.read_all_data(cityItem.key_id)
    val db: SQLiteDatabase = dbHelper.getReadableDatabase()
    try {
        while (cursor.moveToNext()) {
            val item_fav_status: String =
                cursor.getString(cursor.getColumnIndex(dbHelper.FAVORITE_STATUS))
            cityItem.setFavStatus(item_fav_status)


            if (item_fav_status == "1") {
                viewHolder.favBtn.setBackgroundResource(R.drawable.fav)
            } else if (item_fav_status == "0") {
                viewHolder.favBtn.setBackgroundResource(R.drawable.ic_favorite_shadow_24dp)
            }
        }
    } finally {
        if (cursor.isClosed) cursor.close()
        db.close()
    }
}

    private fun likeClick(favItem : FavItem, favBtn : Button,textLike:TextView) {
        val refLike = FirebaseDatabase.getInstance().reference.child("likes")
        val upVotes : DatabaseReference = refLike.child(favItem.key_id)

        if (favItem.favStatus == "0"){
            favItem.favStatus="1"
            dbHelper.insertIntoDB(favItem.item_title,favItem.item_image,
            favItem.key_id.toInt(),favItem.favStatus)
            favBtn.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
            favBtn.isSelected = true
            upVotes.runTransaction(object : Transaction.Handler{
                override fun doTransaction(currentData: MutableData): Transaction.Result {
                    val currentValue: Int? = currentData.getValue(Int::class.java)
                    if (currentValue==null){
                        currentData.value = 1
                    }else{
                        currentData.value = currentValue+1
                       Handler(Looper.getMainLooper()).post {
                       textLike.text = currentData.value.toString()

                       }
                    }
                    return Transaction.success(currentData)

                    }


                override fun onComplete(
                    error: DatabaseError?,
                    committed: Boolean,
                    currentData: DataSnapshot?
                ) {
                    print("Transaction completed!")
                }

            })

        }else if(favItem.favStatus=="1"){
            favItem.favStatus = "0"
            dbHelper.remove_fav(favItem.key_id)
            favBtn.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24)
            favBtn.isSelected = false
            upVotes.runTransaction(object : Transaction.Handler{
                override fun doTransaction(currentData: MutableData): Transaction.Result {
                    val currentValue: Int? = currentData.getValue(Int::class.java)
               if (currentValue == null){
                   currentData.value = 1
               }else{
                   currentData.value = currentValue - 1
                   Handler(Looper.getMainLooper()).post(Runnable {
                       textLike.text = currentData.value.toString()
                   })
               }
                    return Transaction.success(currentData)

                }

                override fun onComplete(
                    error: DatabaseError?,
                    committed: Boolean,
                    currentData: DataSnapshot?
                ) {
                    print("Transaction Completed!")
                }

            })

        }

    }


    inner class StoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.imageView)
        var name: TextView = itemView.findViewById(R.id.titleTextView)
        private var likeCountTextView: TextView = itemView.findViewById(R.id.likeCountTextView)
        var favBtn: Button = itemView.findViewById(R.id.favBtn)

        init {

            //add to fav btn
            favBtn.setOnClickListener {
                val position = adapterPosition
                val cityItem: DataCities = cityItems[position]
                likeClick(cityItem, favBtn, likeCountTextView)
            }
        }
    }


}