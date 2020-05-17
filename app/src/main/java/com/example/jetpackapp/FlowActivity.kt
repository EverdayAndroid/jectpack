package com.example.jetpackapp

import com.example.jetpackapp.base.BaseActivity
import com.example.jetpackapp.base.BaseModel
import com.example.jetpackapp.databinding.ActivityFlowBinding

class FlowActivity : BaseActivity<BaseModel,ActivityFlowBinding>() {
    override fun getLayoutId(): Int = R.layout.activity_flow

    override fun registeredLiveData() {
    }

    override fun initData() {
    }
}