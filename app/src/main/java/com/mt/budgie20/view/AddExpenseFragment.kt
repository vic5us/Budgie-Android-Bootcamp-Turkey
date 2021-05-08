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
import androidx.navigation.ui.NavigationUI
import com.mt.budgie20.ViewBindingHolder
import com.mt.budgie20.ViewBindingHolderImpl
import com.mt.budgie20.databinding.FragmentAddExpenseBinding
import com.mt.budgie20.model.Expense
import com.mt.budgie20.viewmodel.MainViewModel
import com.mt.budgie20.viewmodel.MainViewModelFactory


class AddExpenseFragment : Fragment(),
    ViewBindingHolder<FragmentAddExpenseBinding> by ViewBindingHolderImpl() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = initBinding(FragmentAddExpenseBinding.inflate(inflater), this) {

        setViewModel()
        setActionBarAndMenu()
        setNavigation()

        btAddExpense.setOnClickListener {
            insertExpenseToDatabase()
        }
    }

    private fun setNavigation() {
        navController = findNavController()
        NavigationUI.setupActionBarWithNavController(activity as AppCompatActivity, navController)
    }

    private fun setViewModel() {
        val application = requireNotNull(activity).application
        val mainViewModelFactory = MainViewModelFactory(application)
        mainViewModel = ViewModelProvider(
            requireActivity(),
            mainViewModelFactory
        ).get(MainViewModel::class.java)
    }

    private fun setActionBarAndMenu() {
        val supportActionBar = (activity as AppCompatActivity?)!!.supportActionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Add Expense"
        supportActionBar?.show()
        setHasOptionsMenu(true)
    }

    private fun insertExpenseToDatabase() {
        binding?.apply {
            val amount: Long
            if (etExpenseAmount.text?.isNotEmpty()!!) {
                amount = etExpenseAmount.text.toString().toLong()
                val description = etExpenseDesc.text.toString()
                val currency = when (true) {
                    rbTry.isChecked -> "₺"
                    rbUsd.isChecked -> "$"
                    rbEur.isChecked -> "€"
                    rbGbp.isChecked -> "£"
                    else -> ""
                }
                val type = when (true) {
                    rbMarket.isChecked -> "market"
                    rbRent.isChecked -> "rent"
                    rbOther.isChecked -> "others"
                    else -> ""
                }

                if (inputCheck(description, currency, type)) {
                    tiExpenseDesc.error = "Please fill out all fields!!"
                    rbOther.error = ""
                    rbGbp.error = ""
                } else {
                    val expense = Expense(0, type, description, amount, currency)
                    mainViewModel.insertExpense(expense)
                    Toast.makeText(activity, "Expense Added", Toast.LENGTH_SHORT).show()
                    navController.navigate(AddExpenseFragmentDirections.actionAddExpenseFragmentToHomeFragment())
                }
            } else {
                tiExpenseAmount.error = "Please fill out all fields!!"
            }
        }
    }

    private fun inputCheck(
        description: String,
        currency: String,
        type: String,
    ): Boolean {
        return (TextUtils.isEmpty(description) || TextUtils.isEmpty(type) || TextUtils.isEmpty(
            currency
        ))
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