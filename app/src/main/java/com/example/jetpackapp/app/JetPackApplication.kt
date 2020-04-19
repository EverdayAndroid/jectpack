package com.example.jetpackapp.app

import android.app.Application

/**
  * @date: 2020/4/19
  * @author: wangTao
  * @email: wangtaohandsome@gmail.com
  * @desc:  Application
 */
class JetPackApplication:Application() {
    companion object{
        var application = JetPackApplication()
    }

    override fun onCreate() {
        super.onCreate()
        application = this
    }
    fun getInstance():Application{
        return application
    }
}