package com.example.scheduleapp.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.scheduleapp.databinding.FragmentEditviewBinding
import com.example.scheduleapp.viewmodel.EditViewModel

class EditViewFragment : Fragment() {
    private lateinit var binding: FragmentEditviewBinding
    private val args: EditViewFragmentArgs by navArgs()
    private val editViewModel: EditViewModel by lazy { EditViewModelFactory(args.positions[0], args.positions[1]).create(EditViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentEditviewBinding.inflate(inflater, container, false)
        binding.data = editViewModel
        binding.chatView.setOnClickListener { view ->
            apply {
                val action = EditViewFragmentDirections.actionNavigationEditviewToNavigationChatview(args.positions)
                view.findNavController().navigate(action)
            }
        }
        return binding.root
    }
}

class EditViewModelFactory(private val x: Int, private val y: Int) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EditViewModel(x, y) as T
    }
}