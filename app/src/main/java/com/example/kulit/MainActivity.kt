package com.example.kulit

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.Manifest
import android.content.pm.PackageManager

class MainActivity : AppCompatActivity() {

    private lateinit var scanImageButton: Button
    private lateinit var takePictureButton: Button
    private lateinit var selectedImageView: ImageView
    private lateinit var overlayProcessingLayout: LinearLayout
    private lateinit var tvStatus: TextView
    private lateinit var tvDone: TextView

    private val pickImageRequestCode = 100
    private val takePictureRequestCode = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        scanImageButton = findViewById(R.id.btnSelectImage)
        takePictureButton = findViewById(R.id.btnTakePicture)
        selectedImageView = findViewById(R.id.ivPhoto)
        overlayProcessingLayout = findViewById(R.id.overlayProcessingLayout)
        tvStatus = findViewById(R.id.tvStatus)
        tvDone = findViewById(R.id.tvDone)


        // Check permissions
        checkPermissions()

        // Handle scan image from storage button click
        scanImageButton.setOnClickListener {
            openImagePicker()
        }

        // Handle take picture button click
        takePictureButton.setOnClickListener {
            openCamera()
        }
    }

    // Function to open image picker
    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, pickImageRequestCode)
    }

    // Function to open camera
    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, takePictureRequestCode)
    }

    // Check for permissions
    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), takePictureRequestCode)
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), pickImageRequestCode)
        }
    }

    // Handle the result from image picker or camera
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                pickImageRequestCode -> {
                    val selectedImageUri: Uri? = data?.data
                    selectedImageView.setImageURI(selectedImageUri) // Set the image in the ImageView
                    startProcessingActivity()
                }
                takePictureRequestCode -> {
                    val photo: Bundle? = data?.extras
                    val imageBitmap = photo?.get("data") as? Bitmap
                    if (imageBitmap != null) {
                        selectedImageView.setImageBitmap(imageBitmap) // Set the captured image in the ImageView
                        startProcessingActivity()
                    } else {
                        Toast.makeText(this, "Error capturing image", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            Toast.makeText(this, "Action Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startProcessingActivity() {
        // Hide initial UI elements and show processing overlay
        toggleUIVisibility(false)

        // Simulate processing with a delay
        Thread {
            try {
                Thread.sleep(3000) // Simulating a processing time of 3 seconds
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            // After processing is done, update the status
            runOnUiThread {
                tvStatus.visibility = TextView.GONE
                tvDone.visibility = TextView.VISIBLE
            }

            // Move to the ResultActivity after 1 second delay
            runOnUiThread {
                Thread.sleep(1000) // Wait for 1 second before transitioning
                val intent = Intent(this, ResultActivity::class.java)
                startActivity(intent)
                finish() // Close the current activity
            }
        }.start()
    }

    // Function to toggle visibility of UI elements
    private fun toggleUIVisibility(isProcessing: Boolean) {
        if (isProcessing) {
            scanImageButton.visibility = Button.GONE
            takePictureButton.visibility = Button.GONE
            selectedImageView.visibility = ImageView.GONE
            overlayProcessingLayout.visibility = LinearLayout.VISIBLE
            tvStatus.visibility = TextView.VISIBLE
            tvDone.visibility = TextView.GONE
        } else {
            scanImageButton.visibility = Button.VISIBLE
            takePictureButton.visibility = Button.VISIBLE
            selectedImageView.visibility = ImageView.VISIBLE
            overlayProcessingLayout.visibility = LinearLayout.GONE
        }
    }
}