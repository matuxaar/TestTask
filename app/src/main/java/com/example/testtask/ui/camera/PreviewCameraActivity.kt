package com.example.testtask.ui.camera

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.testtask.DaggerApp
import com.example.testtask.MainActivity
import com.example.testtask.R
import com.example.testtask.databinding.PreviewCameraBinding
import com.example.testtask.di.viewmodel.ViewModelFactory
import com.example.testtask.domain.model.ImageIn
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class PreviewCameraActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: CameraViewModel by viewModels { factory }
    private lateinit var binding: PreviewCameraBinding
    private var imageCapture: ImageCapture? = null
    private lateinit var outputDirectory: File
    private var currentLatitude: Double = 0.0
    private var currentLongitude: Double = 0.0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PreviewCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (applicationContext as DaggerApp).appComponent.inject(this)

        currentLatitude = intent.getDoubleExtra("latitude", 0.0)
        currentLongitude = intent.getDoubleExtra("longitude", 0.0)

        outputDirectory = getOutputDirectory()

        if (allPermissionGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        val intent = Intent(this, MainActivity::class.java)

        binding.takePhotoButton.setOnClickListener {
            takePhoto()
            startActivity(intent)
        }
    }

    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply {
                mkdirs()
            }
        }
        return if (mediaDir != null && mediaDir.exists()) mediaDir else filesDir

    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return
        val photoFile = File(
            outputDirectory,
            SimpleDateFormat(
                FILENAME_FORMAT,
                Locale.getDefault()
            ).format(System.currentTimeMillis()) + ".jpg"
        )
        val outputOption = ImageCapture
            .OutputFileOptions
            .Builder(photoFile)
            .build()
        imageCapture.takePicture(
            outputOption,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    val msg = "Photo Saved"
                    Toast.makeText(
                        this@PreviewCameraActivity,
                        "$msg $savedUri",
                        Toast.LENGTH_LONG
                    ).show()

                    val base64Image = convertImageToBase64(photoFile.absolutePath)

                    val imageIn = ImageIn(
                        base64Image,
                        System.currentTimeMillis() / 1000,
                        currentLatitude,
                        currentLongitude
                    )

                    viewModel.uploadPhoto(imageIn)
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.e(TAG, "onError: ${exception.message}", exception)
                }

            }

        )
    }



    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider
            .getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also { mPreview ->
                    mPreview.setSurfaceProvider(
                        binding.previewView.surfaceProvider
                    )
                }
            imageCapture = ImageCapture.Builder().build()
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageCapture
                )

            } catch (e: Exception) {
                Log.d(TAG, "startCamera Fail:", e)
            }
        }, ContextCompat.getMainExecutor(this))
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionGranted()) {
                startCamera()
            } else {
                Toast.makeText(
                    this, "Permission not granted by the user",
                    Toast.LENGTH_SHORT
                ).show()

                finish()
            }
        }
    }

    private fun allPermissionGranted() =
        REQUIRED_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                baseContext, it
            ) == PackageManager.PERMISSION_GRANTED
        }


    private fun convertImageToBase64(imagePath: String): String {
        val originalBitmap = BitmapFactory.decodeFile(imagePath)

        val maxSize = 1280.0
        val scale = maxSize / Math.max(originalBitmap.width, originalBitmap.height)

        val newWidth = (originalBitmap.width * scale).toInt()
        val newHeight = (originalBitmap.height * scale).toInt()
        val scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true)


        var quality = 20
        val outputStream = ByteArrayOutputStream()
        scaledBitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
        val maxFileSize = 2 * 1024 * 1024

        while (outputStream.size() > maxFileSize && quality > 0) {
            outputStream.reset()
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
            quality -= 5
        }
        val finalWidth = 1024
        val finalHeight = 1024
        val resizedBitmap = Bitmap.createScaledBitmap(scaledBitmap, finalWidth, finalHeight, true)
        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)

        val byteArray = outputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)

    }


    companion object {
        private const val TAG = "CameraXApp"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 123
        private val REQUIRED_PERMISSIONS =
            arrayOf(Manifest.permission.CAMERA)
    }
}