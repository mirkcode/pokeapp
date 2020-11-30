package com.mvaresedev.pokeapp.ui.list

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mvaresedev.pokeapp.R
import com.mvaresedev.pokeapp.domain.models.Pokemon
import kotlinx.android.synthetic.main.holder_pokemon.view.*
import java.lang.IllegalStateException


class PokemonListPagingAdapter(private val onPokemonClick: (Pokemon) -> Unit) : PagingDataAdapter<Pokemon, PokemonListPagingAdapter.PokemonVH>(DiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonVH {
        val layout = when(viewType) {
            VIEW_TYPE_LEFT -> R.layout.holder_pokemon
            VIEW_TYPE_RIGHT -> R.layout.holder_pokemon_reverse
            else -> throw IllegalStateException("Wrong viewType $viewType")
        }
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return PokemonVH(view)
    }

    override fun getItemViewType(position: Int): Int {
        return if(position % 2 == 0) VIEW_TYPE_LEFT else VIEW_TYPE_RIGHT
    }

    override fun onBindViewHolder(holder: PokemonVH, position: Int) {
        getItem(position)?.let { holder.bindPokemon(it, onPokemonClick) }
    }

    class PokemonVH(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bindPokemon(pokemon: Pokemon, onPokemonClick: (Pokemon) -> Unit) {
            with(pokemon) {
                itemView.pokemonName_txt.text = name
                Glide.with(itemView).load(iconUrl).into(itemView.pokemon_img)

                //types
                val firstType = pokemon.types.firstOrNull()
                itemView.firstType_txt.isVisible = firstType != null
                firstType?.let {
                    itemView.firstType_txt.text = firstType.name
                    (itemView.firstType_txt.background.current as GradientDrawable).setColor(ContextCompat.getColor(itemView.context, firstType.color))
                }
                val secondType = pokemon.types.getOrNull(1)
                itemView.secondType_txt.isVisible = secondType != null
                secondType?.let {
                    itemView.secondType_txt.text = secondType.name
                    (itemView.secondType_txt.background as GradientDrawable).setColor(ContextCompat.getColor(itemView.context, secondType.color))
                }

            }
            itemView.setOnClickListener { onPokemonClick(pokemon) }
        }
    }

    class DiffUtilCallback: DiffUtil.ItemCallback<Pokemon>() {

        override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem.id == newItem.id && oldItem.name == newItem.name
        }
    }

    companion object {
        private const val VIEW_TYPE_LEFT = 1
        private const val VIEW_TYPE_RIGHT = 2
    }

}