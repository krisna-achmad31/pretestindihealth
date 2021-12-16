package com.kris.indihealthpretest.main.home.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.kris.indihealthpretest.R
import com.kris.indihealthpretest.databinding.LayoutRoomItemBinding
import com.kris.indihealthpretest.join.JoinActivity
import com.kris.indihealthpretest.join.JoinClass
import com.kris.indihealthpretest.main.MainActivity
import com.kris.indihealthpretest.models.dataClass.Category

class HomeAdapter(private val categorys: List<Category>) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>(){

    class HomeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvDoctor: TextView = view.findViewById(R.id.tvDoctor)
        val btnJoin: Button = view.findViewById(R.id.btnJoinClass)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_room_item, parent, false)

        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.tvTitle.text = categorys[position].title
        holder.tvDoctor.text = categorys[position].doctor

//        holder.btnJoin.setOnClickListener {
//         startActivity(Intent())
//        }
    }

    override fun getItemCount(): Int {
        return categorys.size
    }





//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.layout_room_item, parent, false)
//
//        return HomeViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
//        holder.tvTitle.text = categorys[position].title
//        holder.tvDoctor.text = categorys[position].doctor
//
//    }
//
//    override fun getItemCount(): Int {
//        return categorys.size
//    }
}