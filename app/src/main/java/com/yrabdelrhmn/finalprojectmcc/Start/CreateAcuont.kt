package com.example.cloud_project.Start

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.cloud_project.MainActivity
import com.example.cloud_project.R
import com.yrabdelrhmn.finalprojectmcc.notification.MyApp
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CreateAcuont : AppCompatActivity() {
    var db: FirebaseFirestore? = null
    private var name: EditText? = null
    private  var email:EditText? = null
    private  var last:EditText? = null
     private  var password:EditText? = null
    private var mAuth: FirebaseAuth? = null
    lateinit var btn_sing_In:Button
    private val TAG = ""
    lateinit var  btn_create_account:Button
    private var progressBar: ProgressBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_acuont)

        mAuth = FirebaseAuth.getInstance()
        db = Firebase.firestore
        name = findViewById<View>(R.id.edit_email) as EditText
        email = findViewById<View>(R.id.edit_name) as EditText
        last = findViewById<View>(R.id.edit_last) as EditText
        progressBar = findViewById<View>(R.id.progressBar) as ProgressBar
        password = findViewById<View>(R.id.editpassword) as EditText
        btn_create_account = findViewById(R.id.btn_Sign_in)
        btn_sing_In=findViewById(R.id.btn_sing_In)
        btn_sing_In.setOnClickListener {
            val intent=Intent(this,Login::class.java)
            startActivity(intent)
        }

        btn_create_account.setOnClickListener(View.OnClickListener {
            val email1 = email!!.text.toString()
            val password: String = password!!.getText().toString()
            val last: String = last!!.getText().toString()
            val name1 = name!!.text.toString()
            if (TextUtils.isEmpty(email1)) {
                Toast.makeText(applicationContext, "Enter Eamil Id", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(applicationContext, "Enter Password", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (TextUtils.isEmpty(last)) {
                Toast.makeText(applicationContext, "Enter last ", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (TextUtils.isEmpty(name1)) {
                Toast.makeText(applicationContext, "Enter name", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            progressBar!!.setVisibility(View.VISIBLE)
            mAuth!!.createUserWithEmailAndPassword(email1, password)
                .addOnCompleteListener(this, OnCompleteListener<AuthResult?> {
                    progressBar!!.setVisibility(View.GONE)
                    val user = mAuth!!.currentUser!!
                    addUser(user.uid,name1,last,user.email!!)
                })
        })


    }

    private fun addUser(
        id: String,
        Fname: String,
        Lname: String,
        email: String
    ) {
        val users =
            hashMapOf(
                "id" to id,
                "fName" to Fname,
                "lLame" to Lname,
                "email" to email
            )

        db!!.collection("users").document(id).set(users).addOnSuccessListener {
            Log.e("T.H.A FireStore", "success $Fname")

            val intent = Intent(this@CreateAcuont, Login::class.java)


            val manager = MyApp(this)
            manager.showSmallNotification(
                1,
                getString(R.string.app_name),
                "You are successfully Rigister ",
                intent, R.drawable.check, R.color.white


            )
            btn_create_account.setText("Signed")
            btn_create_account.setBackgroundColor(getApplication().getResources().getColor(R.color.Primary2))
            btn_create_account.setTextColor(getApplication().getResources().getColor(R.color.Primary))
            startActivity(intent)
            finish()

        }.addOnFailureListener { exception ->
            Log.e("T.H.A FireStore", "Error : ${exception.message}")

        }

    }
    fun login(view: View?) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
