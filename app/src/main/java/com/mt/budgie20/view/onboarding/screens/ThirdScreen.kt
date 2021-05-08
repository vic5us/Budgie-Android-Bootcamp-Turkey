package com.mt.budgie20.view.onboarding.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mt.budgie20.ViewBindingHolder
import com.mt.budgie20.ViewBindingHolderImpl
import com.mt.budgie20.databinding.FragmentThirdScreenBinding
import com.mt.budgie20.view.onboarding.ViewPagerFragmentDirections
import com.mt.budgie20.viewmodel.MainViewModel
import com.mt.budgie20.viewmodel.MainViewModelFactory


class ThirdScreen : Fragment(),
    ViewBindingHolder<FragmentThirdScreenBinding> by ViewBindingHolderImpl() {


    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = initBinding(FragmentThirdScreenBinding.inflate(layoutInflater), this) {

        val application = requireNotNull(activity).application
        val mainViewModelFactory = MainViewModelFactory(application)
        mainViewModel = ViewModelProvider(
            requireActivity(),
            mainViewModelFactory
        ).get(MainViewModel::class.java)

        btThirdScNext.setOnClickListener {
            mainViewModel.saveToDataStore(true)
            findNavController().apply {
                navigate(ViewPagerFragmentDirections.actionViewPagerFragmentToProfileFragment(""))
            }


        }
    }


}