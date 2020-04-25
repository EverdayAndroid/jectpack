package com.example.jetpackapp.base

import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.ViewModelStore
import com.example.jetpackapp.app.JetPackApplication
import java.lang.Class as Class

/**
  * @date: 2020/4/19
  * @author: wangTao
  * @email: wangtaohandsome@gmail.com
  * @desc:  ViewMode基类； todo 后期可以进行观察，ViewModelProvider放到Application是否会更好
 */
open class BaseModel:ViewModel() {
    //ViewModelStore职责进行缓存当前ViewModel
    private var shareModel:ViewModelStore = ViewModelStore()
    //替代之前的FragmentActivity和Fragment，使用Application进行
    private var mFactory:ViewModelProvider.Factory = ViewModelProvider.AndroidViewModelFactory.getInstance(JetPackApplication.application.getInstance())

    fun <T : ViewModel> FragmentActivity.createModel(model: Class<T>):T{
        return ViewModelProviders.of(this).get(model)
    }

    fun <T:ViewModel> Fragment.createModel(model: Class<T>):T{
        return ViewModelProviders.of(this).get(model)
    }

    fun init(){

    }

    /**
     * 创建ViewModel
     * 并且进行缓存ViewModel
     * 下次直接进通过key进行获取对应model
     *  查看源码key值为：androidx.lifecycle.ViewModelProvider.DefaultKey:类名
     */
    fun <T:ViewModel> createModel(model: Class<T>):T{
        return  ViewModelProvider(shareModel,mFactory).get(model)
    }

}