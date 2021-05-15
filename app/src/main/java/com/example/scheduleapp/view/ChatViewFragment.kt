package com.example.scheduleapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.scheduleapp.databinding.FragmentChatviewBinding
import com.example.scheduleapp.viewmodel.ChatViewModel

class ChatViewFragment : Fragment() {
    lateinit var binding: FragmentChatviewBinding
    private val args: ChatViewFragmentArgs by navArgs()
    private val chatViewModel: ChatViewModel by lazy { ChatViewModelFactory(args.positions[0], args.positions[1]).create(ChatViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentChatviewBinding.inflate(inflater, container, false)



        return binding.root
    }
}

class ChatViewModelFactory(private val x: Int, private val y: Int) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ChatViewModel(x, y) as T
    }
}