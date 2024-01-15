package com.example.healthytaste.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.healthytaste.R
import com.example.healthytaste.databinding.FragmentSecondDishBinding
import com.example.healthytaste.model.ResourceState
import com.example.healthytaste.presentation.adapter.SecondDishLlistAdapter
import com.example.healthytaste.presentation.viewModel.SecondDishListState
import com.example.healthytaste.presentation.viewModel.SecondDishViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.activityViewModel


class SecondDishFragment : Fragment() {
    private lateinit var searchView: SearchView
    private var lastSearchQuery: String? = null
    private val binding: FragmentSecondDishBinding by lazy {
        FragmentSecondDishBinding.inflate(layoutInflater)
    }
    private val secondDishListAdapter = SecondDishLlistAdapter()
    private val secondDishViewModel: SecondDishViewModel by activityViewModel()


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
        setupSearchView()
        secondDishViewModel.fetchSecondDishList()

    }

    private fun initUI() {
        binding.rvSecondDish.adapter = secondDishListAdapter
        binding.rvSecondDish.layoutManager = LinearLayoutManager(requireContext())

        secondDishListAdapter.onClickListener = { secondDish ->
            findNavController().navigate(
                SecondDishFragmentDirections.actionNavigationSecondDishToNavigationSecondDishDetailFragment(
                    secondDish.id.toInt()
                )
            )
        }
    }

    private fun setupSearchView() {
        searchView = binding.searchView // Asegúrate de tener esta referencia

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                secondDishListAdapter.filter.filter(newText)
                lastSearchQuery = newText
                return true
            }
        })
    }

    private fun initViewModel() {
        secondDishViewModel.getSecondDishLiveData().observe(viewLifecycleOwner) { state ->
            handleSecondDishListState(state)
        }
    }

    private fun handleSecondDishListState(state: SecondDishListState) {
        when (state) {
            is ResourceState.Loading -> {
                binding.pbSecondDishLoading.visibility = View.VISIBLE
            }

            is ResourceState.Success -> {
                binding.pbSecondDishLoading.visibility = View.GONE
                secondDishListAdapter.submitList(state.result)
                lastSearchQuery?.let {
                    searchView.setQuery(it, false)
                    secondDishListAdapter.filter.filter(it)
                }
            }

            is ResourceState.Error -> {
                binding.pbSecondDishLoading.visibility = View.GONE
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
                secondDishViewModel.fetchSecondDishList()
            }
            .show()
    }
}