package com.example.canvas.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.jvm.internal.Ref



/**
  * @date: 2020/5/31
  * @author: wangTao
  * @email: wangtaohandsome@gmail.com
  * @desc:  画布  平移、旋转、缩放、扭曲
 */
class CanvasDrawView:View {
    private var mDensity = 0f
    private lateinit var mPaint:Paint
    private val rect = Rect(0, 0, 300, 300)
    constructor(context:Context):super(context){
        init(context)
    }
    constructor(context: Context,set:AttributeSet):super(context,set){
        init(context)
    }

    private fun init(context:Context){
        val displayMetrics = context.resources.displayMetrics
        mDensity = displayMetrics.density
        mPaint = Paint()
        mPaint.isAntiAlias = true
        mPaint.color = Color.CYAN
//        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = mDensity
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.translate(500f,100f)

        canvas?.drawRect(rect,mPaint)
        //平移
        canvas?.translate(0f,310f)
        //缩放
        canvas?.scale(0.5f,0.5f)
        canvas?.drawRect(rect,mPaint)

        canvas?.translate(0f,310f)

        //旋转
        canvas?.rotate(30f)
        canvas?.drawRect(rect,mPaint)

        canvas?.translate(0f,360f)
        //扭曲
        canvas?.skew(0.5f,0.5f)
        canvas?.drawRect(rect,mPaint)
    }
}