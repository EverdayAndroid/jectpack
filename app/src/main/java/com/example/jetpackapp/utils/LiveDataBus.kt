package com.example.jetpackapp.utils

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
/**
  * @date: 2020/4/20
  * @author: wangTao
  * @email: wangtaohandsome@gmail.com
  * @desc:  修复LiveData数据粘性
 */
class LiveDataBus {
    companion object{
        val liveDataBus = LiveDataBus()
        val map = HashMap<String,BusMutableLiveData<Any>>()
        fun <T> with(key:String,type:Class<T>): BusMutableLiveData<T> {
            var mutableLiveData:BusMutableLiveData<Any>? = null
            if(map.containsKey(key)){
                mutableLiveData= map.get(key)
            }else{
                mutableLiveData = BusMutableLiveData<Any>()
                map.put(key, mutableLiveData)
            }
            return mutableLiveData as BusMutableLiveData<T>
        }
    }


    class BusMutableLiveData<T>: MutableLiveData<T>(){
        override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
            super.observe(owner, observer)
            hook(observer)
        }

        fun hook(observer:Observer<in T>){
            val listDataClass= LiveData::class.java
            Log.e("TAG","listDataClass  ${this::class.java.superclass?.superclass}")
            val mObservers = listDataClass.getDeclaredField("mObservers")
            mObservers.isAccessible = true
            val observers = mObservers.get(this)
            Log.e("TAG","fieldAny   ${observers.toString()}")
            //获取到class类型
            val observersClass = observers::class.java
            //获取到SafeIterableMap类里的get方法，获取到
            val getMethod = observersClass.getDeclaredMethod("get", Any::class.java)
            getMethod.isAccessible = true
//            //执行方法获取
            val objectWrapperEntry = getMethod.invoke(observers, observer)
            var observerWrapper:Any? = null
//            //获取到Map.Entry类型
            if(objectWrapperEntry is Map.Entry<*,*>){
                observerWrapper= objectWrapperEntry.value
            }
            if(observerWrapper == null){
                //todo 提示异常信息
                return
            }
            val wrapperClass = observerWrapper.javaClass.superclass
            Log.e("TAG", "observerWrapperClass   $wrapperClass")
            //获取到mLastVersion
            val fieldLastVersion = wrapperClass?.getDeclaredField("mLastVersion")
            fieldLastVersion?.isAccessible = true
            //获取到mVersion
            val fieldVersion = listDataClass.getField("mVersion")
            fieldVersion.isAccessible = true
            //获取到mVersion值
            val versionVlaue = fieldVersion.get(listDataClass)
            //进行赋值
            fieldLastVersion?.set(observerWrapper,versionVlaue)
        }
    }
}