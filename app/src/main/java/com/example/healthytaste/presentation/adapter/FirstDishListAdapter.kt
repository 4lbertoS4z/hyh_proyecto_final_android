package com.example.healthytaste.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.healthytaste.databinding.FirstDishCardListBinding
import com.example.healthytaste.model.First

class FirstDishListAdapter:RecyclerView.Adapter<FirstDishListAdapter.FirstDishViewHolder>() {

    private var firstDishList: List<First> = emptyList()
     var onClickListener: (First) -> Unit = {}

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FirstDishViewHolder {
        val binding = FirstDishCardListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FirstDishViewHolder(binding)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder:FirstDishViewHolder, position: Int) {
        val item = firstDishList[position]

           holder.rootView.setOnClickListener {
            onClickListener.invoke(item)
        }
        holder.nameTextView.text = item.name

        Glide.with(holder.firstDishImageView)
            .load(item.image)
            .into(holder.firstDishImageView)
    }

    override fun getItemCount(): Int {
        return firstDishList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<First>) {
        firstDishList = list
        notifyDataSetChanged()
    }

    inner class FirstDishViewHolder(binding:FirstDishCardListBinding): RecyclerView.ViewHolder(binding.root) {
        val rootView = binding.root
        val nameTextView = binding.tvFirstDishName
        val firstDishImageView = binding.ivFirstDishPlate
    }
}