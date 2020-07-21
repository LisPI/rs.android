package com.develop.rs_school.thecatapi.detail

import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.provider.MediaStore
import android.transition.Transition
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.develop.rs_school.thecatapi.R
import com.develop.rs_school.thecatapi.databinding.DetailCatFragmentBinding

class DetailCatFragment : Fragment() {

    private var _binding: DetailCatFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: DetailCatViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = DetailCatFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(DetailCatViewModel::class.java)

        //TODO view model factory
        val cat = DetailCatFragmentArgs.fromBundle(requireArguments()).cat
        binding.catId.text = cat.id
        Glide.with(requireActivity()).asBitmap().load(cat.imageUrl)
            .apply(RequestOptions().placeholder(R.drawable.loading_animation))
            .into(binding.catImage)

        saveImage()

        return binding.root

    }

    private fun saveImage() {
//// Add a specific media item.
//        val resolver = requireActivity().contentResolver
//
//// Find all audio files on the primary external storage device.
//// On API <= 28, use VOLUME_EXTERNAL instead.
//        val audioCollection = MediaStore.Audio.Media.
//// Publish a new song.
//        val newSongDetails = ContentValues().apply {
//            put(MediaStore.Audio.Media.DISPLAY_NAME, "My Song.mp3")
//        }
//
//// Keeps a handle to the new song's URI in case we need to modify it
//// later.
//        val myFavoriteSongUri = resolver
//            .insert(audioCollection, newSongDetails)

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}