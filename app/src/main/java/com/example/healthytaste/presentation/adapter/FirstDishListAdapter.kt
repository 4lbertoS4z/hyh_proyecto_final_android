package com.example.healthytaste.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.healthytaste.databinding.RowFirstDishBinding
import com.example.healthytaste.model.First

class FirstDishListAdapter : RecyclerView.Adapter<FirstDishListAdapter.FirstDishViewHolder>(),
    Filterable {

    private var firstDishList: List<First> = emptyList()
    private var firstDishListFiltered: List<First> = firstDishList
    var onClickListener: (First) -> Unit = {}

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FirstDishViewHolder {
        val binding =
            RowFirstDishBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FirstDishViewHolder(binding)
    }

    @SuppressLint("SuspiciousIndentation", "SetTextI18n")
    override fun onBindViewHolder(holder: FirstDishViewHolder, position: Int) {
        val item = firstDishListFiltered[position]
        holder.rootView.setOnClickListener { onClickListener.invoke(item) }
        holder.nameTextView.text = item.name
        holder.numPersonsTextView.text = "Comensales: ${item.numPersons.toString()}"
        Glide.with(holder.firstDishImageView.context)
            .load(item.image)
            .into(holder.firstDishImageView)
    }


    override fun getItemCount(): Int {
        return firstDishListFiltered.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<First>) {
        firstDishList = list
        firstDishListFiltered = list
        notifyDataSetChanged()
    }
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint.toString()
                firstDishListFiltered = if (charString.isEmpty()) {
                    firstDishList
                } else {
                    val filteredList = firstDishList.filter {
                        it.name.contains(charString, ignoreCase = true)
                    }
                    filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = firstDishListFiltered
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                firstDishListFiltered = results?.values as List<First>
                notifyDataSetChanged()
            }
        }
    }
    inner class FirstDishViewHolder(binding: RowFirstDishBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val rootView = binding.root
        val nameTextView = binding.tvFirstDishName
        val firstDishImageView = binding.ivFirstDishPlate
        val numPersonsTextView = binding.tvFirstDishNumPersons
    }
}