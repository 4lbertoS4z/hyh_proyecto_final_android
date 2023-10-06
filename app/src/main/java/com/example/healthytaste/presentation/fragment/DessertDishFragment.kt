package com.example.healthytaste.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.healthytaste.R
import com.example.healthytaste.databinding.FragmentDessertBinding
import com.example.healthytaste.model.ResourceState
import com.example.healthytaste.presentation.adapter.DessertDishListAdapter
import com.example.healthytaste.presentation.viewModel.DessertDishListState
import com.example.healthytaste.presentation.viewModel.DessertDishViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.activityViewModel


class DessertDishFragment : Fragment() {

    private val binding: FragmentDessertBinding by lazy {
        FragmentDessertBinding.inflate(layoutInflater)
    }
    private val dessertDishListAdapter = DessertDishListAdapter()
    private val dessertDishViewModel: DessertDishViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initUI()

        dessertDishViewModel.fetchDessertDishList()
    }

    private fun initUI() {
        binding.rvDessertDish.adapter = dessertDishListAdapter
        binding.rvDessertDish.layoutManager = LinearLayoutManager(requireContext())

        dessertDishListAdapter.onClickListener = { dessertDish ->
            findNavController().navigate(
                DessertDishFragmentDirections.actionNavigationDessertToNavigationDessertDishDetailFragment(
                    dessertDish.id.toInt()
                )
            )
        }
    }

    private fun initViewModel() {
        dessertDishViewModel.getDessertDishLiveData().observe(viewLifecycleOwner) { state ->
            handleDessertDishListState(state)
        }
    }

    private fun handleDessertDishListState(state: DessertDishListState) {
        when (state) {
            is ResourceState.Loading -> {
                binding.pbDessertDishLoading.visibility = View.VISIBLE
            }

            is ResourceState.Success -> {
                binding.pbDessertDishLoading.visibility = View.GONE
                dessertDishListAdapter.submitList(state.result)
            }

            is ResourceState.Error -> {
                binding.pbDessertDishLoading.visibility = View.GONE
                showErrorDialog(state.error)
            }
        }
    }

    private fun showErrorDialog(error: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.error)
            .setMessage(error)
            .setPositiveButton(R.string.action_ok, null)
            .setNegativeButton(R.string.action_retry) { dialog, witch ->
                dessertDishViewModel.fetchDessertDishList()
            }
            .show()
    }

}