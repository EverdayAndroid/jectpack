package com.example.jetpackapp

import android.content.Intent
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.lifecycle.Observer
import com.example.jetpackapp.animation.AnimationUtils
import com.example.jetpackapp.base.BaseActivity
import com.example.jetpackapp.databinding.ActivityMainBinding
import com.example.jetpackapp.entity.MainDto
import com.example.jetpackapp.model.MainActivityModel
import com.example.jetpackapp.utils.LiveDataBus
import kotlinx.android.synthetic.main.activity_main.*

/**
  * @date: 2020/4/20
  * @author: wangTao
  * @email: wangtaohandsome@gmail.com
  * @desc:  描述
 */
class MainActivity : BaseActivity<MainActivityModel,ActivityMainBinding>(),View.OnClickListener {

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun registeredLiveData() {
        mBinding.listene = this
        LiveDataBus.get().with("wt",MainDto::class.java)
            .observe(this, Observer {
                Log.e("TAG","==============="+it.name)
                mBinding.mainDto = it
            })
    }

    override fun initData() {
        mModel.load()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }
    
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn -> {
//                AnimationUtils.getObjectAnimatorByPropertyValuesHolder(btn).start()
//                val currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis()
                Log.e("TAG",mBinding.mainDto!!.name)
//                Log.e("TAG","$currentAnimationTimeMillis")
                startActivity(Intent(this,FlowActivity::class.java))
//                startActivity(Intent(this,HomeActivity::class.java))
            }
        }
    }
}
