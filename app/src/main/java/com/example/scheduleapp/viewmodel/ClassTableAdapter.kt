package com.example.scheduleapp.viewmodel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.scheduleapp.R
import com.example.scheduleapp.databinding.ClassDataBinding
import com.example.scheduleapp.model.ClassData
import com.example.scheduleapp.model.HeaderData

class ClassTableAdapter(context: Context?, val viewModel: ClassDataViewModel) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val inflater = LayoutInflater.from(context)

    enum class ViewType(val id: Int) {
        Data(0) {
            override fun createViewHolder(inflater: LayoutInflater, parent: ViewGroup?): RecyclerView.ViewHolder {
                val binding = ClassDataBinding.inflate(inflater, parent, false)
                return ClassDataBindingHolder(binding)
            }

            override fun bindViewHolder(holder: RecyclerView.ViewHolder, position: Int, viewModel: ClassDataViewModel) {
                holder as ClassDataBindingHolder
                holder.binding.data = viewModel.classData.value?.get(position) as ClassData
                holder.binding.executePendingBindings()
            }
        },
        Header(1) {
            override fun createViewHolder(inflater: LayoutInflater, parent: ViewGroup?): RecyclerView.ViewHolder {
                return HeaderHolder(inflater.inflate(R.layout.header_data, parent, false))
            }

            override fun bindViewHolder(holder: RecyclerView.ViewHolder, position: Int, viewModel: ClassDataViewModel) {
                holder as HeaderHolder
                val layout = holder.view.findViewById<LinearLayout>(R.id.linearLayout2)
                val text = holder.view.findViewById<TextView>(R.id.textView1)
                text.text = viewModel.classData.value?.get(position)?.name
            }
        };

        abstract fun createViewHolder(inflater: LayoutInflater, parent: ViewGroup?): RecyclerView.ViewHolder
        abstract fun bindViewHolder(holder: RecyclerView.ViewHolder, position: Int, viewModel: ClassDataViewModel)
    }

    private class ClassDataBindingHolder(val binding: ClassDataBinding) : RecyclerView.ViewHolder(binding.root)
    private class HeaderHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewType.values()[viewType].createViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        ViewType.values()[holder.itemViewType].bindViewHolder(holder, position, viewModel)
    }

    override fun getItemCount() = viewModel.classData.value?.size ?: 0

    override fun getItemViewType(position: Int): Int {
        val item = viewModel.classData.value?.get(position)

        return when(item) {
            is ClassData -> {
                ViewType.Data.id
            }
            is HeaderData -> {
                ViewType.Header.id
            }
            else -> {
                throw NoSuchElementException()
            }
        }
    }
}