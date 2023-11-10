package com.example.carapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.carapp.R
import com.example.carapp.databinding.CarItemBinding
import com.example.carapp.models.CarModel


class CarAdapter(
    private val onDeleteNoteClick: (index: Int) -> Unit,
    private val context: Context
) : RecyclerView.Adapter<CarAdapter.CarViewHolder>() {
    val carList = mutableListOf<CarModel>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(carSmallList: List<CarModel>) {
        this.carList.clear()
        this.carList.addAll(carSmallList)
        notifyDataSetChanged()
    }

    inner class CarViewHolder(
        private val binding: CarItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cars: CarModel) {
            binding.carName.text = cars.carName
            binding.carDescription.text = cars.carDescription
            Glide.with(context).load(cars.carURL).into(binding.ivCarURL)
            binding.deleteBtn.setOnClickListener {
                onDeleteNoteClick.invoke(carList.indexOf(cars))
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): CarViewHolder {
        val binding = CarItemBinding.bind(
            LayoutInflater.from(parent.context).inflate(
                R.layout.car_item, parent, false
            )
        )
        return CarViewHolder(binding)
    }

    override fun getItemCount(): Int = carList.size

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        holder.bind(carList[position])
        holder.itemView.setOnClickListener {
        }
    }
}