package com.example.keries.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.keries.R
import com.example.keries.dataClass.Event_DataClass
import com.example.keries.fragments.Events



// this adapter is for  the front event page not hte showevent page

class ShowEventAdapter(private val showevents: List<Event_DataClass>, private val itemClickListener: Events) :
    RecyclerView.Adapter<ShowEventAdapter.ShowEventViewHolder>() {
    interface boxo{
        fun OnItemClick(item: Event_DataClass)
    }
    inner class ShowEventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.eventImageShow)

        val roundedCorners = RoundedCorners(20)
        val requestOptions = RequestOptions().transform(roundedCorners)
        fun bind(se: Event_DataClass) {
            Glide.with(itemView.context)
                .load(se.url)
                .apply(requestOptions)
                .placeholder(R.drawable.ic_launcher_background) // Add a placeholder image
                .error(R.drawable.image_svgrepo_com) // Add an error image
                .into(imageView)

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowEventViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.showeventlayout, parent, false)
        return ShowEventViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ShowEventViewHolder, position: Int) {
        val sees = showevents[position %showevents.size]
        holder.bind(sees)

        holder.itemView.setOnClickListener{
            itemClickListener.onItemClick(sees)
        }
    }

    override fun getItemCount(): Int {
        return if (showevents.isEmpty()) {
            0
        } else {
            Int.MAX_VALUE
        }

    }
}
