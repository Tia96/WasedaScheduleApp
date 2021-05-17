package com.example.scheduleapp.view

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Bundle
import android.util.Log
import android.util.TypedValue.COMPLEX_UNIT_DIP
import android.util.TypedValue.applyDimension
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentEditviewBinding.inflate(inflater, container, false).apply {
            data = editViewModel
            lifecycleOwner = viewLifecycleOwner

            chatView.setOnClickListener {
                val action = EditViewFragmentDirections.actionNavigationEditviewToNavigationChatview(args.positions)
                it.findNavController().navigate(action)
            }

            val buttonColors = listOf(Color.rgb(0x81, 0xD4, 0xFA), Color.rgb(0xEF, 0x53, 0x50), Color.rgb(0xFF, 0xCA, 0x28), Color.rgb(0x66, 0xBB, 0x6A))
            val radioButtonDrawables = buttonColors.map { color ->
                StateListDrawable().apply {
                    addState(intArrayOf(android.R.attr.state_checked), GradientDrawable().apply {
                        shape = GradientDrawable.OVAL
                        setColor(color)
                        setSize(applyDimension(COMPLEX_UNIT_DIP, 50.0f, resources.displayMetrics).toInt(), applyDimension(COMPLEX_UNIT_DIP, 50.0f, resources.displayMetrics).toInt())
                        setStroke(applyDimension(COMPLEX_UNIT_DIP, 2.0f, resources.displayMetrics).toInt(), Color.BLACK)
                    })
                    addState(intArrayOf(-android.R.attr.state_checked), GradientDrawable().apply {
                        shape = GradientDrawable.OVAL
                        setColor(color)
                        setSize(applyDimension(COMPLEX_UNIT_DIP, 50.0f, resources.displayMetrics).toInt(), applyDimension(COMPLEX_UNIT_DIP, 50.0f, resources.displayMetrics).toInt())
                    })
                }
            }

            radioButton1.run {
                background = radioButtonDrawables[0]
                setOnClickListener { editViewModel.changeColor(buttonColors[0]) }
            }
            radioButton2.run {
                background = radioButtonDrawables[1]
                setOnClickListener { editViewModel.changeColor(buttonColors[1]) }
            }
            radioButton3.run {
                background = radioButtonDrawables[2]
                setOnClickListener { editViewModel.changeColor(buttonColors[2]) }
            }
            radioButton4.run {
                background = radioButtonDrawables[3]
                setOnClickListener { editViewModel.changeColor(buttonColors[3]) }
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