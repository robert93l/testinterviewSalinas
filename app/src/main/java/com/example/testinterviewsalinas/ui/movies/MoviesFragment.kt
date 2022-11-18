package com.example.testinterviewsalinas.ui.movies

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.testinterviewsalinas.MainActivity
import com.example.testinterviewsalinas.adapters.MoviesAdapter
import com.example.testinterviewsalinas.databinding.FragmentMoviesBinding

import kotlinx.coroutines.launch
import javax.inject.Inject

class MoviesFragment : Fragment() {

    private var _binding: FragmentMoviesBinding? = null
    @Inject
    lateinit var moviesViewModel: MoviesViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var movieAdapter: MoviesAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).testComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initRecycler()
        movieAdapter = MoviesAdapter()

        lifecycleScope.launch{
            moviesViewModel.movieslist.collect {
                movieAdapter.submitData(it)
            }
        }

        binding.rvMovies.adapter = movieAdapter
        return root
    }

    private fun initRecycler(){
        binding.rvMovies.layoutManager = GridLayoutManager(requireContext(), 3)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}