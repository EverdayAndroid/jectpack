package com.example.jetpackapp.utils

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager
import com.example.jetpackapp.app.JetPackApplication

/**
  * @date: 2020/5/6
  * @author: wangTao
  * @email: wangtaohandsome@gmail.com
  * @desc:  获取手机分辨率
 */
object DisplayUtils {
    /**
     * 获取手机分辨率宽度
     * @return widthPixels
     */
    fun getWidthPixels():Int{
        val displayMetrics =
            JetPackApplication.application.applicationContext.resources.displayMetrics
        return displayMetrics.widthPixels
    }
    /**
     * 获取手机分辨率高度
     * @return heightPixels
     */
    fun getHeightPixels():Int{
        val context = JetPackApplication.application.applicationContext
        val systemService = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        var displayMetrics = DisplayMetrics()
        systemService.defaultDisplay.getMetrics(displayMetrics)
//        val displayMetrics =
//            JetPackApplication.application.applicationContext.resources.displayMetrics

        return displayMetrics.heightPixels
    }

    /**
     * 获取手机底部虚拟导航栏高度
     * @return height
     */
    fun getNavigationBarHeight():Int{
        val resources = JetPackApplication.application.applicationContext.resources
        var resourceId= resources.getIdentifier("navigation_bar_height","dimen","android");
        var height = resources.getDimensionPixelSize(resourceId);
        return height
    }
}