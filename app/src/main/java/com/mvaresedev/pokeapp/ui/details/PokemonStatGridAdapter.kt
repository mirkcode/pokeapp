package com.mvaresedev.pokeapp.ui.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mvaresedev.pokeapp.R
import com.mvaresedev.pokeapp.domain.models.PokemonStat
import kotlinx.android.synthetic.main.holder_pokemon_stat.view.*

class PokemonStatGridAdapter: RecyclerView.Adapter<PokemonStatGridAdapter.StatVH>() {

    private val stats = mutableListOf<PokemonStat>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.holder_pokemon_stat, parent, false)
        return StatVH(view)
    }

    override fun onBindViewHolder(holder: StatVH, position: Int) {
        stats[position].let {
            holder.bindStat(it)
        }
    }

    fun submitData(newData: List<PokemonStat>) {
        stats.clear()
        stats.addAll(newData)
        notifyDataSetChanged()
    }

    override fun getItemCount() = stats.size

    class StatVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindStat(stat: PokemonStat) {
            itemView.statValue_txt.text = stat.value.toString()
            itemView.statName_txt.text = stat.name
        }
    }
}