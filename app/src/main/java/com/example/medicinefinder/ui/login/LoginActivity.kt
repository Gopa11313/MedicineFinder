package com.example.medicinefinder.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.medicinefinder.R
import com.example.medicinefinder.api.ServiceBuilder
import com.example.medicinefinder.model.Seller
import com.example.medicinefinder.repository.SellerRepository
import com.example.medicinefinder.ui.signup.SignupActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

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
login()
        }

    }
    fun login(){
        val seller=Seller(email = editTextEmail.text.toString(),password = editTextPassword.text.toString())
        try {
        CoroutineScope(Dispatchers.IO).launch {
            val repository=SellerRepository()
            val response=repository.LoginUser(seller)
            if(response.success==true){
                withContext(Main){
                    Toast.makeText(this@LoginActivity, "${response.msg}", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@LoginActivity,Us::class.java))
                    ServiceBuilder.id=response.id!!
                    Toast.makeText(this@LoginActivity, "${response.id}", Toast.LENGTH_SHORT).show()
                    ServiceBuilder.token="Brearer {${response.token}}"
                }
            }
            else{
                withContext(Main){
                    Toast.makeText(this@LoginActivity, "${response.msg}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }catch (e:Exception){
            Toast.makeText(this, "$e", Toast.LENGTH_SHORT).show()
    }

    }

}