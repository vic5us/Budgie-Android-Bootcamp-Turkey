package com.mt.budgie20.view.onboarding.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.mt.budgie20.R
import com.mt.budgie20.ViewBindingHolder
import com.mt.budgie20.ViewBindingHolderImpl
import com.mt.budgie20.databinding.FragmentSecondScreenBinding

class SecondScreen : Fragment(),
ViewBindingHolder<FragmentSecondScreenBinding> by ViewBindingHolderImpl(){


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = initBinding(FragmentSecondScreenBinding.inflate(layoutInflater),this) {
        // Inflate the layout for this fragment
        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)
        btSecScNext.setOnClickListener{
            viewPager?.currentItem = 2

        }
    }

}