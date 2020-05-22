package com.example.jetpackapp.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class MyViewPage:ViewPager {
    constructor(context:Context):super(context)
    constructor(context:Context,attrs:AttributeSet):super(context,attrs)

//    override fun onInterceptHoverEvent(event: MotionEvent?): Boolean {
//        when(event?.action){
//            MotionEvent.ACTION_DOWN -> {
//                super.onInterceptHoverEvent(event)
//                return false
//            }
//        }
//        return true
//    }
    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
    Log.e("MyViewPage=======>WT",ev?.action.toString())
        when(ev?.action){
            MotionEvent.ACTION_DOWN -> {
                super.onInterceptHoverEvent(ev)
                return false
            }
        }
        return true
    }

}