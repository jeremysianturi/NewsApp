package com.example.newsapp.ui.apple

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.core.domain.model.Apple
import com.example.newsapp.R
import com.example.newsapp.databinding.ItemListAppleBinding
import com.example.newsapp.helper.loadImage

class AppleAdapter : RecyclerView.Adapter<AppleAdapter.UserViewHolder>() {

    var onItemClick: ((Apple) -> Unit)? = null

    private val mData = ArrayList<Apple>()

    fun setData(newListData: List<Apple>?) {
        if (newListData == null) return
        mData.clear()
        mData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int
    ): AppleAdapter.UserViewHolder {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.item_list_apple, parent, false)
        return UserViewHolder(mView)
    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(holder: AppleAdapter.UserViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListAppleBinding.bind(itemView)
        fun bind(data: Apple) {
            with(binding) {

                // concat string
                tvContenttitle.text = data.title
                tvNameApple.text = data.author
                tvContentApple.text = data.description
                ivProfilepic.loadImage(itemView.context,data.urlToImage)
//                ivContentWahana.loadImage(itemView.context,data.thumbnail)



            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(mData[adapterPosition])
            }
        }


    }

}