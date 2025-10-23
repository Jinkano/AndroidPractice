package com.example.androidpractice.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidpractice.data.MusicGroups
import com.example.androidpractice.databinding.ItemListGroupBinding
import com.squareup.picasso.Picasso

class ListGroupAdapter(
    var items: List<MusicGroups>,
    val onClickListener: (Int) -> Unit): RecyclerView.Adapter<MusicGroupViewHolder>()
{
    /*
    * Estas 3 funciones son implementadas onCreateViewHolder onBindViewHolder getItemCount
    *
    * */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicGroupViewHolder
    {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemListGroupBinding.inflate(layoutInflater,parent,false)
        return MusicGroupViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MusicGroupViewHolder, position: Int)
    {
        val item = items[position]
        holder.render(item)
        holder.itemView.setOnClickListener { onClickListener(position) }
    }

    override fun getItemCount(): Int {return items.size}

    /**/
    fun updateItems(items: List<MusicGroups>) {
        this.items = items
        notifyDataSetChanged()
    }
}

/**/
class MusicGroupViewHolder(val binding: ItemListGroupBinding): RecyclerView.ViewHolder(binding.root)
{
        /**/
        fun render(musicGroup: MusicGroups){
            binding.idTvNameGroup.text = musicGroup.group
            //binding.idTvPlaceYearFounded.text = musicGroup.founded
            binding.idTvMusicGenre.text = musicGroup.genre
            //Log.i("API", musicGroup.imageGroup ?: "")
            Picasso.get().load(musicGroup.imageGroup).into(binding.idIvImgGroup)
        }
}
