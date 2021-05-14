package com.example.scheduleapp.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.scheduleapp.model.ClassData
import com.example.scheduleapp.model.HeaderData
import com.example.scheduleapp.viewmodel.ScheduleDataViewModel
import androidx.databinding.ViewDataBinding
import com.example.scheduleapp.BR
import com.example.scheduleapp.databinding.TextviewClassDataBinding
import com.example.scheduleapp.databinding.TextviewHeaderDataBinding

class ScheduleTableAdapter(context: Context?, private val viewModel: ScheduleDataViewModel) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val inflater = LayoutInflater.from(context)

    class BindingHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewType.values()[viewType].createViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        ViewType.values()[holder.itemViewType].bindViewHolder(holder as BindingHolder, position, viewModel)
    }

    override fun getItemCount() = viewModel.scheduleData.value?.size ?: 0

    override fun getItemViewType(position: Int) =
        when (viewModel.scheduleData.value?.get(position)) {
            is ClassData -> ViewType.Class.type
            is HeaderData -> ViewType.Header.type
            else -> throw NoSuchElementException()
        }

    enum class ViewType(val type: Int) {
        Class(0) {
            override fun createViewHolder(inflater: LayoutInflater, parent: ViewGroup?): RecyclerView.ViewHolder {
                val binding = TextviewClassDataBinding.inflate(inflater, parent, false)
                return BindingHolder(binding)
            }
        },
        Header(1) {
            override fun createViewHolder(inflater: LayoutInflater, parent: ViewGroup?): RecyclerView.ViewHolder {
                val binding = TextviewHeaderDataBinding.inflate(inflater, parent, false)
                return BindingHolder(binding)
            }
        };

        abstract fun createViewHolder(inflater: LayoutInflater, parent: ViewGroup?): RecyclerView.ViewHolder
        fun bindViewHolder(holder: BindingHolder, position: Int, viewModel: ScheduleDataViewModel) {
            holder.binding.setVariable(BR.data, viewModel.scheduleData.value?.get(position))
        }
    }
}