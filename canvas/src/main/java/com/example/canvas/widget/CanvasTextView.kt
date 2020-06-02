package com.example.canvas.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
/**
  * @date: 2020/5/31
  * @author: wangTao
  * @email: wangtaohandsome@gmail.com
  * @desc:  画布  绘制文字
 */
class CanvasTextView:View {
    private var mDensity = 0f
    private lateinit var mPaint:Paint
    private var msg:String = "风萧萧易水寒，壮士一去不复返"
    constructor(context: Context):super(context){
        init()
    }
    constructor(context: Context,set:AttributeSet):super(context,set){
        init()
    }

    private fun init(){
        val displayMetrics = context.resources.displayMetrics
        mDensity = displayMetrics.density
        mPaint = Paint()
        mPaint.color = Color.LTGRAY
        mPaint.isAntiAlias = true
        mPaint.strokeWidth = mDensity
        mPaint.textSize = 20*mDensity
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        mPaint.setShadowLayer(5f,0f,0f,Color.CYAN)
//        mPaint.shader = SweepGradient(20f, 10f, Color.YELLOW, Color.RED)
        val measureText = mPaint.measureText(msg)
        canvas?.drawText(msg,100f,100f,mPaint)
    }
}