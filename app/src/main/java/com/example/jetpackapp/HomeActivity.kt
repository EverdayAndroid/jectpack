package com.example.jetpackapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.jetpackapp.base.BaseActivity
import com.example.jetpackapp.base.BaseModel
import com.example.jetpackapp.databinding.ActivityHomeBinding
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity :BaseActivity<BaseModel, ActivityHomeBinding>(){

    override fun getLayoutId(): Int = R.layout.activity_home

    override fun registeredLiveData() {}

    override fun initData() {
        val list = arrayListOf(HomeFragment(), HomeFragment(),HomeFragment(),HomeFragment())
        viewpage.adapter = ViewPageAdapter(supportFragmentManager,mFragments = list)
    }


    class ViewPageAdapter(fm:FragmentManager,var mFragments:ArrayList<HomeFragment>):
        FragmentPagerAdapter(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
        override fun getItem(position: Int): Fragment {
            return mFragments[position]
        }

        override fun getCount(): Int {
            return 4
        }
    }
}