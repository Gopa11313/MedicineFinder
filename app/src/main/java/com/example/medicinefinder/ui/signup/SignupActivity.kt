package com.example.medicinefinder.ui.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.medicinefinder.R
import com.example.medicinefinder.SliderActivity
import com.example.medicinefinder.model.Seller
import com.example.medicinefinder.repository.SellerRepository
import com.example.medicinefinder.ui.login.LoginActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignupActivity : AppCompatActivity() {
    private lateinit var sg_name:EditText
    private lateinit var sg_email:EditText
    private lateinit var sg_password:EditText
    private lateinit var sg_con_password:EditText
    private lateinit var signup: Button
    private lateinit var sg_signin:TextView
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
//    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
//    lateinit var locationRequest: LocationRequest
//    val PERMISSION_ID = 1010
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        sg_name=findViewById(R.id.sg_name)
        sg_email=findViewById(R.id.sg_email)
        sg_password=findViewById(R.id.sg_password)
        sg_con_password=findViewById(R.id.sg_con_password)
        signup=findViewById(R.id.signup)
        sg_signin=findViewById(R.id.sg_signin)
        signup.setOnClickListener(){
            signup()
        }
    }
    fun signup(){
        if(validation()) {
            val seller = Seller(
                name = sg_name.text.toString(),
                email = sg_email.text.toString(),
                password = sg_password.text.toString()
            )
            CoroutineScope(Dispatchers.IO).launch {
                val sellerReposiroty = SellerRepository()
                val response = sellerReposiroty.SignupUser(seller)
                if(response.success==true){
                    withContext(Main){
                        Toast.makeText(this@SignupActivity, "Successfully register", Toast.LENGTH_SHORT).show()
                        Handler(Looper.getMainLooper()).postDelayed({
                            startActivity(Intent(this@SignupActivity,LoginActivity::class.java))
                            finish()
                        }, 2000)

                    }
                }
                else{
                    withContext(Main){
                        Toast.makeText(this@SignupActivity, "error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
    fun validation():Boolean{
        if(sg_name.text.toString().isEmpty() && sg_email.text.toString().isEmpty() && sg_password.text.toString().isEmpty() && sg_con_password.text.toString().isEmpty()){
            Toast.makeText(this, "All the requirement must be fill", Toast.LENGTH_SHORT).show()
            return false
        }
        else{
            if(sg_email.text.toString().matches(emailPattern.toRegex())){
                if(sg_password.text.toString().length>6){
                    if(sg_password.text.toString()==sg_con_password.text.toString()){
                        return true
                    }
                    else{
                        Toast.makeText(this, "Password and coon-password must be same", Toast.LENGTH_SHORT).show()
                        return false
                    }
                }
                else{
                    Toast.makeText(this, "Password must be long than 6 character", Toast.LENGTH_SHORT).show()
                    return false
                }
            }
            else{
                Toast.makeText(this, "Please Enter valid email", Toast.LENGTH_SHORT).show()
                return false
            }
        }
    }
}