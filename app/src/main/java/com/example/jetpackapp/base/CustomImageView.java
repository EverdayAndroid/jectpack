package com.example.jetpackapp.base;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;

import androidx.appcompat.widget.AppCompatImageView;

import com.example.jetpackapp.R;

/**
  * @date: 2020/4/27
  * @author: wangTao
  * @email: wangtaohandsome@gmail.com
  * @desc:  描述  自定义
 */
public class CustomImageView extends AppCompatImageView {
    //圆角值
    public int round;
    //imageView类型 默认是矩形
    private int type;
    private int side;
    private float width,height;
    private Path path;
    public CustomImageView(Context context) {
        super(context);
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomImage);
        round = typedArray.getDimensionPixelSize(R.styleable.CustomImage_round, 10);
        float dimension = typedArray.getDimension(R.styleable.CustomImage_round, 10);
        type = typedArray.getInt(R.styleable.CustomImage_type,3);
        side = typedArray.getDimensionPixelOffset(R.styleable.CustomImage_side,10);
        int side1 = typedArray.getLayoutDimension(R.styleable.CustomImage_side,10);

        Log.e("TAG",String.format("round:%s dimension:%s  side: %s  side1:%s",round,dimension,side,side1));
        typedArray.recycle();
        path = new Path();
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(getDrawable() == null){
            return;
        }
//        path.moveTo(12,0);
//        path.lineTo(width - 12, 0);
//        path.quadTo(width, 0, width, 12);
//        path.lineTo(width, height - 12);
//        path.quadTo(width, height, width - 12, height);
//        path.lineTo(12, height);
//        path.quadTo(0, height, 0, height - 12);
//        path.lineTo(0, 12);
//        path.quadTo(0, 0, 12, 0);
        RectF rectF = new RectF(getLeft(),getTop(),getRight(),getBottom());
        path.addRoundRect(rectF,80,80, Path.Direction.CCW);
//        path.addCircle(width/2,height/2,width/2, Path.Direction.CCW);
        canvas.clipPath(path);
        super.onDraw(canvas);
    }
}
