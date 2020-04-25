package com.example.jetpackapp.model

import android.util.Log
import com.example.jetpackapp.base.BaseModel
import com.example.jetpackapp.entity.MainDto
import com.example.jetpackapp.utils.LiveDataBus

class MainActivityModel :BaseModel(){

    fun load(){
        LiveDataBus.get().sendLiveData("wt",MainDto("张三"))
    }

    fun getData(){

    }
}