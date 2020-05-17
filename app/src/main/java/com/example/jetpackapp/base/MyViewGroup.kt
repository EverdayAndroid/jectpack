package com.example.jetpackapp.base

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import java.lang.Math.max

/**
  * @date: 2020/5/10
  * @author: wangTao
  * @email: wangtaohandsome@gmail.com
  * @desc:  1.ViewGroup开始测量自己的尺寸
 * 18670301864
 * 2.为每个子View计算测量的限制信息
 * 3.把上一步确定的限制信息，传递给每一个子View，然后子View开始measure自己的尺寸
 * 4.获取子View测量完成后的尺寸
 * 5.ViewGroup根据自身的情况，计算自己的尺寸
 * 6.保存自身的尺寸
 */
class MyViewGroup : ViewGroup {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    )
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //1.测量自身
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //2.为每个子View计算测量的限制信息 Mode / Size
        var widthMode = MeasureSpec.getMode(widthMeasureSpec)
        var widthSize = MeasureSpec.getSize(widthMeasureSpec)

        var heightMode = MeasureSpec.getMode(heightMeasureSpec)
        var heightSize = MeasureSpec.getSize(heightMeasureSpec)
        //3.把上一步确定的限制信息，传递给每一个子View，然后子View开始measure
        //自己的尺寸
        val childCount = childCount
        for (index in 0 until childCount){
            val child = getChildAt(index)
            val layoutParams = child.layoutParams
            //通过父类spec获取child spec
            val childWidthSpec = getChildMeasureSpec(widthMeasureSpec, 0, layoutParams.width)
            val childHeightSpec = getChildMeasureSpec(heightMeasureSpec, 0, layoutParams.height)
            //子View进行测量
            child.measure(childWidthSpec,childHeightSpec)
        }
        var width = 0
        var height = 0
        //4.获取子View测量完成后的尺寸
        //5.ViewGroup根据自身的情况，计算自己的尺寸
        when(widthMode){
            //精准模式
            MeasureSpec.EXACTLY -> width = widthSize
            MeasureSpec.UNSPECIFIED , MeasureSpec.AT_MOST -> {
                for (index in 0 until childCount){
                    val child = getChildAt(index)
                    val widthAddOffset = index*100 + child.measuredWidth
                    getHeight()
                    width = width.coerceAtLeast(widthAddOffset)
                    height += child.measuredHeight
                }
            }
            else -> {

            }
        }
        //6.保存自身的尺寸
        setMeasuredDimension(width,height)
    }


    override fun onLayout(
        changed: Boolean,
        l: Int,
        t: Int,
        r: Int,
        b: Int
    ) {
        //1.遍历子View for
        //2.确定自己的规则
        //3.子View的测量尺寸
        //4.left,top,right,bottom
        //5.child.layout
        var left = 0
        var top = 0
        var right = 0
        var bottom = 0

        val childCount = childCount
        for (index in 0 until  childCount){
            val child = getChildAt(index)
            left = index * 100
            right = left+child.measuredWidth
            bottom = top+child.measuredHeight
            child.layout(left,top,right,bottom)
            top += child.measuredHeight
        }

    }
}