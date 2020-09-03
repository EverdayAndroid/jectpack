package com.example.jetpackapp

import androidx.databinding.ViewDataBinding
import com.example.jetpackapp.base.BaseActivity
import com.example.jetpackapp.base.BaseModel
import kotlinx.android.synthetic.main.activity_dispatch.*

class DispatchActivity : BaseActivity<BaseModel, ViewDataBinding>() {
    override fun getLayoutId(): Int = R.layout.activity_dispatch

    override fun registeredLiveData() {

    }

    override fun initData() {
        btn.setOnClickListener {
            line.scrollBy(0,50)
        }
        btn1.setOnClickListener {
            line.scrollTo(0,50)
        }
    }
}