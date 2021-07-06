package com.yrabdelrhmn.finalprojectmcc

import android.app.Application
import android.text.TextUtils
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class MySingleton : Application() {

    val TAG = "T.H.A"
    private var mRequestQueue: RequestQueue? = null

    companion object {
        private var mInstance: MySingleton? = null

        fun getInstance(): MySingleton? {
            return mInstance
        }
    }

    override fun onCreate() {
        super.onCreate()
        mInstance = this
    }

    private fun getRequestQueue(): RequestQueue? {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(this)
        }
        return mRequestQueue
    }

    fun <T> addRequestQueue(req: Request<T>, tag: String?) {
        req.tag = if (TextUtils.isEmpty(tag)) TAG else tag
        getRequestQueue()!!.add(req)
        req.headers.get("User-Agent")
    }

    fun <T> addRequestQueue(req: JsonObjectRequest) {
        req.tag = TAG
        getRequestQueue()!!.add(req)

    }

    fun cancelPendingRequests(tag: Any?) {
        if (mRequestQueue != null) {
            mRequestQueue!!.cancelAll(tag)
        }
    }
}