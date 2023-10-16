package com.example.healthytaste.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.healthytaste.databinding.RowSecondDishBinding
import com.example.healthytaste.model.SecondDish

class SecondDishLlistAdapter : RecyclerView.Adapter<SecondDishLlistAdapter.SecondDishViewHolder>() {
    private var secondDishList: List<SecondDish> = emptyList()
    var onClickListener: (SecondDish) -> Unit = {}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SecondDishViewHolder {
        val binding =
            RowSecondDishBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SecondDishViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return secondDishList.size
    }

    override fun onBindViewHolder(holder: SecondDishViewHolder, position: Int) {
        val item = secondDishList[position]
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
        notifyDataSetChanged()
    }

    inner class SecondDishViewHolder(binding: RowSecondDishBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val rootView = binding.root
        val nameTextView = binding.tvSecondDishName
        val secondDishImageView = binding.ivSecondDishPlate
    }
}