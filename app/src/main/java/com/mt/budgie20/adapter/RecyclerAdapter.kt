package com.mt.budgie20.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.mt.budgie20.R
import com.mt.budgie20.databinding.ItemRowBinding
import com.mt.budgie20.model.Expense

class RecyclerAdapter(
    var typeList: MutableList<String>,
    var rateList: MutableList<Double>,
    var expenseList: MutableList<Expense>,
    val listener: (Expense) -> Unit
) :
    RecyclerView.Adapter<RecyclerAdapter.ModelViewHolder>() {

    class ModelViewHolder(var binding: ItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ModelViewHolder {
        val binding = ItemRowBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ModelViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ModelViewHolder, position: Int) {
        with(holder) {
            with(expenseList[position]) {
                binding.tvItemRowAmount.text = when (this.currency) {
                    "₺" -> {
                        String.format(
                            "%.2f",
                            this.amount * rateList[0]
                        ) + if (typeList[0].isEmpty()) this.currency else typeList[0]
                    }
                    "€" -> {
                        String.format(
                            "%.2f",
                            this.amount * rateList[1]
                        ) + if (typeList[1].isEmpty()) this.currency else typeList[1]
                    }
                    "$" -> {
                        String.format(
                            "%.2f",
                            this.amount * rateList[2]
                        ) + if (typeList[2].isEmpty()) this.currency else typeList[2]
                    }
                    "£" -> {
                        String.format(
                            "%.2f",
                            this.amount * rateList[3]
                        ) + if (typeList[3].isEmpty()) this.currency else typeList[3]
                    }
                    else -> ""
                }
                binding.tvItemRowDesc.text = this.description
                val setDraw = when (this.type) {
                    "rent" -> R.drawable.rent
                    "market" -> R.drawable.market
                    else -> R.drawable.others
                }
                binding.ivItemRowType.setImageResource(setDraw)

                itemView.setOnClickListener { listener(this) }

            }
        }
    }

    override fun getItemCount() = expenseList.size

    fun updateDataList(
        newDataList: List<Expense>,
        newRateList: List<Double>,
        newTypeList: List<String>
    ) {
        typeList.clear()
        typeList.addAll(newTypeList)
        rateList.clear()
        rateList.addAll(newRateList)
        expenseList.clear()
        expenseList.addAll(newDataList)
        notifyDataSetChanged()
    }


}