package com.example.testinterviewsalinas.adapters

import android.content.Intent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testinterviewsalinas.MovieDetailActivity
import com.example.testinterviewsalinas.R
import com.example.testinterviewsalinas.databinding.MoviesAdapterBinding
import com.example.testinterviewsalinas.model.MovieModel
import com.example.testinterviewsalinas.utils.Constants


class MoviesAdapter(): PagingDataAdapter<MovieModel, MoviesAdapter.ViewHolder>(DiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.movies_adapter, parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }

        holder.itemView.setOnClickListener {

            val intentDetail = Intent(holder.itemView.context, MovieDetailActivity::class.java)
            intentDetail.putExtra("movie", item)
            holder.itemView.context.startActivity(intentDetail)
        }
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val binding = MoviesAdapterBinding.bind(view)

        fun bind(movie: MovieModel) {
            Glide.with(itemView.context).load(Constants.urlBaseImage + movie.posterPath).centerCrop().into(binding.movieImv)
        }
    }

    class DiffUtilCallBack: DiffUtil.ItemCallback<MovieModel>() {
        override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
            return oldItem.title == newItem.title
                    && oldItem.id == newItem.id
        }

    }
}