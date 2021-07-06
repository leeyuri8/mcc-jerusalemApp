package com.example.cloud_project.Start

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.cloud_project.MainActivity
import com.example.cloud_project.R
import com.google.android.gms.tasks.OnCanceledListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth


class Login : AppCompatActivity() {
    private var email: EditText? = null
    private  var password:EditText? = null
    private var mAuth: FirebaseAuth? = null
    private var pB: ProgressBar? = null
    lateinit var btn_sing_up:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        email = findViewById<View>(R.id.edit_email) as EditText
        password = findViewById<View>(R.id.editpassword) as EditText
        btn_sing_up=findViewById(R.id.btn_sing_up)
        btn_sing_up.setOnClickListener {
            val intent = Intent(this,CreateAcuont::class.java)
            startActivity(intent)
        }
        pB = findViewById<View>(R.id.PBar) as ProgressBar
        pB!!.setVisibility(View.INVISIBLE)
        mAuth = FirebaseAuth.getInstance()
        val btn_Sign_in = findViewById<View>(R.id.btn_Sign_in) as Button



        btn_Sign_in.setOnClickListener(View.OnClickListener {
        pB?.setVisibility(View.INVISIBLE)
        val Email: String = email?.getText().toString().trim()
        val Pass: String = password?.getText().toString().trim()
        if (Email.isEmpty()) {
            Toast.makeText(this, "Please Add Email.", Toast.LENGTH_SHORT).show()
        } else if (Pass.isEmpty()) {
            Toast.makeText(this, "Please Add Password.", Toast.LENGTH_SHORT).show()
        } else {
            mAuth?.signInWithEmailAndPassword(Email, Pass)
                ?.addOnSuccessListener(OnSuccessListener<AuthResult?> {
                    val intent = Intent(this@Login, MainActivity::class.java)
                    startActivity(intent)
                    pB?.setVisibility(View.VISIBLE)
                })!!.addOnFailureListener(OnFailureListener { e ->
                    Toast.makeText(this@Login, "Error $e", Toast.LENGTH_SHORT).show()
                    pB?.setVisibility(View.VISIBLE)
                }).addOnCanceledListener(OnCanceledListener {
                    Toast.makeText(
                        this@Login, "Task has Cancled From User , Please Try Agen",
                        Toast.LENGTH_LONG
                    ).show()
                    pB?.setVisibility(View.VISIBLE)
                })
        }


    })
    }
            fun CretaAccunte(view: View?) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
    }
