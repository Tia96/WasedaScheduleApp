package com.example.scheduleapp.view

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.TypedValue.COMPLEX_UNIT_DIP
import android.util.TypedValue.applyDimension
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.scheduleapp.databinding.FragmentChatviewBinding
import com.example.scheduleapp.databinding.ImageviewChatBinding
import com.example.scheduleapp.databinding.TextviewChatBinding
import com.example.scheduleapp.viewmodel.ChatViewModel
import java.io.ByteArrayOutputStream


class ChatViewFragment : Fragment() {
    lateinit var binding: FragmentChatviewBinding
    private val args: ChatViewFragmentArgs by navArgs()
    private val chatViewModel: ChatViewModel by lazy { ChatViewModelFactory(args.positions[0], args.positions[1]).create(ChatViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentChatviewBinding.inflate(inflater, container, false).apply {
            data = chatViewModel
            lifecycleOwner = viewLifecycleOwner
        }

        val chatContents = chatViewModel.selected.chats
        for (chatText in chatContents) {
            TextviewChatBinding.inflate(inflater, binding.chatLayoutView, true).root.run {
                text = chatText
            }
        }

        val imageContents = chatViewModel.selected.bitmapCache
        for (imageName in imageContents) {
            var jpegArray: ByteArray
            context?.openFileInput(imageName).use {
                jpegArray = it?.readBytes() ?: byteArrayOf()
            }

            val bitmap = BitmapFactory.decodeByteArray(jpegArray, 0, jpegArray.size)
            ImageviewChatBinding.inflate(layoutInflater, binding.chatLayoutView, true).root.run {
                val (width, height) = calcImageLayoutSize(bitmap.width, bitmap.height, 300.0f, 400.0f)
                layoutParams.width = width
                layoutParams.height = height
                setImageBitmap(bitmap)
            }
        }
        binding.chatView.post { binding.chatView.fullScroll(View.FOCUS_DOWN) }

        binding.enterChatButton.setOnClickListener {
            if (chatViewModel.editText.value != "") {
                TextviewChatBinding.inflate(inflater, binding.chatLayoutView, true).root.run {
                    text = chatViewModel.editText.value
                }
                binding.chatView.post { binding.chatView.fullScroll(View.FOCUS_DOWN) }
                chatViewModel.addChatText(chatViewModel.editText.value!!)
            }
        }

        binding.selectImageButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "image/*"
            }
            startActivityForResult(intent, 0)
        }

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultIntent: Intent?) {
        if (requestCode == 0 && resultCode == RESULT_OK) {
            val bitmap = resultIntent?.data.let {
                if (activity?.contentResolver == null || it == null) null
                else when {
                    Build.VERSION.SDK_INT >= 28 -> ImageDecoder.decodeBitmap(ImageDecoder.createSource(activity?.contentResolver!!, it))
                    else -> MediaStore.Images.Media.getBitmap(activity?.contentResolver, it)
                }
            }
            if (bitmap != null) {
                val fileName = resultIntent?.data.let {
                    activity?.contentResolver?.query(it!!, null, null, null, null)
                }.use { cursor ->
                    cursor?.moveToFirst()
                    cursor?.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                } ?: "null"
                val cacheFileName = when (val index = fileName.lastIndexOf(".")) {
                    -1 -> fileName + "_cache"
                    else -> fileName.substring(0, index) + "_cache" + fileName.substring(index)
                }

                ImageviewChatBinding.inflate(layoutInflater, binding.chatLayoutView, true).root.run {
                    val (width, height) = calcImageLayoutSize(bitmap.width, bitmap.height, 300.0f, 400.0f)
                    layoutParams.width = width
                    layoutParams.height = height
                    setImageBitmap(bitmap)
                    context.openFileOutput(cacheFileName, Context.MODE_PRIVATE).use {
                        val outputStream = ByteArrayOutputStream()
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                        it.write(outputStream.toByteArray())
                    }
                    chatViewModel.addChatImage(cacheFileName)
                    binding.chatView.post { binding.chatView.fullScroll(View.FOCUS_DOWN) }
                }
            }
        }
    }

    private fun calcImageLayoutSize(width: Int, height: Int, maxWidthDP: Float, maxHeightDP: Float): Pair<Int, Int> {
        val widthDP = width / resources.displayMetrics.density
        val heightDP = height / resources.displayMetrics.density
        val retWidthDP: Float
        val retHeightDP: Float

        if (widthDP * maxHeightDP / heightDP > maxWidthDP) {
            retWidthDP = maxWidthDP
            retHeightDP = heightDP * maxWidthDP / widthDP
        } else {
            retWidthDP = widthDP * maxHeightDP / heightDP
            retHeightDP = maxHeightDP
        }
        return applyDimension(COMPLEX_UNIT_DIP, retWidthDP, resources.displayMetrics).toInt() to applyDimension(COMPLEX_UNIT_DIP, retHeightDP, resources.displayMetrics).toInt()
    }
}

class ChatViewModelFactory(private val x: Int, private val y: Int) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ChatViewModel(x, y) as T
    }
}