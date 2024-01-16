package com.example.healthytaste.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.healthytaste.R
import com.example.healthytaste.data.common.DAILY_CALORIES
import com.example.healthytaste.data.common.DAILY_FATS
import com.example.healthytaste.data.common.DAILY_PROTEINS
import com.example.healthytaste.data.common.DAILY_SUGARS
import com.example.healthytaste.databinding.FragmentSecondDishDetailBinding
import com.example.healthytaste.model.ResourceState
import com.example.healthytaste.model.SecondDish
import com.example.healthytaste.model.SecondDishDishDetails
import com.example.healthytaste.presentation.viewModel.SecondDishDetailState
import com.example.healthytaste.presentation.viewModel.SecondDishViewModel
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
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

        setupBarChart(secondDish.details)

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
    private fun calculatePercentage(value: Double, dailyValue: Double): Float {
        return (value / dailyValue * 100).toFloat()
    }
    private fun setupBarChart(details: SecondDishDishDetails) {
        // Crear las entradas para los valores del plato
        val foodEntries = ArrayList<BarEntry>().apply {
            add(BarEntry(0f, calculatePercentage(details.calories, DAILY_CALORIES)))
            add(BarEntry(1f, calculatePercentage(details.protein, DAILY_PROTEINS)))
            add(BarEntry(2f, calculatePercentage(details.fats, DAILY_FATS)))
            add(BarEntry(3f, calculatePercentage(details.sugar, DAILY_SUGARS)))
        }

        // Crear las entradas para los valores diarios recomendados, que serán del 100%
        val dailyEntries = ArrayList<BarEntry>().apply {
            add(BarEntry(0f, 100f))
            add(BarEntry(1f, 100f))
            add(BarEntry(2f, 100f))
            add(BarEntry(3f, 100f))
        }

        // Conjunto de datos para los valores del plato
        val foodDataSet = BarDataSet(foodEntries, "Valores del Plato").apply {
            color = ContextCompat.getColor(requireContext(), R.color.blue)
        }

        // Conjunto de datos para los valores diarios recomendados
        val dailyDataSet = BarDataSet(dailyEntries, "Valores Diarios Recomendados").apply {
            color = ContextCompat.getColor(requireContext(), R.color.red)
        }

        // Configurar el BarData con ambos conjuntos de datos
        val barData = BarData(foodDataSet, dailyDataSet).apply {
            barWidth = 0.35f // Ancho de las barras
        }

        with(binding.barChart) {
            data = barData
            // Ajustar la visualización de las barras para que no se superpongan y queden agrupadas
            val barSpace = 0.02f // Espacio entre barras individuales
            val groupSpace = 0.2f // Espacio entre grupos de barras
            groupBars(-0.5f, groupSpace, barSpace) // Esta función ajusta los grupos de barras
            // Añadir animación
            animateY(1000)

            // Configurar el eje Y para que el máximo sea 100%
            axisLeft.axisMaximum = 100f
            axisRight.isEnabled = false

            // Configurar el eje X para que muestre las etiquetas correctamente
            val labels = arrayOf("Kcal", "Proteína", "Grasa", "Azucar")
            xAxis.valueFormatter = IndexAxisValueFormatter(labels)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.granularity = 1f
            xAxis.setCenterAxisLabels(true)

            // Actualizar la leyenda para que refleje los nuevos datos
            legend.isEnabled = true

            invalidate() // Refresca el gráfico
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