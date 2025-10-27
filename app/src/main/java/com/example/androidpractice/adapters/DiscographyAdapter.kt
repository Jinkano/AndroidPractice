package com.example.androidpractice.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidpractice.data.Discography
import com.example.androidpractice.databinding.ItemDiscographyBinding
import com.squareup.picasso.Picasso

class DiscographyAdapter(
    var items: List<Discography>,
    val onClickListener: (Int) -> Unit): RecyclerView.Adapter<DiscographyViewHolder>()
{
    /*
    * Estas 3 funciones son implementadas onCreateViewHolder onBindViewHolder getItemCount
    *
    * */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscographyViewHolder
    {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemDiscographyBinding.inflate(layoutInflater,parent,false)
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
    fun updateItems(items: List<Discography>) {
        this.items = items
        notifyDataSetChanged()
    }
}


class DiscographyViewHolder(val binding: ItemDiscographyBinding): RecyclerView.ViewHolder(binding.root)
{
        /**/
        fun render(discography: Discography){
            binding.idTvNameDisk.text = discography.title
            Picasso.get().load(discography.imageDisk).into(binding.idIvImgDisk)
        }
}
