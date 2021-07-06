package com.yrabdelrhmn.finalprojectmcc.Adabter

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.DatabaseUtils
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.yrabdelrhmn.finalprojectmcc.DBHelper

class DBHelper(context:Context):
SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSION) {
    var db : SQLiteDatabase? = null
    var dHelper: DBHelper? =null
    override fun onCreate(db: SQLiteDatabase) {
    val q1 = "CREATE TABLE notifications"+
            "(notification_id Integer PRIMARY KEY,"+
            "notification_title TEXT,"+
            "notification_body TEXT"
        db.execSQL(q1)


    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
         db!!.execSQL("DROP TABLE IF EXISTS notifications")
        onCreate(db)
    }
   fun query(query: String): Boolean {
        val db = this.writableDatabase
    db.execSQL(query)
    db.close()
    return true;
} // query

// Count
fun countDB(query: String): Int {
    val db = this.writableDatabase
    val numRows =
        DatabaseUtils.longForQuery(db, query, null).toInt()
    db.close()
    return numRows;
}

// Get
fun rawQuery(query: String?): Cursor {
    val db = this.writableDatabase
    val mCursor: Cursor= db.rawQuery(query, null)
    mCursor.moveToFirst()
    return mCursor


}
    fun insetData(id : Int , title : String,body:String) : Long {
        try {
            val cv = ContentValues()
            cv.put("notification_id",id)
            cv.put("notification_title",title)
            cv.put("notification_body",body)

            return db!!.insert("notifications","id",cv)
        }catch (e:SQLException){
            e.printStackTrace()
        }
        return 0
    }

    fun insertEmpty(){
        val db = this.writableDatabase
        val cv = ContentValues()
        for (x  in 1..20){
          cv.put(KEY_ID, x)
          cv.put(FAVORITE_STATUS, "0")
            db.insert(TABLE_NAME,null,cv)
        }

    }
    fun insertIntoDB(item_title:String,item_image:Int,item_id:Int,fav_status:String){
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(ITEM_TITLE, item_title)
        cv.put(ITEM_IMAGE,item_image)
        cv.put(KEY_ID,item_id)
        cv.put(FAVORITE_STATUS,fav_status)
        db.insert(TABLE_NAME,null,cv)
        Log.d("FavDB Status", "$item_title, favstatus - $fav_status- .$cv")
    }

    fun read_all_data(id:String):Cursor{
        val db = this.writableDatabase
         val sql = "select * from $TABLE_NAME where $KEY_ID = id "
        return db.rawQuery(sql,null,null)
    }
    fun remove_fav(id: String){
        val db = this.writableDatabase
        var sql = "UPDATE $TABLE_NAME SET  $FAVORITE_STATUS ='0' WHERE $KEY_ID=$id"
        db.execSQL(sql)
        Log.d("remove", id)
    }
    fun select_all_fav_list(): Cursor? {
        val db = this.readableDatabase
        var sql = "SELECT * FROM $TABLE_NAME WHERE $FAVORITE_STATUS = 1"
        return db.rawQuery(sql,null,null)

    }


    companion object {
         val DATABASE_VERSION = 1
         val DATABASE_NAME = "notifications"
         val KEY_ID = "id"
        val ITEM_TITLE = "itemTitle"
        val TABLE_NAME = "favoriteTable"
        val ITEM_IMAGE = "itemImage"
         val FAVORITE_STATUS = "fStatus"
    }


}