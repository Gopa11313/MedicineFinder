package com.example.medicinefinder.repository

import com.example.medicinefinder.api.DrugApi
import com.example.medicinefinder.api.MyApiRequest
import com.example.medicinefinder.api.ServiceBuilder
import com.example.medicinefinder.model.Drug
import com.example.medicinefinder.response.DrugResponse

class DrugRepository:MyApiRequest() {
    var myapi=ServiceBuilder.buildServices(DrugApi::class.java)

    suspend fun UploadDrug(drug:Drug):DrugResponse{
        return  apiRequest{
            myapi.uploadDrug(drug)
        }
    }
    suspend fun getAllDrug(token:String,id:String):DrugResponse{
        return apiRequest {
            myapi.getAllDrug(token,id)
        }
    }
    suspend fun searchDrug(drug: Drug):DrugResponse{
        return apiRequest {
            myapi.SearchDrug(drug)
        }
    }
}