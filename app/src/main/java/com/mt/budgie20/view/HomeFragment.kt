package com.mt.budgie20.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mt.budgie20.R
import com.mt.budgie20.ViewBindingHolder
import com.mt.budgie20.ViewBindingHolderImpl
import com.mt.budgie20.adapter.RecyclerAdapter
import com.mt.budgie20.databinding.FragmentHomeBinding
import com.mt.budgie20.viewmodel.MainViewModel
import com.mt.budgie20.viewmodel.MainViewModelFactory
import java.util.*


class HomeFragment : Fragment(),
    ViewBindingHolder<FragmentHomeBinding> by ViewBindingHolderImpl() {


    private lateinit var mainViewModel: MainViewModel
    private lateinit var mAdapter: RecyclerAdapter
    private var tvUser: String = ""
    private var tvUserType = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = initBinding(FragmentHomeBinding.inflate(inflater), this) {

        (activity as AppCompatActivity?)!!.supportActionBar?.hide()

        val application = requireNotNull(activity).application
        val mainViewModelFactory = MainViewModelFactory(application)
        mainViewModel = ViewModelProvider(
            requireActivity(),
            mainViewModelFactory
        ).get(MainViewModel::class.java)


        val navController = findNavController()
        bottomNavigation.setupWithNavController(navController)
        fab.setOnClickListener {
            navController.navigate(HomeFragmentDirections.actionHomeFragmentToAddExpenseFragment())
        }

        mAdapter = RecyclerAdapter(mutableListOf(), mutableListOf(), mutableListOf()) { item ->
            navController.navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment(item.expenseId))
        }
        recyclerView.adapter = mAdapter
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)


        dismissKeyboard(requireActivity())
        observers()
        getRates()

        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.profileFragment -> {
                    navController.navigate(
                        HomeFragmentDirections.actionHomeFragmentToProfileFragment(
                            tvUser, tvUserType
                        )
                    )
                    true
                }
                R.id.homeFragment -> {
                    navController.navigate(HomeFragmentDirections.actionHomeFragmentSelf())
                    true
                }
                else -> false
            }
        }

    }

    private fun getRates() {
        mainViewModel.apply {
            getRate("EUR_TRY")
            getRate("GBP_TRY")
            getRate("USD_TRY")
            getRate("EUR_USD")
            getRate("EUR_GBP")
            getRate("USD_GBP")
        }
    }

    override fun onResume() {
        super.onResume()
        observers()

        binding!!.apply {

            bottomNavigation.menu.getItem(0).isChecked = true
        }
    }

    private fun isOnline() : Boolean {
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        return isConnected
    }

    @SuppressLint("SetTextI18n")
    private fun observers() {
        binding!!.apply {
            mainViewModel.apply {
                getAllUser.observe(viewLifecycleOwner, {
                    it?.let {
                        tvUser = it[0].name
                        tvUserType = it[0].gender
                        tvUserName.text = "Hello " + when (tvUserType) {
                            1 -> "Mr. "
                            2 -> "Ms. "
                            else -> ""
                        } + tvUser.capitalize(Locale.ROOT)
                    }
                })
                getAllExpense.observe(viewLifecycleOwner, {
                    it?.let { exp ->
                        mAdapter.updateDataList(
                            exp,
                            listOf(1.0, 1.0, 1.0, 1.0),
                            listOf("", "", "", "")
                        )
                        readAllRates.observe(viewLifecycleOwner, { rates ->
                            chipAll.setOnClickListener {
                                mAdapter.updateDataList(
                                    exp,
                                    listOf(1.0, 1.0, 1.0, 1.0),
                                    listOf("", "", "", "")
                                )
                            }
                            chipTry.setOnClickListener {
                                if (isOnline())
                                {
                                    mAdapter.updateDataList(
                                        exp,
                                        listOf(
                                            1.0,
                                            rates[0].rateValue,
                                            rates[1].rateValue,
                                            rates[2].rateValue
                                        ),
                                        listOf("₺", "₺", "₺", "₺")
                                    )
                                }else{
                                    Toast.makeText(context, "Connect to the internet first", Toast.LENGTH_SHORT).show()
                                }
                            }
                            chipEur.setOnClickListener {
                                if(isOnline()){
                                    mAdapter.updateDataList(
                                        exp,
                                        listOf(
                                            1.0.div(rates[0].rateValue),
                                            1.0,
                                            1.0.div(rates[3].rateValue),
                                            1.0.div(rates[4].rateValue)
                                        ),
                                        listOf("€", "€", "€", "€")
                                    )
                                }else{
                                    Toast.makeText(context, "Connect to the internet first", Toast.LENGTH_SHORT).show()
                                }

                            }
                            chipUsd.setOnClickListener {
                                if(isOnline()) {
                                    mAdapter.updateDataList(
                                        exp,
                                        listOf(
                                            1.0.div(rates[1].rateValue),
                                            rates[3].rateValue,
                                            1.0,
                                            1.0.div(rates[5].rateValue)
                                        ),
                                        listOf("$", "$", "$", "$")
                                    )
                                }else{
                                    Toast.makeText(context, "Connect to the internet", Toast.LENGTH_SHORT).show()
                                }
                            }
                            chipGbp.setOnClickListener {
                                if (isOnline()){
                                mAdapter.updateDataList(
                                    exp,
                                    listOf(
                                        1.0.div(rates[2].rateValue),
                                        rates[4].rateValue,
                                        rates[5].rateValue,
                                        1.0
                                    ),
                                    listOf("£", "£", "£", "£")
                                )}else{
                                    Toast.makeText(context, "Connect to the internet", Toast.LENGTH_SHORT).show()
                                }
                            }
                        })
                    }
                })
            }
        }
    }

    private fun dismissKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (null != activity.currentFocus) imm.hideSoftInputFromWindow(
            activity.currentFocus!!.applicationWindowToken, 0
        )
    }


}