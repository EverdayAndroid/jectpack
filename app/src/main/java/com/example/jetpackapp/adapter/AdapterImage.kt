package com.example.jetpackapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.jetpackapp.R

class AdapterImage :BaseAdapter{
    var  layoutInflater: LayoutInflater? = null
    var context:Context? = null
    constructor(context:Context){
        layoutInflater = LayoutInflater.from(context)
        this.context = context
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = layoutInflater!!.inflate(R.layout.item_image, parent, false)
        var iv =  view.findViewById<ImageView>(R.id.iv)
        Glide.with(context!!).load("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1590163242&di=3aa9f825d1c50b8f789276a90f4cbfd5&src=http://pic.feizl.com/upload/allimg/170615/19502QV8-6.jpg").into(iv)
        return view
    }

    override fun getItem(position: Int): Any {
        return ""
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return 10
    }
}