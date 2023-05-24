package ru.paskal.freegamesapp.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.paskal.freegamesapp.model.Game
import ru.paskal.freegamesapp.databinding.FragmentGamesItemBinding
import com.bumptech.glide.Glide
import ru.paskal.freegamesapp.R


class GamesRecycleAdapter(private val clicksListener: ItemInteractListener) : RecyclerView.Adapter<GamesRecycleAdapter.GameViewHolder>(){

    class GameViewHolder(val binding: FragmentGamesItemBinding,
                         listener: ItemInteractListener) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnLongClickListener {
                listener.onItemLongClick(it, adapterPosition)
                true
            }
            binding.likedImageView.setOnClickListener {

                listener.onItemLikeClick(it, adapterPosition)

            }
        }
    }

    var gameList = mutableListOf<Game>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentGamesItemBinding.inflate(inflater, parent, false)
        return GameViewHolder(binding, clicksListener)
    }

    override fun getItemCount(): Int = gameList.size


    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = gameList[position]
        with(holder.binding) {
            val imgPlaceholder = R.drawable.ic_menu_games
            titleTextView.text = game.title
            descriptionTextView.text = game.description

            if (!game.imgUrl.isNullOrEmpty()) {
                Glide.with(gameImgImageView.context)
                    .load(game.imgUrl)
                    .placeholder(imgPlaceholder)
                    .error(imgPlaceholder)
                    .into(gameImgImageView)
            } else {
                gameImgImageView.setImageResource(imgPlaceholder)
            }
            if (game.isLiked) {
                likedImageView.setImageResource(R.drawable.ic_like)
            } else {
                likedImageView.setImageResource(R.drawable.ic_no_like)
            }
        }
    }

    fun clearGames() {
        gameList.clear()
        notifyDataSetChanged()
    }

    fun addGame(game: Game) {
        gameList.add(game)
        notifyItemInserted(itemCount - 1)
    }



}