package com.example.testinterviewsalinas.ui.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.testinterviewsalinas.paging.MoviePagingSource
import javax.inject.Inject

class MoviesViewModel @Inject constructor() : ViewModel() {

    val movieslist = Pager(PagingConfig(pageSize = 20)) {
        MoviePagingSource()
    }.flow.cachedIn(viewModelScope)

}