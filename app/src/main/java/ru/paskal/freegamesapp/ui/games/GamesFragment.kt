package ru.paskal.freegamesapp.ui.games

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import ru.paskal.freegamesapp.R
import ru.paskal.freegamesapp.databinding.FragmentGamesBinding
import ru.paskal.freegamesapp.events.GamesParsedEvent
import ru.paskal.freegamesapp.model.db_model.DatabaseModel
import ru.paskal.freegamesapp.model.db_model.DatabaseModelFactory
import ru.paskal.freegamesapp.GamesApplication
import ru.paskal.freegamesapp.MainActivity
import ru.paskal.freegamesapp.events.LikeStateChangedEvent
import ru.paskal.freegamesapp.ui.GamesRecycleAdapter
import ru.paskal.freegamesapp.ui.ItemInteractListener
import ru.paskal.freegamesapp.ui.ViewModelFactory

/**
 * FavoriteFragment - Фрагмент для отображения списка избранных игр.
 */
class GamesFragment : Fragment() {

    /**
     * Привязка для доступа к элементам интерфейса фрагмента
     */
    private var _binding: FragmentGamesBinding? = null

    /**
     * Адаптер для списка игр
     */
    private lateinit var adapter: GamesRecycleAdapter

    /**
     * ViewModel для работы с данными игр
     */
    private lateinit var viewModel: GamesViewModel

    /**
     * Модель базы данных для доступа к игровым данным
     */
    private val dbModel: DatabaseModel by activityViewModels  {
        DatabaseModelFactory(
            (activity?.application as GamesApplication).database.gameItemDao()
        )
    }

    /**
     * Привязка для доступа к элементам интерфейса фрагмента
     */
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        EventBus.getDefault().register(this)
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(requireActivity().application, dbModel)
        )[GamesViewModel::class.java]

        adapter = GamesRecycleAdapter(object : ItemInteractListener {
            override fun onItemLongClick(view: View, position: Int) {
                val link = adapter.gameList[position].readMoreUrl
                if (!link.isNullOrEmpty()) {
                    Toast.makeText(view.context, "Открываем статью", Toast.LENGTH_SHORT).show()
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                    val packageManager = requireContext().packageManager
                    if (intent.resolveActivity(packageManager) != null) {
                        requireContext().startActivity(intent)
                    }
                }
            }

            override fun onItemLikeClick(view: View, position: Int) {
                val curGame = adapter.gameList[position]
                curGame.isLiked = !curGame.isLiked
                if (curGame.isLiked) {
                    view.findViewById<ImageView>(R.id.likedImageView)
                        .setImageResource(R.drawable.ic_like)
                    viewModel.addGameToLiked(curGame)
                }
                else {
                    view.findViewById<ImageView>(R.id.likedImageView)
                        .setImageResource(R.drawable.ic_no_like)
                    viewModel.removeGameFromLiked(curGame)
                }
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentGamesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val recyclerView = root.findViewById<RecyclerView>(R.id.games_list)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        binding.root.setOnRefreshListener {
            viewModel.refresh()
            adapter.clearGames()
            binding.swipeRefreshLayout.isRefreshing = false
        }

        binding.root.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )
        viewModel.parseGames()
        return root
    }

    /**
     * Обработчик события GamesParsedEvent. Добавляет игры в адаптер после успешного парсинга.
     *
     * @param event Событие GamesParsedEvent, содержащее список игр.
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onGamesParsed(event: GamesParsedEvent) {
        for (game in event.gameList) {
            adapter.addGame(game)
        }
    }

    /**
     * Обработчик события LikeStateChangedEvent. Обновляет список игр и очищает адаптер.
     *
     * @param event Событие LikeStateChangedEvent, указывающее на изменение состояния "Избранное".
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLikeStateChanged(event: LikeStateChangedEvent) {
        viewModel.refresh()
        adapter.clearGames()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        Log.d("debug", "GamedFragment destroyed")
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)

    }
}