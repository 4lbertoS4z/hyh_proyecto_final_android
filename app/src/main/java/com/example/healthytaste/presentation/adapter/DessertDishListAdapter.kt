package com.example.healthytaste.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.healthytaste.databinding.RowDessertDishBinding
import com.example.healthytaste.model.DessertDish

class DessertDishListAdapter :
    RecyclerView.Adapter<DessertDishListAdapter.DessertDishViewHolder>() {
    private var dessertDishList: List<DessertDish> = emptyList()
    var onClickListener: (DessertDish) -> Unit = {}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DessertDishViewHolder {
        val binding =
            RowDessertDishBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DessertDishViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dessertDishList.size
    }

    override fun onBindViewHolder(holder: DessertDishViewHolder, position: Int) {
        val item = dessertDishList[position]
        holder.rootView.setOnClickListener {
            onClickListener.invoke(item)
        }
        holder.nameTextView.text = item.name

        Glide.with(holder.secondDishImageView)
            .load(item.image)
            .into(holder.secondDishImageView)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<DessertDish>) {
        dessertDishList = list
        notifyDataSetChanged()
    }

    inner class DessertDishViewHolder(binding: RowDessertDishBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val rootView = binding.root
        val nameTextView = binding.tvDessertDishName
        val secondDishImageView = binding.ivDessertDishPlate
    }
}