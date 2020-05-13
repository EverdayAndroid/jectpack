package com.example.jetpackapp.base

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.example.jetpackapp.R
import com.example.jetpackapp.utils.DisplayUtils
import kotlin.math.abs

/**
  * @date: 2020/5/1
  * @author: wangTao
  * @email: wangtaohandsome@gmail.com
  * @desc:  自定义拖动view
 */
class CustomView : View {
    //默认半径
    private val DEFAULT_RADIUS = 10
    //默认边框
    private val DEFAULT_BORDER_WIDTH = 0f
    //默认背景颜色
    private val DEFAULT_BACKGROUND_COLOR = Color.TRANSPARENT
    private val DEFAULT_STYLE = Paint.Style.STROKE
    private var style_defult = DEFAULT_STYLE
    //半径
    private var radius: Int = 0
    //画布颜色
    private var circle_color: Int = 0
    private var background_Color: Int = 0
    //边框宽度
    private var borderWidth:Float = 0f

    private var style_enum = 0

    private var custome_widht:Float = 0f
    private var custome_height:Float = 0f
    //圆圈画笔
    private var mPaint = Paint()
    //文字画笔
    private var mTextPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var textMsgSize = 0f
    private var textMsgColor = 0
    private var textMsgBold = 0f
    //意图：当前为点击事件还是移动事件；默认为点击事件
    private var intend = true

    private var downX:Float = 0f  //点击时x坐标
    private var downY:Float = 0f  //点击时y坐标
    private var textMsg = "Everday"
    //文字宽度
    private var textMsgWidth = 0
    //文字高度
    private var textMsgHeight = 0
    val recf = Rect()
    private var maxWidth:Int = 0
    private var maxHeight:Int = 0
    private var maxTop:Int = 0
    constructor(context: Context)
            : super(context) {
            initPaint()
    }
    constructor(context: Context, attrs: AttributeSet)
            : super(context, attrs) {
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.CustomView)
        background_Color = typeArray.getColor(R.styleable.CustomView_backgroundColor,DEFAULT_BACKGROUND_COLOR)
        circle_color = typeArray.getColor(R.styleable.CustomView_circle_color,Color.RED)
        radius = typeArray.getDimensionPixelSize(R.styleable.CustomView_radius,DEFAULT_RADIUS)
        borderWidth = typeArray.getDimension(R.styleable.CustomView_border_width,DEFAULT_BORDER_WIDTH)
        style_enum = typeArray.getInt(R.styleable.CustomView_style_enum,0)
        textMsg = typeArray.getNonResourceString(R.styleable.CustomView_text)
        textMsgColor = typeArray.getColor(R.styleable.CustomView_textColor,Color.WHITE)
        textMsgSize = typeArray.getDimension(R.styleable.CustomView_textSize,14f)
        textMsgBold = typeArray.getDimension(R.styleable.CustomView_textBold,1f)
        typeArray.recycle()
        initPaint()
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ):super(context, attrs, defStyleAttr) {

    }

    private fun initPaint(){
        maxWidth = context.resources.displayMetrics.widthPixels
        maxHeight = DisplayUtils.getHeightPixels()-DisplayUtils.getNavigationBarHeight()-DisplayUtils.getStatusBarHeight()
        maxTop = top
        mPaint.color = circle_color
        mPaint.strokeWidth = borderWidth
        when(style_enum){
            0 -> style_defult = Paint.Style.FILL
            1 -> style_defult = Paint.Style.STROKE
            2 -> style_defult = Paint.Style.FILL_AND_STROKE
            else -> style_defult = Paint.Style.STROKE
        }
        mPaint.style = style_defult
        mPaint.alpha = 50
        mPaint.isAntiAlias = true

        mTextPaint.textSize = textMsgSize
        mTextPaint.color = textMsgColor
        mTextPaint.strokeWidth = textMsgBold
        mTextPaint.style = Paint.Style.FILL_AND_STROKE
        //测量文字宽度
        textMsgWidth = mTextPaint.measureText(textMsg).toInt()
        val recf = Rect()
        mTextPaint.getTextBounds(textMsg,0,textMsg.length,recf)
        textMsgWidth = recf.width()
        textMsgHeight = recf.height()
    }



    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        custome_widht = w.toFloat()
        custome_height = h.toFloat()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Log.e("TAG","onDraw")
        val viewX = -custome_widht/2
        val viewY = -custome_height/2
        canvas?.translate(viewX,viewY)
//        canvas?.drawColor(background_Color)
        canvas?.drawCircle(custome_widht,custome_height,radius.toFloat(),mPaint)
//        canvas?.drawText(textMsg,left+radius-(textMsgWidth/2),right-radius+(textMsgWidth/2),mTextPaint)
        canvas?.drawText(textMsg,width-(textMsgWidth/2).toFloat(),height+(textMsgHeight/2).toFloat(),mTextPaint)
//        background?.let {
//            canvas?.drawCircle(custome_widht,custome_height,custome_widht/2-2,Paint())
//        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Log.e("TAG","onMeasure")
    }
    override fun layout(l: Int, t: Int, r: Int, b: Int) {
        super.layout(l, t, r, b)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        when(event?.action){
            MotionEvent.ACTION_DOWN -> down(event)
            MotionEvent.ACTION_MOVE -> move(event)
            MotionEvent.ACTION_UP -> up()
        }
        return intend
    }

    /**
     * 当手指按下去获取当前view
     * 坐标系
     */
    private fun down(event: MotionEvent){
        downX = event.x
        downY = event.y
        intend = true
    }
    var l = 0
    var t = 0
    var r = 0
    var b = 0
    private fun move(event: MotionEvent){
        var moveX = event.x-downX
        var moveY = event.y-downY
        if(abs(moveX) > 3 || abs(moveY) > 3){
            intend = false

            l = if((left + moveX).toInt() < 0) 0 else if(maxWidth-(left + moveX).toInt() < width) maxWidth-width else (left + moveX).toInt()
            t = if((top + moveY).toInt() < maxTop) maxTop else (top + moveY).toInt()
            r = if((l+custome_widht).toInt() > maxWidth) maxWidth else (l+custome_widht).toInt()
            b = (t+custome_height).toInt()
            if(b > maxHeight){
                b = maxHeight
                t = maxHeight-custome_height.toInt()
                Log.e("TAG","t=$t  getBottom=$bottom")
            }
            layout(l,t,r,b)
        }else{
            intend = true
        }
    }

    private fun up(){
        intend = true
    }

}