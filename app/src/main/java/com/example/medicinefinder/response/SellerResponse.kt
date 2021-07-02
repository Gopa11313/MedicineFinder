package com.example.medicinefinder.response

import com.example.medicinefinder.api.SellerApi

data class SellerResponse(
    val success:Boolean?=null,
    val msg:String?=null,
    val data:MutableList<SellerApi>?=null,
    val id:String?=null
) {
}