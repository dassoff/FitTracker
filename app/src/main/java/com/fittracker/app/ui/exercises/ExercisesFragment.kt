package com.fittracker.app.ui.exercises

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fittracker.app.FitTrackerApp
import com.fittracker.app.R
import com.fittracker.app.data.models.MuscleGroup
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

/**
 * Фрагмент для отображения упражнений
 */
class ExercisesFragment : Fragment() {

    private lateinit var exercisesViewModel: ExercisesViewModel
    private lateinit var exercisesAdapter: ExercisesAdapter

    // UI компоненты
    private lateinit var searchView: SearchView
    private lateinit var muscleGroupChips: ChipGroup
    private lateinit var recyclerView: androidx.recyclerview.widget.RecyclerView
    private lateinit var fabAddExercise: com.google.android.material.floatingactionbutton.FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_exercises, container, false)

        // Инициализация UI компонентов
        searchView = root.findViewById(R.id.searchView)
        muscleGroupChips = root.findViewById(R.id.muscleGroupChips)
        recyclerView = root.findViewById(R.id.exercisesRecyclerView)
        fabAddExercise = root.findViewById(R.id.fabAddExercise)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Инициализация ViewModel
        val repository = (requireActivity().application as FitTrackerApp).exerciseRepository
        exercisesViewModel = ViewModelProvider(this, ExercisesViewModel.Factory(repository))
            .get(ExercisesViewModel::class.java)

        setupRecyclerView()
        setupChips()
        setupSearchView()
        setupFab()
        observeViewModel()
    }

    /**
     * Настройка RecyclerView и адаптера
     */
    private fun setupRecyclerView() {
        exercisesAdapter = ExercisesAdapter { exercise ->
            // Обработка нажатия на упражнение
            exercisesViewModel.selectExercise(exercise)
            // TODO: Открыть экран детальной информации об упражнении
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = exercisesAdapter
        }
    }

    /**
     * Настройка фильтрации по группам мышц с помощью Chips
     */
    private fun setupChips() {
        // Добавляем чип для сброса фильтрации
        val allChip = Chip(requireContext()).apply {
            text = "Все"
            isCheckable = true
            isChecked = true
            setOnClickListener {
                exercisesViewModel.setMuscleGroupFilter(null)
            }
        }
        muscleGroupChips.addView(allChip)

        // Добавляем чипы для каждой группы мышц
        MuscleGroup.values().forEach { muscleGroup ->
            val chip = Chip(requireContext()).apply {
                text = muscleGroup.name // В реальном приложении нужно перевести в читаемое название
                isCheckable = true
                setOnClickListener {
                    exercisesViewModel.setMuscleGroupFilter(muscleGroup)
                }
            }
            muscleGroupChips.addView(chip)
        }
    }

    /**
     * Настройка поисковой строки
     */
    private fun setupSearchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { exercisesViewModel.searchExercises(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { exercisesViewModel.searchExercises(it) }
                return true
            }
        })
    }

    /**
     * Настройка FAB для добавления упражнения
     */
    private fun setupFab() {
        fabAddExercise.setOnClickListener {
            // TODO: Открыть диалог/экран для добавления нового упражнения
        }
    }

    /**
     * Наблюдение за изменениями данных во ViewModel
     */
    private fun observeViewModel() {
        // Наблюдаем за всеми упражнениями
        exercisesViewModel.allExercises.observe(viewLifecycleOwner, { exercises ->
            if (exercisesViewModel.selectedMuscleGroup.value == null &&
                exercisesViewModel.searchQuery.value.isNullOrBlank()
            ) {
                exercisesAdapter.submitList(exercises)
            }
        })

        // Наблюдаем за отфильтрованными упражнениями
        exercisesViewModel.filteredExercises.observe(viewLifecycleOwner, { exercises ->
            if (!exercisesViewModel.searchQuery.value.isNullOrBlank()) {
                return@observe // Если активен поиск, не обновляем список
            }
            exercisesAdapter.submitList(exercises)
        })

        // Наблюдаем за результатами поиска
        exercisesViewModel.searchResults.observe(viewLifecycleOwner, { results ->
            if (!exercisesViewModel.searchQuery.value.isNullOrBlank()) {
                exercisesAdapter.submitList(results)
            }
        })
    }
}

/**
 * Адаптер для списка упражнений
 */
class ExercisesAdapter(private val onItemClick: (Exercise) -> Unit) :
    androidx.recyclerview.widget.ListAdapter<com.fittracker.app.data.models.Exercise, ExercisesAdapter.ExerciseViewHolder>(
        androidx.recyclerview.widget.DiffUtil.ItemCallback<com.fittracker.app.data.models.Exercise>() {
            override fun areItemsTheSame(
                oldItem: com.fittracker.app.data.models.Exercise, 
                newItem: com.fittracker.app.data.models.Exercise
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: com.fittracker.app.data.models.Exercise, 
                newItem: com.fittracker.app.data.models.Exercise
            ): Boolean {
                return oldItem == newItem
            }
        }
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_exercise, parent, false)
        return ExerciseViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ExerciseViewHolder(itemView: View, private val onItemClick: (com.fittracker.app.data.models.Exercise) -> Unit) : 
        androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        
        private val nameTextView: android.widget.TextView = itemView.findViewById(R.id.exerciseName)
        private val muscleGroupTextView: android.widget.TextView = itemView.findViewById(R.id.exerciseMuscleGroup)
        private val imageView: android.widget.ImageView = itemView.findViewById(R.id.exerciseImage)

        fun bind(exercise: com.fittracker.app.data.models.Exercise) {
            nameTextView.text = exercise.name
            muscleGroupTextView.text = exercise.muscleGroup.name // В реальном приложении перевести
            
            // Если есть изображение, загружаем его (например, с Glide)
            // Если нет, используем плейсхолдер
            imageView.setImageResource(android.R.drawable.ic_menu_gallery)
            
            itemView.setOnClickListener { onItemClick(exercise) }
        }
    }
} 