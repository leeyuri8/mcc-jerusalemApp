package com.yrabdelrhmn.finalprojectmcc.model

data class DataCities (val id:String="", val CityVideo :String="",val CityDetails:String ="", val CityArea:Double=0.0,val CityPictures:MutableList<Int>   = arrayListOf(0),
                       val CityName:String="",val latitude :Double=0.0,val longitude:Double=0.0 )