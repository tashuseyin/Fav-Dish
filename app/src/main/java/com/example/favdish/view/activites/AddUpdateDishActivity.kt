package com.example.favdish.view.activites

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.favdish.R
import com.example.favdish.databinding.ActivityAddUpdateDishBinding
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.*
import com.karumi.dexter.listener.multi.MultiplePermissionsListener


class AddUpdateDishActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddUpdateDishBinding

    private lateinit var cameraPhoto: TextView
    private lateinit var galleryPhoto: TextView
    private lateinit var dialog: Dialog
    private lateinit var view: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUpdateDishBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActionBar()

        binding.ivAddDishImage.setOnClickListener {
            imageSelectedDialog()
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
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
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
            .withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    if (report!!.areAllPermissionsGranted()) {
                        openGallery()
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

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, 100)
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type= "image/*"
        startActivityForResult(intent,101)


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


