package com.example.healthytaste.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.healthytaste.R
import com.example.healthytaste.databinding.FragmentSecondDishDetailBinding
import com.example.healthytaste.model.ResourceState
import com.example.healthytaste.model.SecondDish
import com.example.healthytaste.presentation.viewModel.SecondDishDetailState
import com.example.healthytaste.presentation.viewModel.SecondDishViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import org.koin.androidx.viewmodel.ext.android.activityViewModel


class SecondDishDetailFragment : Fragment() {
    private var videoId: String = ""
    private var isPlayerReady: Boolean = false

    private val binding: FragmentSecondDishDetailBinding by lazy {
        FragmentSecondDishDetailBinding.inflate(layoutInflater)
    }
    private val args: SecondDishDetailFragmentArgs by navArgs()
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
        secondDishViewModel.fetchSecondDishDetail(args.secondDishId)
    }

    private fun initViewModel() {
        secondDishViewModel.getSecondDishDetailLiveData().observe(viewLifecycleOwner) { state ->
            handleFistDishDetailState(state)
        }
    }

    private fun handleFistDishDetailState(state: SecondDishDetailState) {
        when (state) {
            is ResourceState.Loading -> {
                binding.pbSecondDishDetail.visibility = View.VISIBLE
            }

            is ResourceState.Success -> {
                binding.pbSecondDishDetail.visibility = View.GONE
                initUI(state.result)
            }

            is ResourceState.Error -> {
                binding.pbSecondDishDetail.visibility = View.GONE
                showErrorDialog(state.error)
            }
        }
    }

    private fun initUI(secondDish: SecondDish) {
        binding.tvRecipeName.text = secondDish.name
        Glide.with(requireContext())
            .load(secondDish.image)
            .into(binding.ivRecipe)
        Glide.with(requireContext())
            .load(secondDish.details.allergies)
            .into(binding.ivAllergies)
        binding.tvElaboration.text = secondDish.details.elaboration


        videoId = secondDish.details.urlVideo


        val youTubePlayerView = binding.youtubePlayerView
        lifecycle.addObserver(youTubePlayerView)

        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                isPlayerReady = true
                playYouTubeVideo(youTubePlayer)
            }
        })

        // Devuelve la lista de ingredientes(cada uno en una linea)
        val ingredientsArray = secondDish.details.ingredients
        val tableLayout = binding.tlIngredients
        val textSizeInSp = 16

        for (ingredient in ingredientsArray) {
            val tableRow = TableRow(requireContext())
            val textView = TextView(requireContext())

            textView.textSize = textSizeInSp.toFloat()
            textView.text = ingredient
            tableRow.addView(textView)
            tableLayout.addView(tableRow)
        }

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
                secondDishViewModel.fetchSecondDishList()
            }
            .show()
    }
}