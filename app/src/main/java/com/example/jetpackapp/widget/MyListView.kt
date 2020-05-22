package com.example.jetpackapp.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.ListView
import kotlin.math.abs

class MyListView : ListView {
    var mLastX:Int = 0
    var mLastY:Int = 0

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        Log.e("MyViewPage=======>WT1",ev.action.toString())
        var currentX = ev.x.toInt()
        var currentY = ev.y.toInt()
        when(ev.action){
            MotionEvent.ACTION_DOWN -> {
                parent.requestDisallowInterceptTouchEvent(true)
            }
            MotionEvent.ACTION_MOVE -> {
                val deltaX = currentX - mLastX
                val deltaY = currentY - mLastY
                if(abs(deltaX) > abs(deltaY)){
                    parent.requestDisallowInterceptTouchEvent(false)
                }
            }
            MotionEvent.ACTION_UP -> {

            }
        }
        mLastX = currentX
        mLastY = currentY
        return super.dispatchTouchEvent(ev)
    }
}