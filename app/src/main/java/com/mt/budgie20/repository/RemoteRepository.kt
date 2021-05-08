package com.mt.budgie20.repository

import android.util.Log
import com.mt.budgie20.api.CurrencyService
import com.mt.budgie20.model.Rate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteRepository {

    private var service: CurrencyService =
        CurrencyService.getClient().create(CurrencyService::class.java)

    fun getTryToUsd(
        rateType: String,
        onResult: (isSuccess: Boolean, response: Rate?) -> Unit
    ) =
        service.getTryToUsd(q = rateType).enqueue(object : Callback<Rate> {
            override fun onResponse(call: Call<Rate>, response: Response<Rate>) {
                if (response != null && response.isSuccessful) {
                    onResult(true,response.body())
                } else
                    onResult(false, null)
            }

            override fun onFailure(call: Call<Rate>, t: Throwable) {
                Log.e("ApiHata", t.localizedMessage.toString())
            }

        })
}