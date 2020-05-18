package com.example.jetpackapp.base

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.jetpackapp.R
import java.lang.Exception

/**
  * @date: 2020/5/15
  * @author: wangTao
  * @email: wangtaohandsome@gmail.com
  * @desc:  自定义流式布局
 */
class FlowLayout : ViewGroup {
    //每一行的子view
    private var lineViews:ArrayList<View> ? = null
    //所有的行 一行一行的存储
    private var views:ArrayList<ArrayList<View>>? = null
    //每一行的高度
    private var heights:ArrayList<Int>? = null

    constructor(context:Context):super(context)

    constructor(context: Context,attributeSet: AttributeSet):super(context,attributeSet){


    }

    fun init(){
        lineViews = ArrayList()
        views = ArrayList()
        heights = ArrayList()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var widthMode = MeasureSpec.getMode(widthMeasureSpec)
        var widthSize = MeasureSpec.getSize(widthMeasureSpec)
        var heightMode = MeasureSpec.getMode(heightMeasureSpec)
        var heightSize = MeasureSpec.getSize(heightMeasureSpec)

        //记录当前行的宽度和高度
        var lineWidth = 0
        var lineHeight = 0
        //整个流式布局的宽度和高度
        var flowLayoutWidth = 0
        var flowLayoutHeight = 0

        //初始化
        init()

        val childCount = childCount

        //遍历所有的子view，对子view进行测量，分配到具体的行
        for (index in 0 until childCount){
            val child = getChildAt(index)
            measureChild(child,widthMeasureSpec,heightMeasureSpec)
            //获取到子View的测量宽高
            var childWidth = child.measuredWidth
            var childHeight = child.measuredHeight

            val layoutParams = child.layoutParams
            //当前的行剩余宽度是否可以放的下，下一个子View放不下换行
            //如果放不下，换行保存当前行的所有子View宽度，和高度
            if(lineWidth + childWidth > widthSize){
                views?.add(lineViews!!)
                lineViews = ArrayList()
                flowLayoutWidth = flowLayoutWidth.coerceAtLeast(lineWidth)
                flowLayoutHeight += lineHeight
                heights?.add(lineHeight)
                lineWidth = 0
                lineHeight = 0
            }
            lineViews?.add(child)
            lineWidth += childWidth
            lineHeight = lineHeight.coerceAtLeast(childHeight)
            if(index == childCount-1){  //处理最后一行的高度
                flowLayoutHeight += lineHeight
                flowLayoutWidth = flowLayoutWidth.coerceAtLeast(lineWidth)
                heights?.add(lineHeight)
                views?.add(lineViews!!)
            }
        }
        //判断FlowLayout宽度模式
        if(widthMode == MeasureSpec.EXACTLY){
            flowLayoutWidth = widthSize
        }
        //判断FlowLayout高度模式
        if(heightMode == MeasureSpec.EXACTLY){
            flowLayoutHeight = heightSize
        }
        //FlowLayout最终宽高
        setMeasuredDimension(flowLayoutWidth,flowLayoutHeight)



    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var left = 0
        var top = 0
        var right = 0
        var bottom = 0
        var currentY = 0  //记录当前y轴的高度
        val lineCount = views?.size
        for (index in 0 until (lineCount?:0)){
            val lineView = views!![index]
            val lineHeight = heights!![index]
            left = 0
            top += currentY
            for (index in 0 until lineView.size){
                val child = lineView[index]
                right += child.measuredWidth
                bottom = top+child.measuredHeight
                child.layout(left,top,right,bottom)
                left = right
            }
            right = 0
            currentY += lineHeight


        }
    }


    override fun generateLayoutParams(p: ViewGroup.LayoutParams?): ViewGroup.LayoutParams {
        return LayoutParams(p)
    }

    override fun generateLayoutParams(attrs: AttributeSet?): ViewGroup.LayoutParams {
        return LayoutParams(context,attrs)
    }

    override fun generateDefaultLayoutParams(): ViewGroup.LayoutParams {
        return LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
    }

    override fun checkLayoutParams(p: ViewGroup.LayoutParams?): Boolean {
        return super.checkLayoutParams(p) && p is LayoutParams
    }

    internal class LayoutParams:MarginLayoutParams{

        constructor(context: Context,attrs: AttributeSet):super(context,attrs){
            val attrs = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout_Layout)
            try {
                val gravity = attrs.getInt(R.styleable.FlowLayout_android_gravity,-1)
            }catch (e:Exception){
                attrs.recycle()
            }
        }

    }


}