package com.example.jetpackapp.base

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import com.example.jetpackapp.R

/**
 * @date: 2020/5/15
 * @author: wangTao
 * @email: wangtaohandsome@gmail.com
 * @desc:  自定义流式布局
 * 1.通过View的ScrollBy 和ScrollTo 方法实现滑动
 * 2.通过动画给View添加位移效果实现滑动  （补间动画没有真正改变View位置）
 * 3.通过改变View的layoutparams 让View重新布局从而实现滑动
 */
class FlowLayout2 : ViewGroup {
    var flowWidth = 0 //记录当前布局最终宽度
    var flowHeight = 0
    var lineWidth = 0  //记录行宽
    var lineHeight = 0 //记录View高

    private lateinit var lineViews:ArrayList<View>
    private lateinit var views:ArrayList<ArrayList<View>>
    private lateinit var lineHeights:ArrayList<Int>
    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {

    }




    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var widthMode = MeasureSpec.getMode(widthMeasureSpec)
        var widthSize = MeasureSpec.getSize(widthMeasureSpec)
        var heightMode = MeasureSpec.getMode(heightMeasureSpec)
        var heightSize = MeasureSpec.getSize(heightMeasureSpec)
        var childCount = childCount
        for (index in 0 until  childCount){
            val child = getChildAt(index)
            measureChild(child,widthMeasureSpec,heightMeasureSpec)
            val childWidth = child.measuredWidth
            val childHeight = child.measuredHeight

            if(lineWidth + childWidth >= widthSize){
                views.add(lineViews)
                lineViews = ArrayList()
                lineHeights.add(lineHeight)
                flowHeight += childHeight
                lineWidth = 0
            }
            lineViews.add(child)
            lineWidth += childWidth
            lineHeight.coerceAtLeast(childHeight)
            if(childCount-1 == index){
                flowHeight += childHeight
                flowWidth = lineWidth.coerceAtLeast(flowWidth)
                lineHeights.add(lineHeight)
                views.add(lineViews)

            }
        }


        if(widthMode == MeasureSpec.EXACTLY){
            flowWidth = widthSize
        }
        if(heightMode == MeasureSpec.EXACTLY){
            flowHeight = heightSize
        }
        setMeasuredDimension(flowWidth,flowHeight)

    }
    //重新测量view宽度模式为match_parent
    private fun remeasureChild(widthMeasureSpec: Int,heightMeasureSpec: Int){
       val size = views.size
        var childWidthMeasureSpec = 0
        var childHeightMeasureSpec = 0
        for (index in 0 until size){
            val lineView = views[index]
            for (index in 0 until lineView.size){
                val child = lineView[index]
                val layoutParams = child.layoutParams
                if(layoutParams.height == ViewGroup.LayoutParams.MATCH_PARENT){
                    childWidthMeasureSpec =
                        getChildMeasureSpec(heightMeasureSpec, 0, layoutParams.height)
                }
                if(layoutParams.width == ViewGroup.LayoutParams.MATCH_PARENT){
                    childHeightMeasureSpec = getChildMeasureSpec(widthMeasureSpec,0,layoutParams.width)
                }
                measure(childHeightMeasureSpec,childWidthMeasureSpec)
            }
        }
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var right = 0
        var left = 0
        var top = 0
        var bottom = 0
        for (index in 0 until views.size){
            val lineView = views[index]
            val lineHeight = lineHeights[index]
            for (index in 0 until lineView.size){
                val child = lineView[index]
                right += child.measuredWidth
                bottom = top + child.measuredHeight
                child.layout(left,top,right,bottom)
                left += right
            }
            top += lineHeight
            right = 0
            left = 0
        }
    }








    override fun generateLayoutParams(p: ViewGroup.LayoutParams?): ViewGroup.LayoutParams {
        return LayoutParams(p)
    }

    override fun generateLayoutParams(attrs: AttributeSet?): ViewGroup.LayoutParams {
        return LayoutParams(context, attrs)
    }

    override fun generateDefaultLayoutParams(): ViewGroup.LayoutParams {
        return LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }

    override fun checkLayoutParams(p: ViewGroup.LayoutParams?): Boolean {
        return super.checkLayoutParams(p) && p is LayoutParams
    }

    internal class LayoutParams : MarginLayoutParams {

        constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
            val attrs = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout_Layout)
            try {
                val gravity = attrs.getInt(R.styleable.FlowLayout_android_gravity, -1)
            } catch (e: Exception) {
                attrs.recycle()
            }
        }

    }


}