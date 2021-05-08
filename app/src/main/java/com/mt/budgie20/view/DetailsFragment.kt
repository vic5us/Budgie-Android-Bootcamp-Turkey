package com.mt.budgie20.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mt.budgie20.ViewBindingHolder
import com.mt.budgie20.ViewBindingHolderImpl
import com.mt.budgie20.databinding.FragmentDetailsBinding
import com.mt.budgie20.model.Expense
import com.mt.budgie20.viewmodel.MainViewModel
import com.mt.budgie20.viewmodel.MainViewModelFactory
import java.util.*

class DetailsFragment : Fragment(),
    ViewBindingHolder<FragmentDetailsBinding> by ViewBindingHolderImpl() {

    private val detailArgs: DetailsFragmentArgs by navArgs()
    private lateinit var mainViewModel: MainViewModel
    private lateinit var navController: NavController


    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = initBinding(FragmentDetailsBinding.inflate(inflater), this) {
        // Inflate the layout for this fragment

        setViewModel()
        setActionBarAndMenu()

        navController = findNavController()

        var expDet = Expense(0, "", "", 1, "")
        val arg = detailArgs.detailArg
        arg.let { expId ->
            mainViewModel.getExpenseId(expId).observe(viewLifecycleOwner,
                {
                    tvDetailsAmount.text = it.amount.toString() + " " + it.currency.capitalize(
                        Locale.ROOT)
                    tvDetailsDesc.text = it.description
                    val setDraw = when (it.type) {
                        "rent" -> com.mt.budgie20.R.drawable.rent
                        "market" -> com.mt.budgie20.R.drawable.market
                        else -> com.mt.budgie20.R.drawable.others
                    }
                    ivDetails.setImageResource(setDraw)
                    expDet = it!!
                })


        }


        btDel.setOnClickListener {
            mainViewModel.removeExpense(expDet)
            navController.navigate(DetailsFragmentDirections.actionDetailsFragmentToHomeFragment())
        }

    }

    private fun setActionBarAndMenu() {
        val supportActionBar = (activity as AppCompatActivity?)!!.supportActionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.show()
        setHasOptionsMenu(true)
        supportActionBar?.title = ""
    }

    private fun setViewModel() {
        val application = requireNotNull(activity).application
        val mainViewModelFactory = MainViewModelFactory(application)
        mainViewModel = ViewModelProvider(
            requireActivity(),
            mainViewModelFactory
        ).get(MainViewModel::class.java)
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