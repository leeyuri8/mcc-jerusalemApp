package com.yrabdelrhmn.finalprojectmcc

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.cloud_project.Adabter.NewsAdabter
import com.yrabdelrhmn.finalprojectmcc.model.DataNews
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.Map
import kotlin.collections.MutableMap
import kotlin.collections.set


class News : Fragment() {
    private var data = ArrayList<DataNews>()
    private lateinit var news: RecyclerView
    private val URL_NEWS =
        "https://newsapi.org/v2/everything?q=القدس&sortBy=popularity&from=2021-06-01&to=2021-07-16&apiKey=8b2cd56a0e634212bf4d587190a94c35"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_news, container, false)
        news = root.findViewById(R.id.rvNews)


        getJSONObject()

       // getJSONObject()



        return root
    }

    /*private fun getJSONObject() {

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, URL_NEWS, null,
            Response.Listener
            { response ->
                Log.e("T.H.A", response.toString())



                /*val status = response.getString("status")
                val totalResults = response.getInt("totalResults")*/
                val articles = response.getJSONArray("articles")


                for (i in 0 until articles.length()) {
                    val newsJSONObject = articles.getJSONObject(i)
                    val source = newsJSONObject.getJSONObject("source")
                    val news = DataNews(
                        source.getString("id"),
                        source.getString("name"),
                        newsJSONObject.getString("author"),
                        newsJSONObject.getString("title"),
                        newsJSONObject.getString("description"),
                        newsJSONObject.getString("url"),
                        newsJSONObject.getString("urlToImage"),
                        newsJSONObject.getString("publishedAt"),
                        newsJSONObject.getString("content")
                    )
                    data.add(news)
                }







                news.layoutManager = LinearLayoutManager(context)


                news.setHasFixedSize(true)

                val newsAdabter = NewsAdabter(requireContext(), data)
                news.adapter = newsAdabter


            },
            { error ->
                Log.e("T.H.A Error", "$error")
            })
        MySingleton.getInstance()!!.addRequestQueue<JsonObjectRequest>(jsonObjectRequest)
        MySingleton.getInstance()!!.addRequestQueue(jsonObjectRequest, "jsonObject")


    }*/


    /*private fun AddNews(
            CityDetails: String,
            CityArea: Double,
            CityVideo: String,
            fileImageList: MutableList<Int>,
            CityName: String,
            latitude: Double,
            longitude: Double
    ) {
        val id = UUID.randomUUID().toString()
        val Products =
                hashMapOf(
                        "id" to id,
                        "CityVideo" to CityVideo,
                        "CityDetails" to CityDetails,
                        "CityArea" to CityArea,
                        "CityPictures" to fileImageList,
                        "CityName" to CityName,
                        "latitude" to latitude,
                        "longitude" to longitude
                )


        db!!.collection("cities").document(id).set(Products).addOnSuccessListener { void ->

            Log.e("T.H.A FireStore", "Added success $void")
            val intent = Intent(context, MainActivity::class.java)


            val manager = MyApp(context)
            manager.showSmallNotification(
                    1,
                    getString(R.string.app_name),
                    "Product added successfully ",
                    intent, R.drawable.check,
                    R.color.white


            )


        }.addOnFailureListener { exception ->
            Log.e("T.H.A FireStore", "Error : ${exception.message}")
            val intent = Intent(context, MainActivity::class.java)


            val manager = MyApp(context)
            manager.showSmallNotification(
                    1,
                    getString(R.string.app_name),
                    "Failed to add product ${exception.message}",
                    intent, R.drawable.error,
                    R.color.white


            )
        }

    }*/


    fun getJSONObject() {
        //val queue = Volley.newRequestQueue(context)
        val getRequest: JsonObjectRequest = object : JsonObjectRequest(
            Request.Method.GET, URL_NEWS,null,
            Response.Listener { response -> // response
                Log.d("Response", response.toString())
                Log.e("T.H.A", response.toString())



                /*val status = response.getString("status")
                val totalResults = response.getInt("totalResults")*/
                val articles = response.getJSONArray("articles")


                for (i in 0 until articles.length()) {
                    val newsJSONObject = articles.getJSONObject(i)
                    val source = newsJSONObject.getJSONObject("source")
                    val news = DataNews(
                        source.getString("id"),
                        source.getString("name"),
                        newsJSONObject.getString("author"),
                        newsJSONObject.getString("title"),
                        newsJSONObject.getString("description"),
                        newsJSONObject.getString("url"),
                        newsJSONObject.getString("urlToImage"),
                        newsJSONObject.getString("publishedAt"),
                        newsJSONObject.getString("content")
                    )
                    data.add(news)
                }







                news.layoutManager = LinearLayoutManager(context)


                news.setHasFixedSize(true)

                val newsAdabter = NewsAdabter(requireContext(), data)
                news.adapter = newsAdabter
            },
            Response.ErrorListener { error -> // TODO Auto-generated method stub
                Log.d("ERROR", "error => $error")
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["User-Agent"] = "Mozilla/5.0"
                //params["Accept-Language"] = "fr"
                return params
            }
        }
        MySingleton.getInstance()!!.addRequestQueue<JsonObjectRequest>(getRequest)
        MySingleton.getInstance()!!.addRequestQueue(getRequest, "jsonObject")
    }

}