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
import com.example.healthytaste.databinding.FragmentFirstDishBinding
import com.example.healthytaste.model.ResourceState
import com.example.healthytaste.presentation.adapter.FirstDishListAdapter
import com.example.healthytaste.presentation.viewModel.FirstDishListState
import com.example.healthytaste.presentation.viewModel.FirstDishViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.activityViewModel


class FirstDishFragment : Fragment() {
    private lateinit var searchView:SearchView
    private var lastSearchQuery: String? = null
    private val binding: FragmentFirstDishBinding by lazy {
        FragmentFirstDishBinding.inflate(layoutInflater)
    }
    private val firstDishListAdapter = FirstDishListAdapter()
    private val firstDishViewModel: FirstDishViewModel by activityViewModel()

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
        firstDishViewModel.fetchFirstDishList()

    }

    private fun initUI() {
        binding.rvFirstDish.adapter = firstDishListAdapter
        binding.rvFirstDish.layoutManager = LinearLayoutManager(requireContext())

        firstDishListAdapter.onClickListener = { firstDish ->
            findNavController().navigate(
                FirstDishFragmentDirections.actionFirstDistFragmentToFirstDishDetailFragment(
                    firstDish.id.toInt()
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
                firstDishListAdapter.filter.filter(newText)
                lastSearchQuery = newText
                return true
            }
        })
    }
    private fun initViewModel() {
        firstDishViewModel.getFirstDishLiveData().observe(viewLifecycleOwner) { state ->
            handleFirstDishListState(state)
        }
    }

    private fun handleFirstDishListState(state: FirstDishListState) {
        when (state) {
            is ResourceState.Loading -> {
                binding.pbFirstDishLoading.visibility = View.VISIBLE
            }

            is ResourceState.Success -> {
                binding.pbFirstDishLoading.visibility = View.GONE
                firstDishListAdapter.submitList(state.result)
                lastSearchQuery?.let {
                    searchView.setQuery(it, false)
                    firstDishListAdapter.filter.filter(it)
                }
            }

            is ResourceState.Error -> {
                binding.pbFirstDishLoading.visibility = View.GONE
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
                firstDishViewModel.fetchFirstDishList()
            }
            .show()
    }

}