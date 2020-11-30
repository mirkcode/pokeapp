package com.mvaresedev.pokeapp.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import com.mvaresedev.pokeapp.R
import com.mvaresedev.pokeapp.domain.models.Pokemon
import com.mvaresedev.pokeapp.ui.base.BaseFragment
import com.mvaresedev.pokeapp.ui.list.uimodel.PokemonListEvent
import com.mvaresedev.pokeapp.ui.list.uimodel.PokemonListIntent
import com.mvaresedev.pokeapp.ui.list.uimodel.PokemonListStates
import io.uniflow.androidx.flow.onEvents
import io.uniflow.androidx.flow.onStates
import io.uniflow.core.flow.data.UIEvent
import kotlinx.android.synthetic.main.fragment_pokemon_list.*
import kotlinx.android.synthetic.main.fragment_pokemon_list.view.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PokemonListFragment: BaseFragment() {

    private val listViewModel by viewModel<PokemonListViewModel>()
    private val adapter by lazy { PokemonListPagingAdapter(::onPokemonItemCLick) }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_pokemon_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi(view)

        onStates(listViewModel) { state ->
            when(state) {
                is PokemonListStates.ListPresent -> showPokemonPagingList(state.pagingData)
                is PokemonListStates.ListError -> showListError()
            }
        }

        onEvents(listViewModel) {
            when(val event = it.take()) {
                is PokemonListEvent.GoToDetail -> navigateToDetailsFragment(event.pokemon)
                is UIEvent.Loading -> handleLoading(true)
                is UIEvent.Success -> handleLoading(false)
            }
        }

        listViewModel.initFlow()
    }

    private fun navigateToDetailsFragment(pokemon: Pokemon) {
        val action = PokemonListFragmentDirections.actionPokemonDetails(pokemon.id)
        findNavController().navigate(action)
    }

    private fun showListError() {
        error_container.isVisible = true
        pokemon_recyclerView.isVisible = false
    }

    private fun initUi(view: View) {

        view.pokemon_recyclerView.adapter = adapter.withLoadStateFooter(
            footer = PokemonLoadingAdapter { adapter.retry() }
        )

        adapter.addLoadStateListener { loadState ->
            lifecycleScope.launch {
                listViewModel.intentChannel.send(PokemonListIntent.ListStateChange(adapter.itemCount, loadState))
            }
        }

        retry_btn.setOnClickListener {
            adapter.retry()
        }
    }

    private fun showPokemonPagingList(pagingData: PagingData<Pokemon>) {
        error_container.isVisible = false
        pokemon_recyclerView.isVisible = true
        lifecycleScope.launch {
            adapter.submitData(pagingData)
        }
    }

    private fun onPokemonItemCLick(pokemon: Pokemon) {
        lifecycleScope.launch {
            listViewModel.intentChannel.send(PokemonListIntent.PokemonItemClick(pokemon))
        }
    }

}
