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
        super.onInterceptTouchEvent(ev)
        when(ev?.action){
            MotionEvent.ACTION_DOWN -> {
                Log.e("MyViewPage=====>parent","ACTION_DOWN ${MotionEvent.ACTION_DOWN}")
                super.onInterceptHoverEvent(ev)
                return false
            }
            MotionEvent.ACTION_UP -> {
                Log.e("MyViewPage=====>parent","ACTION_UP ${MotionEvent.ACTION_UP}")
            }
            MotionEvent.ACTION_MOVE -> {
                Log.e("MyViewPage=====>parent","ACTION_MOVE ${MotionEvent.ACTION_MOVE}")
            }
            MotionEvent.ACTION_CANCEL -> {
                Log.e("MyViewPage=====>parent","ACTION_CANCEL ${MotionEvent.ACTION_CANCEL}")
            }
        }
        return true
    }

}