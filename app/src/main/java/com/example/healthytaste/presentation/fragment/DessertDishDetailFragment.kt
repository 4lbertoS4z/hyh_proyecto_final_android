package com.example.healthytaste.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.healthytaste.R
import com.example.healthytaste.databinding.FragmentDessertDishDetailBinding
import com.example.healthytaste.model.DessertDish
import com.example.healthytaste.model.First
import com.example.healthytaste.model.ResourceState
import com.example.healthytaste.presentation.viewModel.DessertDishDetailState
import com.example.healthytaste.presentation.viewModel.DessertDishViewModel
import com.example.healthytaste.presentation.viewModel.FirstDishViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import org.koin.androidx.viewmodel.ext.android.activityViewModel


class DessertDishDetailFragment : Fragment() {
    private var videoId: String = ""
    private var isPlayerReady: Boolean = false

    private val binding:FragmentDessertDishDetailBinding by lazy {
        FragmentDessertDishDetailBinding.inflate(layoutInflater)
    }
    private val args: DessertDishDetailFragmentArgs by navArgs()
    private val dessertDishViewModel: DessertDishViewModel by activityViewModel()
            override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        dessertDishViewModel.fetchDessertDishDetail(args.dessertDishId)
    }

    private fun initViewModel() {
        dessertDishViewModel.getDessertDishDetailLiveData().observe(viewLifecycleOwner) { state ->
            handleDessertDishDetailState(state)
        }
    }

    private fun handleDessertDishDetailState(state: DessertDishDetailState) {
        when (state) {
            is ResourceState.Loading -> {
                binding.pbDessertDishDetail.visibility = View.VISIBLE
            }

            is ResourceState.Success -> {
                binding.pbDessertDishDetail.visibility = View.GONE
                initUI(state.result)
            }

            is ResourceState.Error -> {
                binding.pbDessertDishDetail.visibility = View.GONE
                showErrorDialog(state.error)
            }
        }
    }

    private fun initUI(dessertDish: DessertDish) {
        binding.textViewNombre.text = dessertDish.name
        Glide.with(requireContext())
            .load(dessertDish.image)
            .into(binding.imageViewReceta)
        binding.textViewIngredientes.text = dessertDish.details.ingredients.toString()
        binding.textViewCalorias.text = dessertDish.details.apto
        binding.textViewElaboracion.text = dessertDish.details.elaboration

        // Obtén la ID del video de YouTube y guárdala en videoId
        videoId = dessertDish.details.urlVideo ?: ""

        // Configura el reproductor de YouTube
        val youTubePlayerView = binding.youtubePlayerView
        lifecycle.addObserver(youTubePlayerView)

        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                isPlayerReady = true
                playYouTubeVideo(youTubePlayer)
            }
        })
    }

    private fun playYouTubeVideo(youTubePlayer: YouTubePlayer) {
        if (isPlayerReady) {
            youTubePlayer.cueVideo(videoId, 0f)
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