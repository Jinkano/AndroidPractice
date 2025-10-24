package com.example.androidpractice.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidpractice.data.MusicGroups
import com.example.androidpractice.databinding.ItemListGroupBinding
import com.squareup.picasso.Picasso
/*
class DiscographyAdapter(
    var items: List<MusicGroups>,
    val onClickListener: (Int) -> Unit): RecyclerView.Adapter<DiscographyViewHolder>()
{
    /*
    * Estas 3 funciones son implementadas onCreateViewHolder onBindViewHolder getItemCount
    *
    * */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscographyViewHolder
    {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemInformationBinding.inflate(layoutInflater,parent,false)
        return DiscographyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DiscographyViewHolder, position: Int)
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

**
class DiscographyViewHolder(val binding: ItemInformationBinding): RecyclerView.ViewHolder(binding.root)
{
        /**/
        fun render(musicGroup: MusicGroups){
            binding.idTvNameGroup.text = musicGroup.group
            binding.idTvPlaceYearFounded.text = musicGroup.founded
            binding.idTvMusicalGenre.text = musicGroup.genre
            binding.idTvGroupMembers.text = musicGroup.members
            binding.idTvHistory.text = musicGroup.history
            //Log.i("API", musicGroup.imageGroup ?: "")
            Picasso.get().load(musicGroup.imageGroup).into(binding.idIvImgGroup)
        }
}*/
