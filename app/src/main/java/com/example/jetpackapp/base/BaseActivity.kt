package com.example.jetpackapp.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import java.lang.reflect.ParameterizedType

/**
  * @date: 2020/4/20
  * @author: wangTao
  * @email: wangtaohandsome@gmail.com
  * @desc:  描述
 */
abstract class BaseActivity<V: BaseModel,T: ViewDataBinding>:AppCompatActivity() {

    protected lateinit var mModel:Class<V>
    protected lateinit var mBinding:T
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initData()
    }
    //获取视图id
    protected abstract fun getLayoutId():Int

    //监听数据
    protected abstract fun registeredLiveData()

    protected val baseModel:ViewModel by lazy {
        return@lazy BaseModel().createModel(mModel)
    }
    //初始化
    private fun initView(){
        mBinding = DataBindingUtil.setContentView(this,getLayoutId())
        initCustomerStatus()
        getModelClass()
    }
    //初始化数据
    protected abstract fun initData()
    //自定义状态栏
    protected fun initCustomerStatus(){

    }
    /**
     * 获取BaseMode泛型参数
     */
    private fun getModelClass(){
        val genericSuperclass = javaClass.genericSuperclass
        if(genericSuperclass is ParameterizedType){
            val actualTypeArguments = genericSuperclass.actualTypeArguments
            actualTypeArguments.forEach {
                if(it is BaseModel){
                    mModel = it as Class<V>
                }
            }
        }
    }

}