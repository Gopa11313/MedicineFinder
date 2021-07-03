package com.example.medicinefinder.response

import com.example.medicinefinder.model.Seller

data class SellerResponse(
    val success:Boolean?=null,
    val token:String?=null,
    val msg:String?=null,
    val data:List<Seller>?=null,
    val id:String?=null
) {
}