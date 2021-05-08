package com.mt.budgie20.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.mt.budgie20.R
import com.mt.budgie20.databinding.FragmentSplashBinding
import com.mt.budgie20.viewmodel.MainViewModel
import com.mt.budgie20.viewmodel.MainViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding
    private lateinit var mainViewModel: MainViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSplashBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        (activity as AppCompatActivity).supportActionBar?.hide()


        val application = requireNotNull(this.activity).application

        val mainViewModelFactory = MainViewModelFactory(application)
        mainViewModel = ViewModelProvider(
            requireActivity(),
            mainViewModelFactory
        ).get(MainViewModel::class.java)

        lifecycleScope.launch {

            binding.tvSplashHead.startAnimation(
                AnimationUtils.loadAnimation(
                    context,
                    R.anim.slide_side
                )
            )
            delay(3000)

            mainViewModel.readStatus.observe(viewLifecycleOwner, {
                if (it.showCompleted) {
                    findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
                    // Toast.makeText(context, "True", Toast.LENGTH_SHORT).show()
                } else {
                    findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToViewPagerFragment())
//                    Toast.makeText(context, "False", Toast.LENGTH_SHORT).show()

                }
            })
        }


        return view
    }


}