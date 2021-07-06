package com.yrabdelrhmn.finalprojectmcc

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    private lateinit var myNotificationManager: MyNotificationManager
    private lateinit var title: String
    private lateinit var body: String
    var db : SQLiteDatabase? = null
    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Log.e("yra",p0)

    }

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        myNotificationManager = MyNotificationManager(applicationContext)
        myNotificationManager.showNotification(
            1,
            p0.notification!!.title!!,
            p0.notification!!.body!!,
            Intent(applicationContext, NotificationPage::class.java)
        )

}

    override fun handleIntent(intent: Intent) {
        super.handleIntent(intent)
        if(intent.extras!=null){
            for (key in intent.extras!!.keySet()){
                val value = intent.extras!!.getString(key)
                Log.d("TAG", "Key: {0} $key Value: {1} $value")
                if(key.equals("title")){
                    Log.d("Delay notification Title"," $value")
                    title = value!!
                }else if(key.equals("body")){
                    Log.d("Delay notification Body"," $value")
                    body = value!!
               insertData(title,body)
                }
            }
        }
    }

    private fun insertData(title : String , body :String) {
        val t = title
        val b = body
        Log.d("title",t)
        Log.d("body",b)
    }


}