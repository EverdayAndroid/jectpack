package com.example.jetpackapp.base

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.*
import android.view.ViewGroup.LayoutParams
import android.widget.Scroller
import androidx.core.view.ViewConfigurationCompat
import com.example.jetpackapp.R
import kotlin.math.abs

/**
 * @date: 2020/5/15
 * @author: wangTao
 * @email: wangtaohandsome@gmail.com
 * @desc:  自定义流式布局
 * 1.通过View的ScrollBy 和ScrollTo 方法实现滑动
 * 2.通过动画给View添加位移效果实现滑动  （补间动画没有真正改变View位置）
 * 3.通过改变View的layoutparams 让View重新布局从而实现滑动
 */
class FlowLayout : ViewGroup {
    //每一行的子view
    private var lineViews: ArrayList<View>? = null

    //所有的行 一行一行的存储
    private var views: ArrayList<ArrayList<View>>? = null

    //每一行的高度
    private var heights: ArrayList<Int>? = null

    private var mLastX = 0   //记录最后一次坐标
    private var mLastY = 0   //记录最后一次坐标
    private var mTochSlop = 0 //用来判断是不是第一次滑动
    private var scrollable = false  //是否可以进行滑动
    private var measureHeight = 0 //this本身高度
    private var realHeight = 0 //标识内容的高度
    private lateinit var mScroller:Scroller
    private var mMinimumVelocity = 0
    private var mMaximumVelocity = 0
    private var mOverscrollDistance = 0
    //惯性
    private lateinit var mVelocityTracker:VelocityTracker
    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        val configuration = ViewConfiguration.get(context)
        //获取滑动最小距离
        mTochSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration)
        mScroller = Scroller(context)
        mMinimumVelocity = configuration.scaledMinimumFlingVelocity
        mMaximumVelocity = configuration.scaledMaximumFlingVelocity
        mOverscrollDistance = configuration.scaledOverscrollDistance

    }

    private fun initVelocityTrackerIfNotExists(){
        mVelocityTracker = VelocityTracker.obtain()
    }

    private fun recycleVelocityTracker(){
        //回收
        mVelocityTracker?.recycle()
    }

    fun init() {
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

        measureHeight = heightSize
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
        for (index in 0 until childCount) {
            val child = getChildAt(index)
            measureChild(child, widthMeasureSpec, heightMeasureSpec)
            //获取到子View的测量宽高
            var childWidth = child.measuredWidth
            var childHeight = child.measuredHeight

            val layoutParams = child.layoutParams
            //当前的行剩余宽度是否可以放的下，下一个子View放不下换行
            //如果放不下，换行保存当前行的所有子View宽度，和高度
            if (lineWidth + childWidth > widthSize) {
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
            if (index == childCount - 1) {  //处理最后一行的高度
                flowLayoutHeight += lineHeight
                flowLayoutWidth = flowLayoutWidth.coerceAtLeast(lineWidth)
                heights?.add(lineHeight)
                views?.add(lineViews!!)
            }
        }
        //判断FlowLayout宽度模式
        if (widthMode == MeasureSpec.EXACTLY) {
            flowLayoutWidth = widthSize
        }

        //重新测量子view为 match_parent
        remeasureChild(widthMeasureSpec, heightMeasureSpec)
        //如果
        if(heightMode == MeasureSpec.EXACTLY){
            flowLayoutHeight = heightSize.coerceAtLeast(flowLayoutHeight)
        }
        //this本身真实高度
        realHeight = flowLayoutHeight
        //是否可以进行滑动
        scrollable = realHeight > measureHeight
        //FlowLayout最终宽高
        setMeasuredDimension(flowLayoutWidth, realHeight)

    }

    private fun remeasureChild(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val size = views?.size!!
        for (index in 0 until size) {
            val lineHeight = heights?.get(index)
            val lineViews = views?.get(index)
            for (index in 0 until (lineViews?.size ?: 0)) {
                val child = getChildAt(index)
                val layoutParams = child.layoutParams
//                if(child.measuredHeight < desiredHeight)

                val desiredHeight = measuredHeight
                if (layoutParams.height == ViewGroup.LayoutParams.MATCH_PARENT) {
                    val childWidthMeasureSpec =
                        getChildMeasureSpec(widthMeasureSpec, 0, layoutParams.width)
                    val childHeightMeasureSpec =
                        getChildMeasureSpec(heightMeasureSpec, 0, layoutParams.height)
                    //todo 重新测量子View宽高
                    child.measure(childWidthMeasureSpec, childHeightMeasureSpec)
                }
            }

        }
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var left = 0
        var top = 0
        var right = 0
        var bottom = 0
        var currentY = 0  //记录当前y轴的高度
        val lineCount = views?.size
        for (index in 0 until (lineCount ?: 0)) {
            val lineView = views!![index]
            val lineHeight = heights!![index]
            left = 0
            top = currentY
            for (index in 0 until lineView.size) {
                val child = lineView[index]
                right += child.measuredWidth
                bottom = top + child.measuredHeight
                child.layout(left, top, right, bottom)
                left = right
            }
            right = 0
            currentY += lineHeight
        }
    }

    override fun scrollBy(x: Int, y: Int) {
        super.scrollBy(x, y)
    }

    override fun scrollTo(x: Int, y: Int) {
        super.scrollTo(x, y)
    }

    override fun onInterceptTouchEvent(event: MotionEvent?): Boolean {
        var intercept:Boolean = false
        var currentX = event?.x?.toInt()?:0
        var currentY = event?.y?.toInt()?:0

        when(event?.action){
            MotionEvent.ACTION_DOWN ->{
                intercept = false
                mLastX = event.x.toInt()
                mLastY = event.y.toInt()
            }
            MotionEvent.ACTION_MOVE -> {
                var dx = currentX - mLastX
                var dy = currentY - mLastY
                intercept = abs(dx) < abs(dy) && abs(dy) > mTochSlop
            }
            MotionEvent.ACTION_UP -> {
                intercept = false
            }
            MotionEvent.ACTION_CANCEL -> ""
        }
        mLastX = currentX
        mLastY = currentY
        return intercept
    }

    var dy = 0
    var oldMoveLastY = 0
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if(!scrollable){
            return super.onTouchEvent(event)
        }

        var currentY = event?.y?.toInt()?:0
        initVelocityTrackerIfNotExists()
        mVelocityTracker.addMovement(event)
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                //判断如果滑动没有停止，则进行停止处理
                if(!(mScroller?.isFinished)){
                    mScroller?.abortAnimation()
                }
                mLastY = currentY
            }
            MotionEvent.ACTION_MOVE -> {
                dy = mLastY - currentY  //计算偏移数值
                oldMoveLastY = scrollY  //获取当前Y轴数值
                var currentMoveY = oldMoveLastY + dy  //根据Y轴数值+移动偏移量===滑动数值
                when {
                    currentMoveY < 0 -> {
                        currentMoveY = 0
                    }
                    currentMoveY > realHeight-measureHeight -> {
                        currentMoveY = realHeight -measureHeight
                    }
                }

                scrollTo(0, currentMoveY)
                mLastY = currentY
            }
            MotionEvent.ACTION_UP -> {
                //Scroller：通过scroller来实现快速滑动效果
                mScroller?.startScroll(0,mScroller.finalY,0,dy)
                invalidate()
            }
        }
        return super.onTouchEvent(event)
    }

    override fun computeScroll() {
        super.computeScroll()

        mScroller?.let {
            //true标识还在滑动，false标识已停止滑动      2868   1922
            if(it.computeScrollOffset()){
                var currY = mScroller.currY
                if (currY < 0) {
                    currY = 0
                }
                if (currY > realHeight - measureHeight) {
                    currY = realHeight - measureHeight
                }
                Log.e("scroller","$currY")
                scrollTo(it.currX,currY)
                invalidate()
            }
        }
    }


    override fun computeVerticalScrollOffset(): Int {

        val timePassed = mScroller.timePassed()

        return super.computeVerticalScrollOffset()
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