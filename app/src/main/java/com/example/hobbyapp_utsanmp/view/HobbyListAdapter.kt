package com.example.hobbyapp_utsanmp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hobbyapp_utsanmp.R
import com.example.hobbyapp_utsanmp.model.Hobby
import com.example.hobbyapp_utsanmp.util.loadImage

class HobbyListAdapter(val hobbyList: ArrayList<Hobby>) :
    RecyclerView.Adapter<HobbyListAdapter.HobbyViewHolder>() {
    class HobbyViewHolder(var view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HobbyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.hobby_list_item, parent, false)

        return HobbyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return hobbyList.size
    }

    override fun onBindViewHolder(holder: HobbyViewHolder, position: Int) {
        holder.view.findViewById<ImageView>(R.id.imgHobby).loadImage(hobbyList[position].imgUrl)
        holder.view.findViewById<TextView>(R.id.txtTitle).text = hobbyList[position].title
        holder.view.findViewById<TextView>(R.id.txtWriter).text = hobbyList[position].writer
        holder.view.findViewById<TextView>(R.id.txtPreview).text = hobbyList[position].preview

        holder.view.findViewById<Button>(R.id.btnRead).setOnClickListener {

        }
    }

    fun updateHobbyList(newHobbyList: ArrayList<Hobby>){
        hobbyList.clear()
        hobbyList.addAll(newHobbyList)
        notifyDataSetChanged()
    }
}