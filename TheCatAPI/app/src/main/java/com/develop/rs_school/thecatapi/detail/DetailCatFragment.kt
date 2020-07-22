package com.develop.rs_school.thecatapi.detail

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.develop.rs_school.thecatapi.databinding.DetailCatFragmentBinding
import java.io.OutputStream

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
        /*Glide.with(requireActivity()).asBitmap().load(cat.imageUrl)
            .apply(RequestOptions().placeholder(R.drawable.loading_animation))
            .into(binding.catImage)*/



        Glide.with(this)
            .asBitmap()
            .load(cat.imageUrl)
            .into(object : CustomTarget<Bitmap>(){
                override fun onResourceReady(bitmapResource: Bitmap, transition: Transition<in Bitmap>?) {
                    binding.catImage.setImageBitmap(bitmapResource)
                    val resolver = context?.contentResolver
                    val savedImageURL = MediaStore.Images.Media.insertImage(
                        resolver,
                        bitmapResource,
                        cat.id+".png",
                        "Image of ${cat.id}"
                    )

                    //saveImage(resource, context!!, "1")
                }
                override fun onLoadCleared(placeholder: Drawable?) {
                    // this is called when imageView is cleared on lifecycle call or for
                    // some other reason.
                    // if you are referencing the bitmap somewhere else too other than this imageView
                    // clear it here as you can no longer have the bitmap
                }
            })

        return binding.root
    }
    private val collection =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) MediaStore.Video.Media.getContentUri(
            MediaStore.VOLUME_EXTERNAL
        ) else MediaStore.Video.Media.EXTERNAL_CONTENT_URI

//    private fun saveImage() {
//        val resolver = context?.contentResolver!!
//
//// Find all audio files on the primary external storage device.
//// On API <= 28, use VOLUME_EXTERNAL instead.
//        val audioCollection = MediaStore.Audio.Media
//            .getContentUri(MediaStore.VOLUME_EXTERNAL)
//
//        val songDetails = ContentValues().apply {
//            put(MediaStore.Audio.Media.DISPLAY_NAME, "My Workout Playlist.mp3")
//            put(MediaStore.Audio.Media.IS_PENDING, 1)
//        }
//
//        val songContentUri = resolver.insert(audioCollection, songDetails)
//
//        if (songContentUri != null) {
//            resolver.openFileDescriptor(songContentUri, "w", null).use { pfd ->
//                // Write data into the pending audio file.
//            }
//        }
//
//// Now that we're finished, release the "pending" status, and allow other apps
//// to play the audio track.
//        songDetails.clear()
//        songDetails.put(MediaStore.Audio.Media.IS_PENDING, 0)
//        if (songContentUri != null) {
//            resolver.update(songContentUri, songDetails, null, null)
//        }
//
//    }


//    /// @param folderName can be your app's name
//    private fun saveImage(bitmap: Bitmap, context: Context, folderName: String) {
//
//        requestPerm()
//
//        if (android.os.Build.VERSION.SDK_INT >= 29) {
//            val values = contentValues()
//            values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/" + folderName)
//            values.put(MediaStore.Images.Media.IS_PENDING, true)
//            // RELATIVE_PATH and IS_PENDING are introduced in API 29.
//
//            val uri: Uri? = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
//            if (uri != null) {
//                saveImageToStream(bitmap, context.contentResolver.openOutputStream(uri))
//                values.put(MediaStore.Images.Media.IS_PENDING, false)
//                context.contentResolver.update(uri, values, null, null)
//            }
//        } else {
//            val directory = File(Environment.getExternalStorageDirectory().toString() + separator + folderName)
//            // getExternalStorageDirectory is deprecated in API 29
//
//            if (!directory.exists()) {
//                directory.mkdirs()
//            }
//            val fileName = System.currentTimeMillis().toString() + ".png"
//            val file = File(directory, fileName)
//            saveImageToStream(bitmap, FileOutputStream(file))
//            if (file.absolutePath != null) {
//                val values = contentValues()
//                values.put(MediaStore.Images.Media.DATA, file.absolutePath)
//                // .DATA is deprecated in API 29
//                context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
//            }
//        }
//    }

    private fun requestPerm() {

        if (checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 55)

            // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
            // app-defined int constant that should be quite unique

            return;
        }
    }

    private fun contentValues() : ContentValues {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png")
        values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000);
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        return values
    }

    private fun saveImageToStream(bitmap: Bitmap, outputStream: OutputStream?) {
        if (outputStream != null) {
            try {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                outputStream.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}