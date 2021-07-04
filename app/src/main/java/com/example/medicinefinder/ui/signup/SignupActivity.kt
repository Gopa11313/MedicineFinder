package com.example.medicinefinder.ui.signup

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.medicinefinder.R
import com.example.medicinefinder.SliderActivity
import com.example.medicinefinder.model.Seller
import com.example.medicinefinder.repository.SellerRepository
import com.example.medicinefinder.ui.login.LoginActivity
import com.google.android.gms.location.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class SignupActivity : AppCompatActivity() {
    private lateinit var sg_name:EditText
    private lateinit var sg_email:EditText
    private lateinit var sg_password:EditText
    private lateinit var sg_con_password:EditText
    private lateinit var signup: Button
    private lateinit var sg_signin:TextView
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    val PERMISSION_ID = 1010
    var longitude:String?=null
    var latitude:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        sg_name=findViewById(R.id.sg_name)
        sg_email=findViewById(R.id.sg_email)
        sg_password=findViewById(R.id.sg_password)
        sg_con_password=findViewById(R.id.sg_con_password)
        signup=findViewById(R.id.signup)
        sg_signin=findViewById(R.id.sg_signin)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        signup.setOnClickListener(){
            signup()
            Log.d("Debug:",CheckPermission().toString())
            Log.d("Debug:",isLocationEnabled().toString())
            RequestPermission()
            /* fusedLocationProviderClient.lastLocation.addOnSuccessListener{location: Location? ->
                 textView.text = location?.latitude.toString() + "," + location?.longitude.toString()
             }*/
            getLastLocation()
        }
    }
    fun signup(){
        if(validation()) {
            val seller = Seller(name = sg_name.text.toString(), email = sg_email.text.toString(), password = sg_password.text.toString(), latitude = latitude, longitude = longitude)
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
    fun getLastLocation(){
        if(CheckPermission()){
            if(isLocationEnabled()){
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return
                }
                fusedLocationProviderClient.lastLocation.addOnCompleteListener { task->
                    var location: Location? = task.result
                    if(location == null){
                        NewLocationData()
                    }else{
                        Log.d("Debug:" ,"Your Location:"+ location.longitude)
                        val long=location.longitude
                        val latit=location.latitude
                        longitude= long.toString()
                        latitude=latit.toString()
//                        val a=longitude
                    }
                }
            }else{
                Toast.makeText(this,"Please Turn on Your device Location",Toast.LENGTH_SHORT).show()
            }
        }else{
            RequestPermission()
        }
    }


    fun NewLocationData(){
        var locationRequest =  LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 1
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationProviderClient!!.requestLocationUpdates(
            locationRequest,locationCallback, Looper.myLooper()
        )
    }


    private val locationCallback = object : LocationCallback(){
        override fun onLocationResult(locationResult: LocationResult) {
            var lastLocation: Location = locationResult.lastLocation
            Log.d("Debug:","your last last location: "+ lastLocation.longitude.toString())
            longitude=lastLocation.longitude.toString()
        latitude= lastLocation.latitude.toString()
        }
    }

    private fun CheckPermission():Boolean{
        //this function will return a boolean
        //true: if we have permission
        //false if not
        if(
            ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ){
            return true
        }

        return false

    }

    fun RequestPermission(){
        //this function will allows us to tell the user to requesut the necessary permsiion if they are not garented
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION,android.Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_ID
        )
    }

    fun isLocationEnabled():Boolean{
        //this function will return to us the state of the location service
        //if the gps or the network provider is enabled then it will return true otherwise it will return false
        var locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == PERMISSION_ID){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.d("Debug:","You have the Permission")
            }
        }
    }

    private fun getCityName(lat: Double,long: Double):String{
        var cityName:String = ""
        var countryName = ""
        var geoCoder = Geocoder(this, Locale.getDefault())
        var Adress = geoCoder.getFromLocation(lat,long,3)

        cityName = Adress.get(0).locality
        countryName = Adress.get(0).countryName
        Log.d("Debug:","Your City: " + cityName + " ; your Country " + countryName)
        return cityName
    }

}