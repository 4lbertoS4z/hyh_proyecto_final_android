package com.example.healthytaste.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.healthytaste.databinding.RowSecondDishBinding
import com.example.healthytaste.model.SecondDish

class SecondDishLlistAdapter : RecyclerView.Adapter<SecondDishLlistAdapter.SecondDishViewHolder>(), Filterable {
    private var secondDishList: List<SecondDish> = emptyList()
    private var secondDishListFiltered: List<SecondDish> = secondDishList
    var onClickListener: (SecondDish) -> Unit = {}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SecondDishViewHolder {
        val binding =
            RowSecondDishBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SecondDishViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return secondDishListFiltered.size
    }

    override fun onBindViewHolder(holder: SecondDishViewHolder, position: Int) {
        val item = secondDishListFiltered[position]
        holder.rootView.setOnClickListener {
            onClickListener.invoke(item)
        }
        holder.nameTextView.text = item.name

        Glide.with(holder.secondDishImageView)
            .load(item.image)
            .into(holder.secondDishImageView)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<SecondDish>) {
        secondDishList = list
        secondDishListFiltered = list
        notifyDataSetChanged()
    }
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint.toString()
                secondDishListFiltered = if (charString.isEmpty()) {
                    secondDishList
                } else {
                    val filteredList = secondDishList.filter {
                        it.name.contains(charString, ignoreCase = true)
                    }
                    filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = secondDishListFiltered
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                secondDishListFiltered = results?.values as List<SecondDish>
                notifyDataSetChanged()
            }
        }
    }
    inner class SecondDishViewHolder(binding: RowSecondDishBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val rootView = binding.root
        val nameTextView = binding.tvSecondDishName
        val secondDishImageView = binding.ivSecondDishPlate
    }
}