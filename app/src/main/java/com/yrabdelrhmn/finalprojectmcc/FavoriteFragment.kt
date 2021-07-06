package com.yrabdelrhmn.finalprojectmcc

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yrabdelrhmn.finalprojectmcc.Adabter.FavoriteAdapter


class FavoriteFragment : Fragment() {

    private var rvFav: RecyclerView? = null
    private var param2: String? = null
    private var dbHelper: com.yrabdelrhmn.finalprojectmcc.Adabter.DBHelper?=null
    private lateinit var favItemList:ArrayList<FavItem>
    private var favAdapter : FavoriteAdapter?=null
    lateinit var root : View




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.activity_favorite_page, container, false)
       rvFav = root.findViewById(R.id.recyclerView)
        rvFav!!.setHasFixedSize(true)
        rvFav!!.layoutManager = LinearLayoutManager(activity)
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(rvFav)

        loadData()
        return root

    }

    private fun loadData() {
        favItemList.clear()
        val db : SQLiteDatabase = dbHelper!!.readableDatabase
        val cursor : Cursor? = dbHelper!!.select_all_fav_list()
       try {
           while (cursor!!.moveToNext()){
               val title:String = cursor!!.getString(cursor.getColumnIndex(DBHelper.ITEM_TITLE))
               val id : String = cursor!!.getString(cursor.getColumnIndex(DBHelper.KEY_ID))
               val image = cursor.getString(cursor.getColumnIndex(DBHelper.ITEM_IMAGE)).toInt()
               val favItem= FavItem(title,id,image,"")
                 favItemList.add(favItem)
           }


       } finally {
       if (cursor!=null&&cursor.isClosed)
           cursor.close()
           db.close()

       }
        favAdapter = context?.let { FavoriteAdapter(it,favItemList) }
        rvFav!!.adapter = favAdapter

    }

    private val itemTouchHelperCallback =
        object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {

                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val favItem:FavItem = favItemList[position]
                if(direction==ItemTouchHelper.LEFT){
                    favAdapter!!.notifyItemRemoved(position)
                    favItemList.removeAt(position)
                    dbHelper!!.remove_fav(favItem.key_id)
                }
            }

        }


}