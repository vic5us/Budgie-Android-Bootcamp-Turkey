package com.mt.budgie20.api

import com.mt.budgie20.model.Rate
import com.mt.budgie20.util.Constant
import com.mt.budgie20.util.Constant.Companion.API_KEY
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyService {

    @GET("v7/convert")
    fun getTryToUsd(
        @Query("q")
        q: String = "TRY_USD",
        @Query("compact")
        compact : String = "ultra",
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Call<Rate>


    companion object {

        private var retrofit: Retrofit? = null

        fun getClient(): Retrofit {
            if (retrofit == null)
                retrofit =
                    Retrofit.Builder()
                        .baseUrl(Constant.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()

            return retrofit as Retrofit
        }
    }

}