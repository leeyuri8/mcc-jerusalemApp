package com.example.cloud_project

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yrabdelrhmn.finalprojectmcc.model.UploadImage
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.yrabdelrhmn.finalprojectmcc.Site

class DetailsCities : AppCompatActivity() {
    var db: FirebaseFirestore? = null
    lateinit var toolbar:androidx.appcompat.widget.Toolbar
    lateinit var city_Video: PlayerView
    lateinit var city_Details: TextView
    lateinit var UrlVideo:String
    lateinit var city_Area: TextView
    lateinit var city_Location: TextView
    lateinit var city_images: RecyclerView
    lateinit var btn_city_location: ImageView
    private var fileImageList: MutableList<Int>? = null
    private var uploadListAdapter: UploadImage? = null
    //lateinit var cities: RecyclerView
    lateinit var i: String
    var simpleExoPlayer: SimpleExoPlayer? = null
    private var playWhenReady = true
    private var currentWindow = 0
    private var playpackposition: Long = 0
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_cities)
        db = Firebase.firestore
        city_Area = findViewById(R.id.city_area)
        city_Video = findViewById(R.id.city_Video)
        city_Details = findViewById(R.id.city_details)
        city_Location = findViewById(R.id.city_name)
        city_images = findViewById(R.id.city_images)
        btn_city_location = findViewById(R.id.btn_city_location)
        i = intent.getStringExtra("id")
        toolbar = findViewById(R.id.toolbar)

        setSupportActionBar(toolbar as androidx.appcompat.widget.Toolbar)
        if (i.isNotEmpty()) {
            city_Details()



        } else {
            Toast.makeText(this, "No", Toast.LENGTH_LONG).show()
        }


    }




    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun city_Details() {
        db!!.collection("cities").document(i).get()
            .addOnSuccessListener { querySnapshot ->
/*
lateinit var city_Video: PlayerView
lateinit var city_images:RecyclerView
lateinit var btn_city_location:ImageView

 */
                btn_city_location.setOnClickListener {
                    val intent = Intent(this, Site::class.java)
                    intent.putExtra("name",querySnapshot.get("CityName").toString())
                    intent.putExtra("latitude",querySnapshot.get("latitude").toString())
                    intent.putExtra("longitude",querySnapshot.get("longitude").toString())
                    startActivity(intent)

                }
                UrlVideo=querySnapshot.get("CityVideo").toString()

                getSupportActionBar()!!.setTitle(querySnapshot.get("CityName").toString())
               // getSupportActionBar()!!.setIcon(R.drawable.logo);
                //getSupportActionBar()!!.setDisplayUseLogoEnabled(true);
                /*
                "CityVideo" to CityVideo,
                "CityPictures" to fileImageList,
                "latitude" to latitude,
                "longitude" to longitude
                 */
                city_Details.setText(querySnapshot.get("CityDetails").toString())
                city_Area.setText(querySnapshot.get("CityArea").toString())
                city_Location.setText(querySnapshot.get("CityName").toString())


                try{

                    fileImageList= querySnapshot.get("CityPictures") as MutableList<Int>
                    uploadListAdapter =  UploadImage(fileImageList!!, this)

                    //RecyclerView
                    GridLayoutManager(
                            this, // context
                            3, // span count
                            RecyclerView.VERTICAL, // orientation
                            false // reverse layout
                    ).apply {
                        // specify the layout manager for recycler view
                        city_images.layoutManager = this
                    }

                    // finally, data bind the recycler view with adapter
                    city_images.adapter =uploadListAdapter

                    //RecyclerView
                } catch (ex: NumberFormatException){ // handle your exception
                    Log.e("T.H.A error", "${ex}")
                }




            }.addOnFailureListener {

                }


    }

    @SuppressLint("RestrictedApi")
    fun initVideo(source: String?) {
        //player
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(this)

        //connect player with player view
        city_Video.setPlayer(simpleExoPlayer)

        //media source
        val uri = Uri.parse(source)
        val dataSource: DataSource.Factory = DefaultDataSourceFactory(this, "exoplayer-codelab")
        val media: MediaSource = ProgressiveMediaSource.Factory(dataSource).createMediaSource(uri)
        simpleExoPlayer!!.setPlayWhenReady(playWhenReady)
        simpleExoPlayer!!.seekTo(currentWindow, playpackposition)
        simpleExoPlayer!!.prepare(media, false, false)
    }

    fun releaseVideo() {
        if (simpleExoPlayer != null) {
            playWhenReady = simpleExoPlayer!!.getPlayWhenReady()
            playpackposition = simpleExoPlayer!!.getContentPosition()
            currentWindow = simpleExoPlayer!!.getCurrentWindowIndex()
            simpleExoPlayer!!.release()
            simpleExoPlayer = null
        }
    }


    override fun onStart() {
        super.onStart()

        db!!.collection("cities").document(i).get()
                .addOnSuccessListener { querySnapshot ->

                    UrlVideo=querySnapshot.get("CityVideo").toString()

                    initVideo(UrlVideo)
                }




    }


    override fun onResume() {
        super.onResume()
        if (simpleExoPlayer != null) {
            db!!.collection("cities").document(i).get()
                    .addOnSuccessListener { querySnapshot ->

                        UrlVideo=querySnapshot.get("CityVideo").toString()

                        initVideo(UrlVideo)
                    }

        }
    }

    override fun onPause() {
        super.onPause()
        releaseVideo()
    }

    override fun onStop() {
        super.onStop()
        releaseVideo()
    }

    
}