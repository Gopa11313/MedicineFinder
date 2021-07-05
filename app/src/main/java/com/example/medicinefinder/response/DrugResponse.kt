package com.example.medicinefinder.response

import com.example.medicinefinder.model.Drug

data class DrugResponse(
    var success:Boolean?=null,
    var msg:String?=null,
    var data:MutableList<Drug>?=null
) {
}