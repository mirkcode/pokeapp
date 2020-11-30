package com.mvaresedev.pokeapp.ui.details

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.mvaresedev.pokeapp.R
import com.mvaresedev.pokeapp.domain.models.Pokemon
import com.mvaresedev.pokeapp.ui.base.BaseFragment
import com.mvaresedev.pokeapp.ui.details.uimodel.PokemonDetailsEvent
import com.mvaresedev.pokeapp.ui.details.uimodel.PokemonDetailsIntent
import com.mvaresedev.pokeapp.ui.details.uimodel.PokemonDetailsState
import io.uniflow.androidx.flow.onEvents
import io.uniflow.androidx.flow.onStates
import io.uniflow.core.flow.data.UIEvent
import kotlinx.android.synthetic.main.fragment_pokemon_details.*
import kotlinx.android.synthetic.main.fragment_pokemon_details.view.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PokemonDetailsFragment : BaseFragment() {

    private val detailsViewModel by viewModel<PokemonDetailsViewModel>()

    private val statsAdapter by lazy { PokemonStatGridAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_pokemon_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi(view)

        onStates(detailsViewModel) { state ->
            when(state) {
                is PokemonDetailsState.DetailsPresent -> showPokemonDetails(state.pokemon)
                is PokemonDetailsState.DetailsError -> showError()
            }
        }

        onEvents(detailsViewModel) {
            when(it.take()) {
                is PokemonDetailsEvent.NavigateBack -> navigateBack()
                is UIEvent.Loading -> handleLoading(true)
                is UIEvent.Success -> handleLoading(false)
            }
        }
        val safeArgs: PokemonDetailsFragmentArgs by navArgs()
        detailsViewModel.initFlow(safeArgs.pokemonId)
    }

    private fun showError() {
        error_container.isVisible = true
        pokemon_container.isVisible = false
    }

    private fun navigateBack() {
        findNavController().popBackStack()
    }

    private fun showPokemonDetails(pokemon: Pokemon) {
        error_container.isVisible = false
        pokemon_container.isVisible = true

        with(pokemon) {

            pokemonName_txt.text = name
            Glide.with(requireContext()).load(bigIconUrl).into(pokemon_img)

            val firstType = pokemon.types.firstOrNull()
            firstType_txt.isVisible = firstType != null
            firstType?.let {
                firstType_txt.text = firstType.name
                (firstType_txt.background.current as GradientDrawable).setColor(ContextCompat.getColor(requireContext(), firstType.color))
            }
            val secondType = pokemon.types.getOrNull(1)
            secondType_txt.isVisible = secondType != null
            secondType?.let {
                secondType_txt.text = secondType.name
                (secondType_txt.background as GradientDrawable).setColor(ContextCompat.getColor(requireContext(), secondType.color))
            }

            abilityValue_txt.text = pokemon.ability
            heightValue_txt.text = getString(R.string.details_height_format, pokemon.height)
            weightValue_txt.text = getString(R.string.details_weight_format, pokemon.weight)

            statsAdapter.submitData(stats)
        }
    }

    private fun initUi(view: View) {
        view.pokeball_img.startAnimation(AnimationUtils.loadAnimation(context, R.anim.rotation))
        view.stats_recycler.adapter = statsAdapter

        view.toolbar.setNavigationOnClickListener {
            lifecycleScope.launch {
                detailsViewModel.intentChannel.send(PokemonDetailsIntent.DetailsBackClick)
            }
        }

        goBack_btn.setOnClickListener {
            lifecycleScope.launch {
                detailsViewModel.intentChannel.send(PokemonDetailsIntent.DetailsButtonBackClick)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                lifecycleScope.launch {
                    detailsViewModel.intentChannel.send(PokemonDetailsIntent.DetailsBackClick)
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

}