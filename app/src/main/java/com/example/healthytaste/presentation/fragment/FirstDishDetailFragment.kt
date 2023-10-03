package com.example.healthytaste.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.healthytaste.R
import com.example.healthytaste.databinding.FragmentFirstDishDetailBinding
import com.example.healthytaste.model.First
import com.example.healthytaste.model.ResourceState
import com.example.healthytaste.presentation.viewModel.FirstDishDetailState
import com.example.healthytaste.presentation.viewModel.FirstDishViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import org.koin.androidx.viewmodel.ext.android.activityViewModel


class FirstDishDetailFragment : Fragment() {
    private var videoId: String = ""
    private var isPlayerReady: Boolean = false

    private val binding: FragmentFirstDishDetailBinding by lazy {
        FragmentFirstDishDetailBinding.inflate(layoutInflater)
    }

    private val args: FirstDishDetailFragmentArgs by navArgs()
    private val fistDishViewModel: FirstDishViewModel by activityViewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        fistDishViewModel.fetchOneFirstDishList(args.firstDishId)
    }

    private fun initViewModel() {
        fistDishViewModel.getFirstOneDishLiveData().observe(viewLifecycleOwner) { state ->
            handleFistDishDetailState(state)
        }
    }

    private fun handleFistDishDetailState(state: FirstDishDetailState) {
        when (state) {
            is ResourceState.Loading -> {
                binding.pbFirstDishDetail.visibility = View.VISIBLE
            }

            is ResourceState.Success -> {
                binding.pbFirstDishDetail.visibility = View.GONE
                initUI(state.result)
            }

            is ResourceState.Error -> {
                binding.pbFirstDishDetail.visibility = View.GONE
                showErrorDialog(state.error)
            }
        }
    }

    private fun initUI(firstDish: First) {
        binding.textViewNombre.text = firstDish.nombre
        Glide.with(requireContext())
            .load(firstDish.image)
            .into(binding.imageViewReceta)
        binding.textViewIngredientes.text = firstDish.detalles.ingredientes.toString()
        binding.textViewCalorias.text = firstDish.detalles.apto
        binding.textViewElaboracion.text = firstDish.detalles.elaboracion

        // Obtén la ID del video de YouTube y guárdala en videoId
        videoId = firstDish.detalles.urlVideo ?: ""

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
                fistDishViewModel.fetchFirstDishList()
            }
            .show()
    }
}