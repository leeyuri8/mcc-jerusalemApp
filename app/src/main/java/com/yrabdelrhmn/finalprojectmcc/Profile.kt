package com.yrabdelrhmn.finalprojectmcc

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.vansuita.pickimage.bundle.PickSetup
import com.vansuita.pickimage.dialog.PickImageDialog
import com.vansuita.pickimage.listeners.IPickCancel
import com.vansuita.pickimage.listeners.IPickResult
import java.util.*


class Profile : Fragment() {

    var db: FirebaseFirestore? = null
    lateinit var auth: FirebaseAuth
    lateinit var BackPersonalImage: ImageView
    lateinit var PersonImage: ImageView
    lateinit var Name: TextView
    lateinit var Email: TextView
    var storage: FirebaseStorage? = null
    var reference: StorageReference? = null
    var path = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root= inflater.inflate(R.layout.fragment_profile, container, false)

        auth = Firebase.auth
        db = Firebase.firestore
        storage = Firebase.storage
        reference = storage!!.reference
        BackPersonalImage = root.findViewById(R.id.ProfilePersonalPicture1)
        PersonImage = root.findViewById(R.id.ProfilePersonalPicture)
        Name = root.findViewById(R.id.ProfileFullName)
        Email = root.findViewById(R.id.ProfileEmail)
        PersonImage.setOnClickListener {
            PickImageDialog.build(PickSetup())
                .setOnPickResult(object : IPickResult {
                    override fun onPickResult(r: com.vansuita.pickimage.bean.PickResult?) {
                        PersonImage.setImageBitmap(r!!.bitmap)
                        uploadImage(r.uri)
                    }
                })
                .setOnPickCancel(object : IPickCancel{
                    override fun onCancelClick() {
                        TODO("Not yet implemented")
                    }

                }).show(fragmentManager)

        }


        getProfileData()

      return root
    }
    fun getProfileData() {
        db!!.collection("users").document(auth.currentUser!!.uid).get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot.get("image").toString().isNotEmpty()){
                    Glide.with(this)
                        .load(querySnapshot.get("image"))
                        .into(BackPersonalImage)
                    Glide.with(this)
                        .load(querySnapshot.get("image"))
                        .into(PersonImage)
                }




                val FullName = "${querySnapshot.get("fName").toString()}  ${querySnapshot.get("lLame").toString()}"
                Name.setText(FullName)
                Email.setText(auth.currentUser!!.email)
            }.addOnFailureListener { exception ->
                Log.e("T.H.A Profile :","Error : ${exception.message}")

            }
    }
    fun updateImage(path:String){
        val washingtonRef = db!!.collection("users").document(auth.currentUser!!.uid)

        washingtonRef
            .update("image", path)
            .addOnSuccessListener { Log.e("T.H.A Edit:", "DocumentSnapshot successfully updated!") }
            .addOnFailureListener { e -> Log.e("T.H.A Edit:", "Error updating document", e) }
    }



    private fun uploadImage(uri: Uri?) {
        reference!!.child("profile/" + UUID.randomUUID().toString()).putFile(uri!!)
            .addOnSuccessListener { taskSnapshot ->
                taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                    path = uri.toString()
                    updateImage(path)

                }
                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()

            }.addOnFailureListener { exception ->
                Log.e("T.H.A Storege:","Erorr : ${exception.message}")


            }
    }


}


