package com.example.medicinefinder.repository

import com.example.medicinefinder.api.MyApiRequest
import com.example.medicinefinder.api.SellerApi
import com.example.medicinefinder.api.ServiceBuilder
import com.example.medicinefinder.model.Seller
import com.example.medicinefinder.response.SellerResponse

class SellerRepository:MyApiRequest() {
    val myapi= ServiceBuilder.buildServices(SellerApi::class.java)
    suspend fun SignupUser(seller:Seller):SellerResponse{
        return  apiRequest {
        myapi.UserSignup(seller)
        }
    }

    suspend fun LoginUser(seller: Seller):SellerResponse{
        return apiRequest {
            myapi.USerLogin(seller)
        }
    }
}