package com.example.usersp

import android.content.Context
import android.location.GnssAntennaInfo
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.usersp.databinding.ItemUserBinding

open class UserAdapter(private val users: List<User>, private val listener: OnClickListener): RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.ViewHolder {
        context = parent.context
        var view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false)

        return this.ViewHolder(view);

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users.get(position);

        with(holder){
            setListener(user)
            binding.tvOrder.text = (position + 1).toString()
            binding.tvName.text = user.getFullName()
            Glide.with(context)
                .load(user.url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .circleCrop()
                .into(binding.imgProfile)
        }
    }

    override fun getItemCount(): Int = users.size

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding = ItemUserBinding.bind(view)

        fun setListener(user: User) {
            binding.root.setOnClickListener {
                listener.onClick(user)
            }
        }

    }
}