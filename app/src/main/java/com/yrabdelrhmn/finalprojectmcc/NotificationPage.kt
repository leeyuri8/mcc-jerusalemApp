package com.yrabdelrhmn.finalprojectmcc

import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.yrabdelrhmn.finalprojectmcc.Adabter.DBHelper
import com.yrabdelrhmn.finalprojectmcc.Adabter.NotificationAdapter
import com.yrabdelrhmn.finalprojectmcc.model.NotificationModel

import kotlinx.android.synthetic.main.rv_notification.*

class NotificationPage : AppCompatActivity() {
     private var db : DBHelper? = null
    private lateinit var adapter: NotificationAdapter
    private lateinit var notificationsList : ArrayList<NotificationModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rv_notification)

        listNotifications()

    }
    private fun listNotifications(){
        db = DBHelper(this)
     val notificationsCursor : Cursor = db!!
         .rawQuery("notification_id,notification_title,notification_body FROM notifications")
        val notificationsSize : Int = notificationsCursor.count
        Log.d("listNotifications()", "notifications Size = $notificationsSize")
        notificationsList = ArrayList<NotificationModel>()
        while (notificationsCursor.moveToNext()){
            val notificationId = notificationsCursor.getInt(0)
            val notificationTitle = notificationsCursor.getString(1)
            val notificationBody = notificationsCursor.getString(2)
            Log.d("listNotifications()",
                "notificationId $notificationId"
                    +"notificationTitle $notificationTitle"
                    +"notificationBody $notificationBody")
            notificationsList.add(NotificationModel(notificationId,notificationTitle,notificationBody))

        }

        rvNotification.adapter = adapter
        rvNotification.layoutManager = LinearLayoutManager(this)
        rvNotification.setHasFixedSize(true)
        }
    }


/*
  private fun getAllNotifications(){
      val query = db!!.collection("notifications")
       val options = FirebaseRecyclerOptions.Builder<NotificationModel>().setQuery(
           query,NotificationModel::class.java
       ).build()
      adapter = object : FirebaseRecyclerAdapter<NotificationModel,NotificationViewHolder>(options){
          override fun onCreateViewHolder(
              parent: ViewGroup,
              viewType: Int
          ): NotificationViewHolder {
              val view = LayoutInflater.from(this@NotificationPage)
                  .inflate(R.layout.activity_notification_page,parent,false)
            return NotificationViewHolder(view)
          }

          override fun onBindViewHolder(
              holder: NotificationViewHolder,
              position: Int,
              model: NotificationModel
          ) {
              holder.title.text = model.name
              holder.body.text = model.description
               holder.id.text = model.id
    }

      }
      rvNotification?.layoutManager = LinearLayoutManager(this)
     rvNotification?.adapter = adapter
  }

    override fun onStart() {
        super.onStart()
        adapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter!!.stopListening()
    }

}

class NotificationViewHolder(view:View) : RecyclerView.ViewHolder(view) {
    var id :TextView = view.notification_id
    var title :TextView = view.notification_title
    var body:TextView = view.notification_body

}
*/


