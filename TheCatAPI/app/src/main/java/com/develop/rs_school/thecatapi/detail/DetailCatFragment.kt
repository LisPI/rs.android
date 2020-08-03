package com.develop.rs_school.thecatapi.detail

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.develop.rs_school.thecatapi.R
import com.develop.rs_school.thecatapi.databinding.DetailCatFragmentBinding
import com.develop.rs_school.thecatapi.network.Cat
import com.develop.rs_school.thecatapi.saveBitmapImageToPNGFile
import com.google.android.material.snackbar.Snackbar

class DetailCatFragment : Fragment() {

    private var _binding: DetailCatFragmentBinding? = null
    private val binding get() = requireNotNull(_binding)
    private lateinit var viewModel: DetailCatViewModel
    private lateinit var viewModelFactory: DetailCatViewModelFactory

    private companion object {
        private const val bitmapCompressQuality = 100
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DetailCatFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelFactory =
            DetailCatViewModelFactory(DetailCatFragmentArgs.fromBundle(requireArguments()).cat)
        viewModel = ViewModelProvider(this, viewModelFactory).get(DetailCatViewModel::class.java)

        Glide.with(requireActivity())
            .load(viewModel.cat.imageUrl)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.connection_error)
            )
            .into(binding.catImage)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.detail_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.save_image_menu_item)
            saveCatImageToGallery()
        return super.onOptionsItemSelected(item)
    }

    private fun saveCatImageToGallery() {
        if (PackageManager.PERMISSION_GRANTED ==
            checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
        ) {
            saveImage(viewModel.cat)
        } else {
            requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            isGranted: Boolean ->
                if (isGranted) {
                    saveImage(viewModel.cat)
                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.GrantPermissionMessage),
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }

    private fun saveImage(cat: Cat) {
        Glide.with(this)
            .asBitmap()
            .load(cat.imageUrl)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    bitmapResource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    val savedImageURI =
                        context?.let {
                            saveBitmapImageToPNGFile(
                                bitmapResource,
                                bitmapCompressQuality,
                                cat.imageUrl,
                                it
                            )
                        }

                    if (savedImageURI != null)
                        showSnackbar(getString(R.string.ImageSaved))
                    else
                        showSnackbar(getString(R.string.ImageSaveError))
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    super.onLoadFailed(errorDrawable)
                    showSnackbar(getString(R.string.ImageSaveError))
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    // this is called when imageView is cleared on lifecycle call or for
                    // some other reason.
                }
            })
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(
            binding.root,
            message,
            Snackbar.LENGTH_SHORT
        ).show()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
