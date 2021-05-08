package com.mt.budgie20.view.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mt.budgie20.ViewBindingHolder
import com.mt.budgie20.ViewBindingHolderImpl
import com.mt.budgie20.adapter.ViewPagerAdapter
import com.mt.budgie20.databinding.FragmentViewPagerBinding
import com.mt.budgie20.view.onboarding.screens.FirstScreen
import com.mt.budgie20.view.onboarding.screens.SecondScreen
import com.mt.budgie20.view.onboarding.screens.ThirdScreen


class ViewPagerFragment() : Fragment(),
    ViewBindingHolder<FragmentViewPagerBinding> by ViewBindingHolderImpl() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = initBinding(FragmentViewPagerBinding.inflate(layoutInflater), this) {

        val fragmentList = arrayListOf<Fragment>(
            FirstScreen(),
            SecondScreen(),
            ThirdScreen()
        )

        val adapter =
            ViewPagerAdapter(fragmentList, requireActivity().supportFragmentManager, lifecycle)
        binding?.viewPager?.adapter = adapter
    }


}