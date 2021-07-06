package com.example.medicinefinder.api

import com.example.medicinefinder.model.Seller
import com.example.medicinefinder.response.SellerResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SellerApi {
    @POST("register/user")
    suspend fun UserSignup(@Body seller:Seller):Response<SellerResponse>

    @POST("seller/login")
    suspend fun USerLogin(@Body seller: Seller):Response<SellerResponse>

    @GET("Seller/by/{id}")
    suspend fun getUSer(@Path("id") id:String):Response<SellerResponse>
}