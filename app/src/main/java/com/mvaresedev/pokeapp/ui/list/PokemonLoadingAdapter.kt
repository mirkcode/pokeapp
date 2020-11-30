package com.mvaresedev.pokeapp.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mvaresedev.pokeapp.R
import kotlinx.android.synthetic.main.holder_pokemon_loading.view.*

class PokemonLoadingAdapter (private val retry: () -> Unit) : LoadStateAdapter<PokemonLoadingAdapter.PokemonLoadingVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): PokemonLoadingVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.holder_pokemon_loading, parent, false)
        return PokemonLoadingVH(view, retry)
    }

    override fun onBindViewHolder(holder: PokemonLoadingVH, loadState: LoadState) {
        holder.bindState(loadState)
    }

    class PokemonLoadingVH(itemView: View, retry: () -> Unit): RecyclerView.ViewHolder(itemView) {

        init {
            itemView.retry_btn.setOnClickListener {
                retry.invoke()
            }
        }

        fun bindState(loadState: LoadState) {
            itemView.animation_view.isVisible = loadState != LoadState.Loading
            itemView.error_txt.isVisible = loadState != LoadState.Loading
            itemView.retry_btn.isVisible = loadState != LoadState.Loading
            itemView.loading_pb.isVisible = loadState == LoadState.Loading
        }
    }

}