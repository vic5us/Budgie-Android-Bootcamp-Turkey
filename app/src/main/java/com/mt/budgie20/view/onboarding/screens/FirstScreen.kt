package com.mt.budgie20.view.onboarding.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.mt.budgie20.R
import com.mt.budgie20.ViewBindingHolder
import com.mt.budgie20.ViewBindingHolderImpl
import com.mt.budgie20.databinding.FragmentFirstScreenBinding


class FirstScreen : Fragment(),
    ViewBindingHolder<FragmentFirstScreenBinding> by ViewBindingHolderImpl() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = initBinding(FragmentFirstScreenBinding.inflate(layoutInflater), this) {
        // Inflate the layout for this
        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)
        btFirstScNext.setOnClickListener{
            viewPager?.currentItem = 1

        }
    }

}