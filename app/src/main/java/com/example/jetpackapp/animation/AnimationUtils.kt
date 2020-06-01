package com.example.jetpackapp.animation

import android.animation.*
import android.view.View

object AnimationUtils {


    //属性动画
    fun getObjectAnimatorByPropertyValuesHolder(mTarget: View):Animator{
        val ofObject = PropertyValuesHolder.ofObject(
            "backgroundColor",
            ArgbEvaluator(),
            0xff009688,
            0xff795548
        )
        val ofFloat = PropertyValuesHolder.ofFloat("rotationX", 0f, 360f)
        val objectAnimator = ObjectAnimator.ofPropertyValuesHolder(mTarget, ofObject, ofFloat)
        objectAnimator.duration = 3000
        objectAnimator.repeatCount = 1
        objectAnimator.repeatMode = ValueAnimator.REVERSE
        return objectAnimator
    }
}