package com.example.testinterviewsalinas.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testinterviewsalinas.R
import com.example.testinterviewsalinas.databinding.StorageAdapterBinding


class StorageAdapter (private val urls: List<String>): RecyclerView.Adapter<StorageAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.storage_adapter, parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val url = urls[position]
        holder.bind(url)
    }

    override fun getItemCount(): Int = urls.size

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val binding = StorageAdapterBinding.bind(view)

        fun bind(url: String) {
            val urlImage = url.removeRange(0,1)
            val urlFirebase = urlImage.removeRange(urlImage.length -1, urlImage.length )
            Glide.with(itemView.context).load(urlFirebase).centerCrop().into(binding.storageImv)
        }
    }
}