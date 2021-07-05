package com.example.medicinefinder.api

import com.example.medicinefinder.model.Drug
import com.example.medicinefinder.response.DrugResponse
import retrofit2.Response
import retrofit2.http.*

interface DrugApi {
@POST("upload/drug")
suspend fun uploadDrug(@Body drug: Drug):Response<DrugResponse>

@GET("all/drugs/{id}")
suspend fun getAllDrug(
    @Header("Authorization")token:String,
    @Path("id") id:String ):Response<DrugResponse>
}