package com.example.medicinefinder.ui.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DrugViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is drug Fragment"
    }
    val text: LiveData<String> = _text
}