package com.example.canvas.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
/**
  * @date: 2020/5/31
  * @author: wangTao
  * @email: wangtaohandsome@gmail.com
  * @desc:  画布  保存和回滚方法 save、restore
 */
class CanvasSaveView:View {
    private var mDensity = 0f
    private lateinit var mPaint:Paint
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
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)


        canvas?.drawColor(Color.CYAN)
        canvas?.save()

        canvas?.clipRect(RectF(100f,100f,800f,800f))
        canvas?.drawColor(Color.DKGRAY)
        canvas?.save()

        canvas?.clipRect(RectF(200f,200f,700f,700f))
        canvas?.drawColor(Color.LTGRAY)
        canvas?.save()


        canvas?.clipRect(RectF(300f,300f,600f,600f))
        canvas?.drawColor(Color.WHITE)
        canvas?.save()


        //回滚上一次save之前的画布
//        canvas?.restore()

        canvas?.drawColor(Color.RED)
    }
}