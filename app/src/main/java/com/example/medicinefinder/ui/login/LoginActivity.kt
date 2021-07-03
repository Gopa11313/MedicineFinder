package com.example.medicinefinder.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.medicinefinder.R
import com.example.medicinefinder.model.Seller
import com.example.medicinefinder.repository.SellerRepository
import com.example.medicinefinder.ui.signup.SignupActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    lateinit var editTextEmail:EditText
    lateinit var editTextPassword:EditText
    lateinit var cirLoginButton: Button
    lateinit var gotoSignup:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        editTextEmail=findViewById(R.id.editTextEmail)
        editTextPassword=findViewById(R.id.editTextPassword)
        cirLoginButton=findViewById(R.id.cirLoginButton)
        gotoSignup=findViewById(R.id.gotoSignup)

        gotoSignup.setOnClickListener(){
            startActivity(Intent(this,SignupActivity::class.java))
        }
        cirLoginButton.setOnClickListener(){

        }

    }
    fun login(){
        val seller=Seller(email = editTextEmail.text.toString(),password = editTextPassword.text.toString())
        CoroutineScope(Dispatchers.IO).launch {
            val repository=SellerRepository()
            val response=repository.LoginUser(seller)
            if(response.success==true){

            }
        }
    }
}