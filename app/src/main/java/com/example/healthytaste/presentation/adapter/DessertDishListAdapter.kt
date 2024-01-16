package com.example.healthytaste.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.healthytaste.databinding.RowDessertDishBinding
import com.example.healthytaste.model.DessertDish

class DessertDishListAdapter :
    RecyclerView.Adapter<DessertDishListAdapter.DessertDishViewHolder>(), Filterable {
    private var dessertDishList: List<DessertDish> = emptyList()
    private var dessertDishListFiltered: List<DessertDish> = dessertDishList
    var onClickListener: (DessertDish) -> Unit = {}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DessertDishViewHolder {
        val binding =
            RowDessertDishBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DessertDishViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dessertDishListFiltered.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: DessertDishViewHolder, position: Int) {
        val item = dessertDishList[position]
        holder.rootView.setOnClickListener {
            onClickListener.invoke(item)
        }
        holder.nameTextView.text = item.name
        holder.numPersonsTextView.text = "Comensales: ${item.numPersons.toString()}"

        Glide.with(holder.secondDishImageView)
            .load(item.image)
            .into(holder.secondDishImageView)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<DessertDish>) {
        dessertDishList = list
        dessertDishListFiltered = list
        notifyDataSetChanged()
    }
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint.toString()
                dessertDishListFiltered = if (charString.isEmpty()) {
                    dessertDishList
                } else {
                    val filteredList = dessertDishList.filter {
                        it.name.contains(charString, ignoreCase = true)
                    }
                    filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = dessertDishListFiltered
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                dessertDishListFiltered = results?.values as List<DessertDish>
                notifyDataSetChanged()
            }
        }
    }
    inner class DessertDishViewHolder(binding: RowDessertDishBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val rootView = binding.root
        val nameTextView = binding.tvDessertDishName
        val secondDishImageView = binding.ivDessertDishPlate
        val numPersonsTextView = binding.tvDessertDishNumPersons
    }
}