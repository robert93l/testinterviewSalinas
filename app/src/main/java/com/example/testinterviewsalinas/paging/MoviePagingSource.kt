package com.example.testinterviewsalinas.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.testinterviewsalinas.model.MovieModel
import com.example.testinterviewsalinas.utils.Constants
import com.example.testinterviewsalinas.webservices.ApiRetrofit
import retrofit2.HttpException
import java.io.IOException

private const val MOVIES_STARTING_PAGE_INDEX = 1

class MoviePagingSource(): PagingSource<Int, MovieModel>() {
    override fun getRefreshKey(state: PagingState<Int, MovieModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieModel> {

        return try {
            val currentPageList = params.key ?: MOVIES_STARTING_PAGE_INDEX
            val response = ApiRetrofit.movieService.getMovies(
                Constants.API_KEY,
                currentPageList,
                Constants.apiLanguageEn
            )
            val responseList = mutableListOf<MovieModel>()

            if(response.isSuccessful){
                val data = response.body()?.results ?: emptyList()
                responseList.addAll(data)
            }

            LoadResult.Page(
                data = responseList,
                prevKey = if (currentPageList == MOVIES_STARTING_PAGE_INDEX) null else currentPageList - 1,
                nextKey = currentPageList.plus(1)
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
    }

    }
}