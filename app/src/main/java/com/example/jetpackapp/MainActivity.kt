package com.example.jetpackapp

import com.example.jetpackapp.base.BaseActivity
import com.example.jetpackapp.databinding.ActivityMainBinding
import com.example.jetpackapp.model.MainActivityModel

/**
  * @date: 2020/4/20
  * @author: wangTao
  * @email: wangtaohandsome@gmail.com
  * @desc:  描述
 */
class MainActivity : BaseActivity<MainActivityModel,ActivityMainBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun registeredLiveData() {

    }

    override fun initData() {}
}
