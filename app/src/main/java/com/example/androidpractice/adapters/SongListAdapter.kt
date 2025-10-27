package com.example.androidpractice.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidpractice.databinding.ItemSongListBinding
import com.squareup.picasso.Picasso

class SongListAdapter(
    var items: List<String>,
    val onClickListener: (Int) -> Unit): RecyclerView.Adapter<SongListViewHolder>()
{
    /**/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongListViewHolder
    {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemSongListBinding.inflate(layoutInflater,parent,false)
        return SongListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SongListViewHolder, position: Int)
    {
        val item = items[position]
        holder.render(item)
        holder.itemView.setOnClickListener { onClickListener(position) }
    }

    override fun getItemCount(): Int {return items.size}

    /**/
    fun updateItems(items: List<String>) {
        this.items = items
        notifyDataSetChanged()
    }
}


class SongListViewHolder(val binding: ItemSongListBinding): RecyclerView.ViewHolder(binding.root)
{
    /**/
    fun render(songList: String){
        //binding.idTvSongName.text = songList.
        //Picasso.get().load(discography.imageDisk).into(binding.idIvImgDisk)
    }
}
