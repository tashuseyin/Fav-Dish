package com.example.favdish.view.activites

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.favdish.R
import com.example.favdish.databinding.ActivityAddUpdateDishBinding
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.*
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import java.io.File


class AddUpdateDishActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddUpdateDishBinding

    private lateinit var cameraPhoto: TextView
    private lateinit var galleryPhoto: TextView
    private lateinit var dialog: Dialog
    private lateinit var view: View
    private val requestCamera = 1
    private val requestMedia = 200
    private lateinit var photoFile: File
    private val fileName = "photo.jpg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUpdateDishBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActionBar()

        binding.ivAddDishImage.setOnClickListener {
            imageSelectedDialog()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == requestCamera) {
            val image = BitmapFactory.decodeFile(photoFile.absolutePath)
            binding.ivDishImage.setImageBitmap(image)
        }
        if (resultCode == Activity.RESULT_OK && requestCode == requestMedia) {
            binding.ivDishImage.setImageURI(data?.data)
        }
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbarAddDishActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarAddDishActivity.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun dispatchTakePictureIntent() {
        Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    if (report!!.areAllPermissionsGranted()) {
                        openCamera()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    token!!.continuePermissionRequest()
                }
            }).withErrorListener {
                Toast.makeText(this@AddUpdateDishActivity, "Error occurred!", Toast.LENGTH_SHORT)
                    .show()
            }.onSameThread()
            .check()
    }

    private fun dispatchTakePictureGalleryIntent() {
        Dexter.withContext(this)
            .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    openGallery()
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    TODO("Not yet implemented")
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: PermissionRequest?,
                    token: PermissionToken?
                ) {
                    token!!.continuePermissionRequest()
                }
            }).withErrorListener {
                Toast.makeText(this@AddUpdateDishActivity, "Error occurred!", Toast.LENGTH_SHORT)
                    .show()
            }.onSameThread()
            .check()
    }

    private fun getPhotoFile(fileName: String): File {
        val storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", storageDirectory)
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        photoFile = getPhotoFile(fileName)
        val fileProvider =
            FileProvider.getUriForFile(this, "com.example.favdish.view.fileprovider", photoFile)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)
        startActivityForResult(intent, requestCamera)
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, requestMedia)
    }

    private fun imageSelectedDialog() {
        dialog = Dialog(this)
        view = layoutInflater.inflate(R.layout.dialog_custom, null)
        dialog.setContentView(view)
        dialogSetView()
        dialog.show()
        dialogSetListener()
    }

    private fun dialogSetView() {
        cameraPhoto = view.findViewById(R.id.tv_camera)
        galleryPhoto = view.findViewById(R.id.tv_gallery)
    }

    private fun dialogSetListener() {
        cameraPhoto.setOnClickListener {
            dispatchTakePictureIntent()
            dialog.dismiss()
        }

        galleryPhoto.setOnClickListener {
            dispatchTakePictureGalleryIntent()
            dialog.dismiss()
        }
    }

}


