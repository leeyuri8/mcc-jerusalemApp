package com.yrabdelrhmn.finalprojectmcc.Adabter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.yrabdelrhmn.finalprojectmcc.NotificationAdapter
import com.yrabdelrhmn.finalprojectmcc.model.NotificationModel
import com.yrabdelrhmn.finalprojectmcc.R
import kotlinx.android.synthetic.main.activity_notification_page.view.*

class NotificationAdapter(var activity:Activity, var data:ArrayList<NotificationModel>)
    :RecyclerView.Adapter<NotificationAdapter.MyViewHolder>(),Filterable{


    var notificationListFilter = ArrayList<NotificationModel>()

    fun setData(){
        this.data = data
        this.notificationListFilter = data
        notifyDataSetChanged()
    }
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

      fun bindItem(model: NotificationModel){
          itemView.notification_title.text = "${model.id}. ${model.name}"
          itemView.notification_body.text = model.description



      }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(activity).inflate(R.layout.activity_notification_page,parent,false)

        return MyViewHolder(view)

    }


    override fun getItemCount(): Int {
        return data.size
    }

    override fun getFilter(): Filter {
        return object :Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                if(constraint == null|| constraint.length<0){
                    filterResults.count = notificationListFilter.size
                    filterResults.values = notificationListFilter
                }else{
                    var searchChr = constraint.toString()
                    val notificationSearch = ArrayList<NotificationModel>()
                    for(item in notificationListFilter){
                        if(item.name!!.toLowerCase()
                                .contains(searchChr) ){
                            notificationSearch.add(item)
                        }
                    }
                    filterResults.count  = notificationSearch.size
                    filterResults.values = notificationSearch
                }
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                data = results!!.values as ArrayList<NotificationModel>
                notifyDataSetChanged()

            }


        }
    }

    override fun onBindViewHolder(holder: NotificationAdapter.MyViewHolder, position: Int) {
        holder.bindItem(data[position])
    }

}