package com.example.lu3_camera_app

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.lu3_camera_app.Model.ImageModel
import com.example.lu3_camera_app.databinding.FragmentLocalImagesViewBinding

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyLocalImageModelRecyclerViewAdapter(
    private val values: List<ImageModel>
) : RecyclerView.Adapter<MyLocalImageModelRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentLocalImagesViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.nameView.text = item.imageName
        holder.imageView.setImageBitmap(item.imageBitmap)
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentLocalImagesViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val nameView = binding.txtViewImageName
        val imageView = binding.imgViewImagePane

        override fun toString(): String {
            return super.toString() + " '" + nameView.text + "'"
        }
    }
}