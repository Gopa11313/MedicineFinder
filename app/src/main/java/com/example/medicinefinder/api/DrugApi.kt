package com.example.medicinefinder.api

import com.example.medicinefinder.model.Drug
import com.example.medicinefinder.response.DrugResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface DrugApi {
@POST("upload/drug")
suspend fun uploadDrug(@Body drug: Drug):Response<DrugResponse>
}