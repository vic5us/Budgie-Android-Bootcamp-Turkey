package com.mt.budgie20.view

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mt.budgie20.ViewBindingHolder
import com.mt.budgie20.ViewBindingHolderImpl
import com.mt.budgie20.databinding.FragmentProfileBinding
import com.mt.budgie20.model.User
import com.mt.budgie20.viewmodel.MainViewModel
import com.mt.budgie20.viewmodel.MainViewModelFactory

class ProfileFragment : Fragment(),
    ViewBindingHolder<FragmentProfileBinding> by ViewBindingHolderImpl() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var navController: NavController

    private val args: ProfileFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = initBinding(FragmentProfileBinding.inflate(inflater), this) {

        val application = requireNotNull(activity).application
        val mainViewModelFactory = MainViewModelFactory(application)
        mainViewModel = ViewModelProvider(
            requireActivity(),
            mainViewModelFactory
        ).get(MainViewModel::class.java)


        val supportActionBar = (activity as AppCompatActivity?)!!.supportActionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Profile"
        supportActionBar?.show()
        setHasOptionsMenu(true)


        navController = findNavController()
        args.typeArg.let {
            when (it) {
                1 -> rbHe.isChecked = true
                2 -> rbShe.isChecked = true
                3 -> rbThey.isChecked = true
                else -> rbNot.isChecked = true
            }
        }

        args.nameArg.let {
            tilUser.hint = "Change your name"
            etUser.setText(it)
        }

        btSave.setOnClickListener {
            insertUserToDatabase()
        }


    }

    private fun insertUserToDatabase() {
        binding?.apply {
            val name = etUser.text.toString()
            val gender = when (true) {
                rbHe.isChecked -> 1
                rbShe.isChecked -> 2
                rbThey.isChecked -> 3
                else -> 0
            }

            if (inputCheck(name)) {
                val user = User(0, name, gender)
                mainViewModel.insertUser(user)
                Toast.makeText(requireContext(), "Successfully added!!", Toast.LENGTH_SHORT).show()
                navController.navigate(ProfileFragmentDirections.actionProfileFragmentToHomeFragment())
            } else {
                tilUser.error = "Please fill out all fields!!"
                Toast.makeText(requireContext(), "Please fill out all fields!!", Toast.LENGTH_SHORT)
                    .show()

            }

        }
    }

    private fun inputCheck(name: String): Boolean {
        return !(TextUtils.isEmpty(name))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                navController.navigateUp()
            }
        }
        return true
    }


}