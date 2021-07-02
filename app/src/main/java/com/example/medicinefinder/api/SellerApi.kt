package com.example.medicinefinder.api

import com.example.medicinefinder.model.Seller
import com.example.medicinefinder.response.SellerResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SellerApi {
    @POST("register/user")
    suspend fun UserSignup(@Body sellerapi:Seller):Response<SellerResponse>
}