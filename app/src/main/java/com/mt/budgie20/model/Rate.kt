package com.mt.budgie20.model

import com.google.gson.annotations.SerializedName

data class Rate(
    @SerializedName(
        value = "USD_TRY",
        alternate = arrayOf(
            "GBP_TRY",
            "EUR_TRY",
            "EUR_USD",
            "EUR_GBP",
            "USD_GBP"
        )
    )
    val rate: Double
)