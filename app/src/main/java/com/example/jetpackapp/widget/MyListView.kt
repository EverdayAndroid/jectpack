package com.example.jetpackapp.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.ListView
import com.google.gson.Gson
import kotlin.math.abs

class MyListView : ListView {
    //记录用户第一次触碰屏幕坐标点
    var mLastX:Int = 0
    var mLastY:Int = 0

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
        val gson = Gson()
            .newBuilder()
            .serializeNulls()
            .create()
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        //实时更新用户操作屏幕的坐标x,y 轴
        var currentX = ev.x.toInt()
        var currentY = ev.y.toInt()

        when(ev.action){
            MotionEvent.ACTION_DOWN -> {
                Log.e("MyViewPage=======>child","ACTION_DOWN ${MotionEvent.ACTION_DOWN}")
                //禁止父控件处理事件
                parent.requestDisallowInterceptTouchEvent(true)
            }
            MotionEvent.ACTION_MOVE -> {
                Log.e("MyViewPage=======>child","ACTION_MOVE ${MotionEvent.ACTION_MOVE}")
                val deltaX = currentX - mLastX
                val deltaY = currentY - mLastY
                if(abs(deltaX) > abs(deltaY)){  //横向滑动
                    Log.e("MyViewPage=====>child","currentx:$currentX - $mLastX  currentY$currentY  - $mLastY " +
                            "   deltaX: ${abs(deltaX)} == $deltaX    deltaY:$deltaY")
                    //交给父控件处理事件
                    parent.requestDisallowInterceptTouchEvent(false)
                }
            }
            MotionEvent.ACTION_UP -> {
                Log.e("MyViewPage=======>child","ACTION_UP ${MotionEvent.ACTION_UP}")
            }
            MotionEvent.ACTION_CANCEL -> {
                Log.e("MyViewPage=======>child","ACTION_CANCEL ${MotionEvent.ACTION_CANCEL}")
            }
        }
        mLastX = currentX
        mLastY = currentY
        return super.dispatchTouchEvent(ev)
    }
}